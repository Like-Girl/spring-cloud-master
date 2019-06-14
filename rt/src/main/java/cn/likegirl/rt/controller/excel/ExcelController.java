package cn.likegirl.rt.controller.excel;


import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      List<List<RowFormat>> lists = ExcelUtil.readExcel(inputStream, file.getOriginalFilename());
      return PlatformResult.success(lists.get(0));
    } catch (Exception e) {
      e.printStackTrace();
      return PlatformResult.failure("文件解析错误");
    }

  }

}
