package cn.likegirl.rt.controller.excel;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellFormat implements Serializable {

  public CellFormat(Integer index, String value) {
    this.index = index;
    this.value = value;
    this.merged = false;
    this.display = true;
    this.mergedCol = 1;
    this.mergedRow = 1;
  }

  /**
   * 单元格角标
   */
  private Integer index;

  /**
   * 单元格值
   */
  private String value;

  /**
   * 合并单元格
   */
  private Boolean merged;

  /**
   * 单元格渲染
   */
  private Boolean display;

  /**
   * 起始列角标
   */
  private Integer firstColumn;

  /**
   * 结束列角标
   */
  private Integer lastColumn;

  /**
   * 起始行角标
   */
  private Integer firstRow;

  /**
   * 结束行角标
   */
  private Integer lastRow;

  /**
   * 单元格合并列数
   */
  private Integer mergedCol;

  /**
   * 单元格合并行数
   */
  private Integer mergedRow;

}
