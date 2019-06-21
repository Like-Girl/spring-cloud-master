package cn.likegirl.rt.controller.excel;


import org.apache.poi.ss.usermodel.Sheet;

/**
 * Description :   方法回调
 *
 * @author : LikeGirl
 * @date : Created in 2019/6/20 16:17
 */
public interface ExcelAnalyzeCallback {

	/**
   * 前置解析罗
	 * @param sheet
   * @return
   */
	void beforeAnalyze(Sheet sheet) throws Exception;

	/**
	 * 后置解析逻辑
	 * @param params
	 * @return
	 */
	void afterAnalyze(Object... params);
	
}