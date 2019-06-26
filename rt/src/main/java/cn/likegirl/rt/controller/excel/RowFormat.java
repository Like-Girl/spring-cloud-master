package cn.likegirl.rt.controller.excel;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class RowFormat {

  private LinkedList<CellFormat> cellFormats = new LinkedList<CellFormat>();


  public void addCellFormat(CellFormat cellFormat){
    this.cellFormats.add(cellFormat);
  }

  public CellFormat getCellFormat(int index){
    return cellFormats.get(index);
  }

  public void removeLast(){
    cellFormats.removeLast();
  }

  public int size(){
    return cellFormats.size();
  }

}
