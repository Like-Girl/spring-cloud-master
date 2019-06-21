package cn.likegirl.rt.controller.excel;


import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import java.io.InputStream;
import java.util.Map;
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
  public Result transferHtml(ExcelTransferHtmlReq req){
    try {
      System.out.println(req);
      MultipartFile file = req.getFile();
      InputStream inputStream = file.getInputStream();
      Map<Integer,RowFormat> lists = ExcelTransferGridUtil.readExcel(inputStream,
          file.getOriginalFilename(), new ExcelAnalyzeCallback() {
            @Override
            public void beforeAnalyze(Sheet sheet) throws Exception {
              int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
              if(rowNum > 100){
                throw new Exception("不能超过5行！");
              }
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

}
