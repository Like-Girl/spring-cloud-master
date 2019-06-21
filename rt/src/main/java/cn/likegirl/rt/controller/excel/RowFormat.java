package cn.likegirl.rt.controller.excel;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class RowFormat {

  private List<CellFormat> cellFormats = new LinkedList<CellFormat>();


  public void addCellFormat(CellFormat cellFormat){
    this.cellFormats.add(cellFormat);
  }

  public CellFormat getCellFormat(int index){
    return cellFormats.get(index);
  }

}
