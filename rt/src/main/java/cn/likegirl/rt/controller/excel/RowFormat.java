package cn.likegirl.rt.controller.excel;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RowFormat {

  private List<CellFormat> cellFormats = new ArrayList<>();


  public void addCellFormat(CellFormat cellFormat){
    this.cellFormats.add(cellFormat);
  }

}
