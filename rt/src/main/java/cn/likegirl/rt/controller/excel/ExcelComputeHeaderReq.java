package cn.likegirl.rt.controller.excel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ExcelComputeHeaderReq {

  private MultipartFile file;

  private Integer rowStart;

  private Integer rowEnd;

}
