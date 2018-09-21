package cn.likegirl.rt.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 继承HashMap
 * 扩展部分功能
 *
 * @param <K>   key
 * @param <V>   value
 * @author LikeGirl
 * @date 2018/09/21
 */
public class BusinessMap<K, V> extends HashMap<K, V> {

    private static final String TIMESTAMP_FORMAT = "^\\d+$";

    private static final String DEFAULT_DATE_TIME_FORMAT = "^\\d{4}(\\-)\\d{1,2}\\1\\d{1,2} \\d{2}:\\d{2}:\\d{2}$";

    private static final String DEFAULT_DATE_FORMAT = "^\\d{4}(\\-)\\d{1,2}\\1\\d{1,2}";

    private static final String  SLASH_DATE_TIME_FORMAT = "^\\d{4}(\\/)\\d{1,2}\\1\\d{1,2} \\d{2}:\\d{2}:\\d{2}$";

    private static final String  SLASH_DATE_FORMAT = "^\\d{4}(\\/)\\d{1,2}\\1\\d{1,2}$";

    public BusinessMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public BusinessMap(int initialCapacity) {
        super(initialCapacity);
    }

    public BusinessMap() {
        super();
    }

    public BusinessMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    static {
        ConvertUtils.register(new Converter() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> T convert(Class<T> type, Object value) {
                String val = Optional.ofNullable(value).map(String::valueOf).map(String::trim).orElse(null);
                if (StringUtils.isEmpty(val)) {
                    return null;
                }
                if(Pattern.matches(TIMESTAMP_FORMAT,val)){
                    return (T) new Date(Long.valueOf(val));
                }
                String dateFormat = null;
                if (Pattern.matches(DEFAULT_DATE_TIME_FORMAT, val)) {
                    dateFormat = "yyyy-MM-dd HH:mm:ss";
                }
                if (Pattern.matches(DEFAULT_DATE_FORMAT, val)) {
                    dateFormat = "yyyy-MM-dd";
                }
                if (Pattern.matches(SLASH_DATE_TIME_FORMAT, val)) {
                    dateFormat = "yyyy/MM/dd HH:mm:ss";
                }
                if (Pattern.matches(SLASH_DATE_FORMAT, val)) {
                    dateFormat = "yyyy-MM-dd";
                }
                if(null == dateFormat){
                    throw new RuntimeException("[java.util.Date]类型转换时异常：参数格式错误!");
                }
                try {
                    SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
                    return (T) sd.parse(val);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }, java.util.Date.class);
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    public <T> T get(Object key, Class<T> clazz) {
        return this.get(key,clazz,false);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> clazz, Boolean needThrow) {
        Object obj = Optional.ofNullable(super.get(key)).orElse(null);
        if(obj == null){
            if((needThrow == null ? false : needThrow)){
                throw new NullPointerException(key + " value is null");
            }
            return null;
        }
        Object convert = ConvertUtils.convert(obj, clazz);
        return (T) convert;
    }

    public <T> List<T> parseArray(Object key, Class<T> clazz) {
        return this.parseArray(key,clazz,false);
    }

    public <T> List<T> parseArray(Object key, Class<T> clazz, Boolean needThrow) {
        Object obj = Optional.ofNullable(super.get(key)).orElse(null);
        if(obj == null){
            if((needThrow == null ? false : needThrow)){
                throw new NullPointerException(key + " value is null");
            }
            return null;
        }
        return JSONObject.parseArray(JSONObject.toJSONString(obj),clazz);
    }

    public <T> T parseObject(String key, Class<T> clazz) {
        return this.parseObject(key,clazz,false);
    }

    public <T> T parseObject(String key, Class<T> clazz, Boolean needThrow) {
        Object obj = Optional.ofNullable(super.get(key)).orElse(null);
        if(obj == null){
            if((needThrow == null ? false : needThrow)){
                throw new NullPointerException(key + "value is null");
            }
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(obj),clazz);
    }


}
