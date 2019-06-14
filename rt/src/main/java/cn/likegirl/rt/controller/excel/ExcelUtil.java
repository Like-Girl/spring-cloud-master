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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

  private final static String excel2003L = ".xls"; // 2003- 版本的excel
  private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel


  /**
   * 将流中的Excel数据转成List<Map>(读取Excel)
   *
   * @param in 输入流
   * @param fileName 文件名（判断Excel版本）
   */
  public static List<List<RowFormat>> readExcel(InputStream in, String fileName)
      throws Exception {
    // 根据文件名来创建Excel工作薄
    Workbook work = getWorkbook(in, fileName);
    if (null == work) {
      throw new Exception("创建Excel工作薄为空！");
    }
    Sheet sheet = null;
    Row row = null;
    Cell cell = null;
    // 返回数据
    List<List<RowFormat>> data = new ArrayList<>();
    //循环多个工作表
    for (int i = 0; i < work.getNumberOfSheets(); i++) {
      Map<String, Object> result = new HashMap<>();
      sheet = work.getSheetAt(i);
      if (sheet == null) {
        continue;
      }
      //获取有合并单元格的区域
      List<CellRangeAddress> combineCellList = getCombineCellList(sheet);
      // 测试有几行数据是有表头数据的
      boolean flag = true;
      int h = 0;
      for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
        row = sheet.getRow(j);
        // 遍历所有的列
        if (flag) {
          for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
            cell = row.getCell(y);
            Object v = getCellValue(cell);
            flag = false;
            if (v != null && !v.toString().equals("")) {
              flag = true;
              break;
            }
          }
          h++;
        }
      }
      System.out.println("h:" + h);
      //列数
      int colNum = 0;
      //表头行数
      int h1 = h - 1;
      //表头数据
      List<RowFormat> sheetRows = new ArrayList<>();
      //循环行，不要从0开始，防止前几行为空的情况
      for (int k = sheet.getFirstRowNum(); k < sheet.getFirstRowNum() + h1; k++) {
        RowFormat rowFormat = new RowFormat();
        row = sheet.getRow(k);
        //获取列数
        colNum = row.getLastCellNum() - row.getFirstCellNum();
        // 遍历所有的列
        for (int x = row.getFirstCellNum(); x < row.getLastCellNum(); x++) {
          CellFormat cellFormat = new CellFormat();
          cell = row.getCell(x);
          if (cell == null) {
            cellFormat.setM(false);
            cellFormat.setMc(1);
            cellFormat.setMr(1);
            rowFormat.addCellFormat(cellFormat);
            continue;
          }
          //表格cell的数据，合并的只有左上角一个有数据，其余全为空
          String v = getCellValue(cell).toString();
          cellFormat.setValue(v);

          //判断该cell是否为合同单元格中的一个
          isCombineCell(combineCellList, cell, cellFormat);
          //如果是，则判断是否有值，有值的才添加到list中
          if (cellFormat.getM()) {
            // 合并后，其他的单元格值为空串 ""
            if (v != null && !"".equals(v)) {
              rowFormat.addCellFormat(cellFormat);
            }
          } else {
            //如果不是，则直接插入
            cellFormat.setMc(1);
            cellFormat.setMr(1);
            rowFormat.addCellFormat(cellFormat);
          }
        }
        sheetRows.add(rowFormat);
      }
      data.add(sheetRows);
    }

    System.out.println(data);
    //work.close();
    return data;
  }

  /**
   * 获取合并单元格集合
   */
  public static List<CellRangeAddress> getCombineCellList(Sheet sheet) {
    List<CellRangeAddress> list = new ArrayList<>();
    //获得一个 sheet 中合并单元格的数量
    int numMergedRegions = sheet.getNumMergedRegions();
    //遍历所有的合并单元格
    for (int i = 0; i < numMergedRegions; i++) {
      //获得合并单元格保存进list中
      CellRangeAddress ca = sheet.getMergedRegion(i);
      list.add(ca);
    }
    return list;
  }

  /**
   * 判断cell是否为合并单元格
   * 是的话返回合并行数和列数（只要在合并区域中的cell就会返回合同行列数，但只有左上角第一个有数据）
   *
   * @param listCombineCell 合并区域列表
   */
  public static Map<String, Object> isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, CellFormat cellFormat) throws Exception {

    int fc, // 起始列角标
        lc, // 末尾列角标
        fr, // 起始行角标
        lr; // 末尾列角标
    String cellValue = null;
    boolean flag = false;
    int mergedRow, // 合并的行数
        mergedCol; // 合并的列数
    Map<String, Object> result = new HashMap<>();

    for (CellRangeAddress cra : listCombineCell) {
      //获得合并单元格的起始行, 结束行, 起始列, 结束列
      fc = cra.getFirstColumn();
      lc = cra.getLastColumn();
      fr = cra.getFirstRow();
      lr = cra.getLastRow();
      //判断cell是否在合并区域之内，在的话返回true和合并行列数
      if (cell.getRowIndex() >= fr && cell.getRowIndex() <= lr) {
        if (cell.getColumnIndex() >= fc && cell.getColumnIndex() <= lc) {
          flag = true;
          mergedRow = lr - fr + 1;
          mergedCol = lc - fc + 1;
          result.put("mergedRow", mergedRow);
          result.put("mergedCol", mergedCol);
          result.put("fc",fc);
          result.put("fr",fr);
          // cell format
          cellFormat.setMr(mergedRow);
          cellFormat.setMc(mergedCol);
          cellFormat.setFc(fc);
          cellFormat.setLc(lc);
          cellFormat.setFr(fr);
          cellFormat.setLr(lr);
          break;
        }
      }
    }
    // 是否为合并的单元格
    cellFormat.setM(flag);
    result.put("flag", flag);
    return result;
  }

  /**
   * 描述：根据文件后缀，自适应上传文件的版本
   *
   * @param inStr ,fileName
   */
  public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
    Workbook wb = null;
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    if (excel2003L.equals(fileType)) {
      wb = new HSSFWorkbook(inStr); // 2003-
    } else if (excel2007U.equals(fileType)) {
      wb = new XSSFWorkbook(inStr); // 2007+
    } else {
      throw new Exception("解析的文件格式有误！");
    }
    return wb;
  }

  /**
   * 描述：对表格中数值进行格式化
   */
  public static Object getCellValue(Cell cell) {
    Object value = null;
    DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
    DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字
    if (cell != null) {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
          value = cell.getRichStringCellValue().getString();
          break;
        case Cell.CELL_TYPE_NUMERIC:
          if ("General".equals(cell.getCellStyle().getDataFormatString())) {
            value = df.format(cell.getNumericCellValue());
          } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
            value = sdf.format(cell.getDateCellValue());
          } else {
            value = df2.format(cell.getNumericCellValue());
          }
          break;
        case Cell.CELL_TYPE_BOOLEAN:
          value = cell.getBooleanCellValue();
          break;
        case Cell.CELL_TYPE_BLANK:
          value = "";
          break;
        case Cell.CELL_TYPE_ERROR:
          value = "";
          break;
        default:
          break;
      }
    } else {
      value = "";
    }
    return value;
  }

  public static void main(String[] args) throws Exception {
    File file = new File(System.getProperty("user.dir") + "\\" + "test.xlsx");
    FileInputStream fis = new FileInputStream(file);
    List<List<RowFormat>> lists = readExcel(fis, file.getName());
    System.out.println(JSON.toJSONString(lists));
  }
}
