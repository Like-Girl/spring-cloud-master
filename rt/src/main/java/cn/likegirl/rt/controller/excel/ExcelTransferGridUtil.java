package cn.likegirl.rt.controller.excel;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ExcelTransferGridUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExcelTransferGridUtil.class);

  /**
   * 2003- 版本的excel
   */
  private final static String EXCEL_2003_L = ".xls";
  /**
   * 2007+ 版本的excel
   */
  private final static String EXCEL_2007_U = ".xlsx";

  /**
   * 格式化数字
   */
  private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");
  /**
   * 日期格式化
   */
  private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private final static String GENERAL_NUMERIC_TYPE = "General";

  private final static String DEFAULT_EXCEL_DATETIME_FORMAT = "m/d/yy";

  /**
   * 合并的单元格只有左上角一个有数据， 其他单元格 默认： ""
   */
  private final static String DEFAULT_COMBINE_CELL_NON_VALUE = "";

  /**
   * 默认解析的Sheet
   */
  private final static int DEFAULT_SELECTOR_SHEET_NUM = 0;

  /**
   * 默认最大解析条数
   */
  private final static int  DEFAULT_MAXIMUM_ANALYSIS_NUM = 10;


  public static Map<Integer, RowFormat> readExcel(InputStream in, String fileName,ExcelAnalyzeCallback callback) throws Exception {
    Workbook work = getWorkbook(in, fileName);
    return readExcel(work,DEFAULT_SELECTOR_SHEET_NUM,callback);
  }

  public static Map<Integer, RowFormat> readExcel(InputStream in, String fileName,int sheetNo,ExcelAnalyzeCallback callback) throws Exception {
    Workbook work = getWorkbook(in, fileName);
    return readExcel(work,sheetNo,callback);
  }

  public static List<Map<Integer, RowFormat>> readAllExcel(InputStream in, String fileName,ExcelAnalyzeCallback callback) throws Exception {
    Workbook work = getWorkbook(in, fileName);
    List<Map<Integer, RowFormat>> data = new ArrayList<>();
    for(int no = 0; no < work.getNumberOfSheets(); no++){
      data.add(readExcel(work,no,callback));
    }
    return data;
  }

  /**
   * 将流中的Excel数据转成List<Map>(读取Excel)
   *
   * @param work     Workbook
   * @param sheetNo  Sheet Number
   */
  private static Map<Integer, RowFormat> readExcel(Workbook work,int sheetNo,ExcelAnalyzeCallback callback)
      throws Exception {
    Assert.notNull(work,"Workbook must not be null.");
    Sheet sheet;
    int maxColumnNum = 0;
    final FormulaEvaluator formulaEvaluator = work.getCreationHelper().createFormulaEvaluator();
    if ((sheet = work.getSheetAt(sheetNo)) == null) {
      throw new Exception("Sheet must not be null.");
    }
    callback.beforeAnalyze(sheet);
    // 获取有合并单元格的区域
    List<CellRangeAddress> combineCellList = getCombineCellList(sheet);
    // 表头行数据
    Map<Integer, RowFormat> sheetRows = new HashMap<>();
    Row row;
    Cell cell;
    int analysisNum = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
    analysisNum = analysisNum > DEFAULT_MAXIMUM_ANALYSIS_NUM ? DEFAULT_MAXIMUM_ANALYSIS_NUM : analysisNum;
    for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet.getFirstRowNum() + analysisNum; rowNum++) {
      RowFormat rowFormat = new RowFormat();
      row = sheet.getRow(rowNum);
      if (row == null) {
        LOGGER.warn("SheetName [{}] Row [{}] 为空", sheet.getSheetName(), rowNum);
        continue;
      }
      for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
        CellFormat cellFormat = new CellFormat(cellNum, null);
        // 前置布局
        rowFormat.addCellFormat(cellFormat);
        cell = row.getCell(cellNum);
        if (cell == null) {
          continue;
        }
        Object v = getCellValue(cell, formulaEvaluator);
        cellFormat.setValue(String.valueOf(v));
        // 判断该cell是否为合同单元格中的一个
        combineCellProcess(combineCellList, cell, cellFormat);
        // 最大有效列数
        if(cellNum >= maxColumnNum && StringUtils.isNotEmpty(cellFormat.getValue())){
          if(cellFormat.getMerged()){
            maxColumnNum = cellNum + cellFormat.getMergedCol() - 1;
          }else{
            maxColumnNum = cellNum;
          }
        }
        // 若为合并的单元，则判断是否有值
        combineCellValueProcess(rowNum, cellNum, cellFormat,rowFormat, sheetRows);


      }
      // 校验空白行 后置布局
      if(isBlankRow(rowFormat)){
        sheetRows.put(rowNum, rowFormat);
      }
    }
    // 补偿列
    compensate(sheetRows,maxColumnNum);
    callback.afterAnalyze();
    work.close();
    return sheetRows;
  }

  /**
   * 补偿列
   * 去掉多余单元格
   * 追加默认单元格
   *
   * @param rows      数据
   * @param maxColumn 最大列数
   */
  private static void compensate(Map<Integer, RowFormat> rows,int maxColumn){
    final int max = maxColumn + 1;
    LOGGER.info("Excel 最大列数为 [{}]", max);
    rows.forEach((k,v) -> {
      for(;;){
        if(v.size() == max){
          break;
        }else if(v.size() > max){
          v.removeLast();
        }else {
          CellFormat cellFormat = new CellFormat(v.size(), null);
          v.addCellFormat(cellFormat);
        }
      }
    });
  }

  /**
   * 校验空白行
   * @param rowFormat Row
   * @return  Boolean
   */
  private static boolean isBlankRow(RowFormat rowFormat){
    return rowFormat.getCellFormats().stream().anyMatch(cell -> StringUtils.isNotEmpty(cell.getValue()));
  }

  /**
   * 获取合并单元格集合
   */
  private static List<CellRangeAddress> getCombineCellList(Sheet sheet) {
    List<CellRangeAddress> list = new ArrayList<>();
    //获得一个 sheet 中合并单元格的数量
    int numMergedRegions = sheet.getNumMergedRegions();
    //遍历所有的合并单元格
    for (int i = 0; i < numMergedRegions; i++) {
      //获得合并单元格保存进list中
      CellRangeAddress ca = sheet.getMergedRegion(i);
      list.add(ca);
    }
    List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
    return list;
  }

  /**
   * 计算Cell是否为合并单元格 是的话返回合并行数和列数 （只要在合并区域中的Cell就会返回合同行列数，但只有左上角第一个有数据）
   *
   * @param listCombineCell 合并区域列表
   * @param cell 单元格
   * @param cellFormat 单元格格式
   */
  private static void combineCellProcess(List<CellRangeAddress> listCombineCell, Cell cell, CellFormat cellFormat) {

    int fc, // 起始列角标
        lc, // 末尾列角标
        fr, // 起始行角标
        lr; // 末尾列角标
    boolean isMerged = false;
    int mergedRow, // 合并的行数
        mergedCol; // 合并的列数
    for (CellRangeAddress cra : listCombineCell) {
      //获得合并单元格的起始行, 结束行, 起始列, 结束列
      fc = cra.getFirstColumn();
      lc = cra.getLastColumn();
      fr = cra.getFirstRow();
      lr = cra.getLastRow();
      //判断cell是否在合并区域之内，在的话返回true和合并行列数
      if (cell.getRowIndex() >= fr && cell.getRowIndex() <= lr) {
        if (cell.getColumnIndex() >= fc && cell.getColumnIndex() <= lc) {
          isMerged = true;
          mergedRow = lr - fr + 1;
          mergedCol = lc - fc + 1;
          // cell format
          cellFormat.setMergedRow(mergedRow);
          cellFormat.setMergedCol(mergedCol);
          cellFormat.setFirstColumn(fc);
          cellFormat.setLastColumn(lc);
          cellFormat.setFirstRow(fr);
          cellFormat.setLastRow(lr);
          break;
        }
      }
    }
    cellFormat.setMerged(isMerged);
  }


  private static void combineCellValueProcess(int currentRowNum, int currentCellNum,
      CellFormat cellFormat, RowFormat currentRowFormat, Map<Integer, RowFormat> sheetRows) {
    if (cellFormat.getMerged()) {
      if (DEFAULT_COMBINE_CELL_NON_VALUE.equals(cellFormat.getValue())) {
        // 合并单元格： 空值
        // 左上角
        if (!(cellFormat.getFirstRow() == currentRowNum
            && cellFormat.getFirstColumn() == currentCellNum)) {
          cellFormat.setDisplay(Boolean.FALSE);
          // 补偿合并单元格的值
          // 计算获取左上角的数据
          // 若在当前行
          // 不捕捉NPE
          String value;
          if(currentRowNum == cellFormat.getFirstRow()){
            value = currentRowFormat.getCellFormat(cellFormat.getFirstColumn()).getValue();
          }else{
            value = sheetRows.get(cellFormat.getFirstRow())
                .getCellFormat(cellFormat.getFirstColumn()).getValue();
          }
          cellFormat.setValue(value);
        }
      } else {
        // 是否捕捉NULL
      }
    }
  }

  /**
   * 根据文件后缀，自适应上传文件的版本
   *
   * @param in 文件流
   * @param fileName 文件名
   */
  public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {
    Workbook wb;
    if (fileName.endsWith(EXCEL_2003_L)) {
      // 2003-
      wb = new HSSFWorkbook(in);
    } else if (fileName.endsWith(EXCEL_2007_U)) {
      // 2007+
      wb = new XSSFWorkbook(in);
    } else {
      throw new Exception("文件格式有误！");
    }
    return wb;
  }

  /**
   * 单元格的值
   *
   * @param cell 单元格
   * @param formulaEvaluator 公式解析器
   */
  private static Object getCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
    return getCellValue(cell, formulaEvaluator, false);
  }


  /**
   * 单元格的值
   *
   * CachedFormulaResultTypeEnum one of ({@link CellType#NUMERIC}, {@link CellType#STRING}, {@link
   * CellType#BOOLEAN}, {@link CellType#ERROR}) depending
   *
   * @param cell 单元格
   * @param formulaEvaluator 公式解析器
   * @param isCached CachedFormulaResultTypeEnum
   */
  private static Object getCellValue(Cell cell, FormulaEvaluator formulaEvaluator,
      boolean isCached) {
    Assert.notNull(cell, "Cell must not be null.");
    Assert.notNull(formulaEvaluator, "FormulaEvaluator must not be null");
    Object value;
    CellType cellType = isCached ? cell.getCachedFormulaResultTypeEnum() : cell.getCellTypeEnum();
    switch (cellType) {
      case STRING:
        value = cell.getRichStringCellValue().getString();
        break;
//      case _NONE:
//        break;
      case NUMERIC:
        if (GENERAL_NUMERIC_TYPE.equals(cell.getCellStyle().getDataFormatString())) {
          value = DECIMAL_FORMAT.format(cell.getNumericCellValue());
        } else if (DEFAULT_EXCEL_DATETIME_FORMAT.equals(cell.getCellStyle().getDataFormatString())) {
          value = SIMPLE_DATE_FORMAT.format(cell.getDateCellValue());
        } else {
          value = DECIMAL_FORMAT.format(cell.getNumericCellValue());
        }
        break;
      case BOOLEAN:
        value = cell.getBooleanCellValue();
        break;
      case BLANK:
        value = "";
        break;
      case ERROR:
        value = "未知异常";
        LOGGER.error("sheet name [{}] row [{}] column [{}]", cell.getSheet().getSheetName(),
            cell.getRowIndex(), cell.getColumnIndex());
        break;
      case FORMULA:
        /*
         * 对于公式单元格，excel存储两件事。一个是公式本身，另一个是“缓存”值（论坛被评估为的最后一个值）
         * 如果你想获得最后一个缓存的值（可能不再正确，但只要Excel保存文件而你没有改变它应该是这样），
         */
        try {
          value = formulaEvaluator.evaluate(cell).getNumberValue();
        } catch (Exception e) {
          LOGGER.warn("Excel row [{}] column [{}] 公式解析异常 [{}]. 开始使用“缓存”值.", cell.getRowIndex(),
              cell.getColumnIndex(), e.getMessage());
          value = getCellValue(cell, formulaEvaluator, true);
        }
        break;
      default:
        throw new IllegalArgumentException("getCellValue method cell unknown type");
    }
    return value;
  }


  public static void main(String[] args) throws Exception {
    File file = new File(System.getProperty("user.dir") + "\\" + "test.xlsx");
    FileInputStream fis = new FileInputStream(file);
    Map<Integer, RowFormat> lists = readExcel(fis, file.getName(), new ExcelAnalyzeCallback() {
      @Override
      public void beforeAnalyze(Sheet sheet) {
      }

      @Override
      public void afterAnalyze(Object... params) {
      }
    });
    System.out.println(JSON.toJSONString(lists));

  }
}
