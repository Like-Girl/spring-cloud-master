package cn.likegirl.rt.controller.excel;


import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
public class ExcelController {

  @RequestMapping(value = "/transfer/html")
  @ResponseBody
  public Result transferHtml(ExcelTransferHtmlReq req) {
    try {
      System.out.println(req);
      MultipartFile file = req.getFile();
      InputStream inputStream = file.getInputStream();
      Map<Integer, RowFormat> lists = ExcelTransferGridUtil.readExcel(inputStream,
          file.getOriginalFilename(), new ExcelAnalyzeCallback() {
            @Override
            public void beforeAnalyze(Sheet sheet) throws Exception {
//              int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
//              if(rowNum > 100){
//                throw new Exception("不能超过5行！");
//              }
            }

            @Override
            public void afterAnalyze(Object... params) {
            }
          });
      return PlatformResult.success(lists);
    } catch (Exception e) {
      e.printStackTrace();
      return PlatformResult.failure("文件解析错误");
    }
  }


  @RequestMapping(value = "/compute/header")
  @ResponseBody
  public Result computeHeader(ExcelComputeHeaderReq req) {
    try {
      MultipartFile file = req.getFile();
      InputStream inputStream = file.getInputStream();
      Map<Integer, RowFormat> lists = ExcelTransferGridUtil.readExcel(inputStream,
          file.getOriginalFilename(), new ExcelAnalyzeCallback() {
            @Override
            public void beforeAnalyze(Sheet sheet) {
            }

            @Override
            public void afterAnalyze(Object... params) {
            }
          });
      List<RowFormat> selectorHeader = lists.entrySet()
          .stream()
          .filter((e) -> e.getKey() >= req.getRowStart() && e.getKey() <= req.getRowEnd())
          .map(Entry::getValue).collect(Collectors.toList());
      // 获取最后一行
      RowFormat header = selectorHeader.stream().reduce((first,end) -> end)
          .orElseThrow(() -> new Exception("参数错误"));
      // 校验是否有跨列单元格
      Predicate<CellFormat> colspanFuc = cell -> {
        if(cell.getMerged() && cell.getMergedCol() > 1){
          return Boolean.TRUE;
        }
        return Boolean.FALSE;
      };
      if(header.getCellFormats().stream().anyMatch(colspanFuc)){
        throw new Exception("存在合并单元格");
      }
      return PlatformResult.success(header.getCellFormats());
    } catch (Exception e) {
      e.printStackTrace();
      return PlatformResult.failure("文件解析错误");
    }
  }

}
