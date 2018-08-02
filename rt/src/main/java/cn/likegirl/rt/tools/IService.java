package cn.likegirl.rt.tools;

import java.util.List;

/**
 * 通用Service
 */
public interface IService<T> {

    /**
     * 新增
     */
    void insert(T t);

    /**
     * 批量新增
     */
    void batchInsert(List<T> t);

    /**
     * 通过主键删除
     */
    void deleteById(Object id);

    /**
     * 通过主键批量删除
     */
    void batchDeleteById(List<Object> ids);

    /**
     * 根据主键更新
     */
    void update(T t);

    /**
     * 根据主键更新
     *
     * @param selective 是否有选择性的更新
     *                  true    更新属性不为null的值
     *                  false   null值会被更新
     */
    void update(T t,boolean selective);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     */
    T getById(Object id);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     */
    T get(T t);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     */
    List<T> find(T t);

}
