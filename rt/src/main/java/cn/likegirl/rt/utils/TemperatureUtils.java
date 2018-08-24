package cn.likegirl.rt.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemperatureUtils {

    private TemperatureUtils(){}

    /**
     * 温度要求格式化
     * @param typeName  温度类型
     * @param minTemp   最低温度
     * @param maxTemp   最高温度
     * @return          **(**℃~**℃)
     */
    public static String format(String typeName, BigDecimal minTemp,BigDecimal maxTemp){
        if(StringUtils.isEmpty(typeName)
                || minTemp == null
                || maxTemp == null){
            throw new RuntimeException("参数错误");
        }
        typeName = typeName.replaceAll("(\\([^\\)]*\\))","");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(typeName)
                .append("(")
                .append(minTemp.toString())
                .append("℃~")
                .append(maxTemp.toString())
                .append("℃)");
        return stringBuilder.toString();
    }

    /**
     * 解析温度要求
     * 示例数据： 冷冻(-18℃~0℃)
     * @return   Temp
     */
    public static Temp parse(String s){
        Temp temp = new Temp();
        String regex = "^(.*)\\([^\\(\\-0-9]*?\\-*?(\\-?[0-9]+\\.?[0-9]*?)[^\\(\\-0-9]*\\-*?(\\-?[0-9]+\\.?[0-9]*?)[^\\(0-9]*\\)$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);

        if(matcher.matches()){
            temp.setTypeTemp(matcher.group(1));
            temp.setMinTemp(BigDecimal.valueOf(Double.valueOf(matcher.group(2))));
            temp.setMaxTemp(BigDecimal.valueOf(Double.valueOf(matcher.group(3))));
        }
        return temp;
    }

    public static class Temp{
        /**
         * 温度类型
         */
        public String typeTemp;

        /**
         * 最低温度
         */
        public BigDecimal minTemp;

        /**
         * 最高温度
         */
        public BigDecimal maxTemp;

        public String getTypeTemp() {
            return typeTemp;
        }

        public void setTypeTemp(String typeTemp) {
            this.typeTemp = typeTemp;
        }

        public BigDecimal getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(BigDecimal minTemp) {
            this.minTemp = minTemp;
        }

        public BigDecimal getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(BigDecimal maxTemp) {
            this.maxTemp = maxTemp;
        }
    }
}
