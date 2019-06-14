package cn.likegirl.rt.controller.excel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ExcelTransferHtmlReq {

  private MultipartFile file;

  private Integer size;

}
