package cn.likegirl.rt.service.base;

import cn.likegirl.rt.config.database.BaseMapper;
import cn.likegirl.rt.config.database.ReadOnlyConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public void insert(T t) {
        baseMapper.insertSelective(t);
    }

    @Override
    public void batchInsert(List<T> t) {
        // 需要优化
        t.forEach(m -> baseMapper.insertSelective(m));
    }

    @Override
    public void deleteById(Object id) {
        baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void batchDeleteById(List<Object> ids) {
        // 需要优化
        ids.forEach(id -> baseMapper.deleteByPrimaryKey(id));
    }

    @Override
    public void update(T t) {
        this.update(t,true);
    }

    @Override
    public void update(T t,boolean selective) {
        if(selective){
            baseMapper.updateByPrimaryKeySelective(t);
        }else {
            baseMapper.updateByPrimaryKey(t);
        }
    }

    @Override
    @ReadOnlyConnection
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public T getById(Object id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    @ReadOnlyConnection
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public T get(T t) {
        return baseMapper.selectOne(t);
    }

    @Override
    @ReadOnlyConnection
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public List<T> find(T t) {
        return baseMapper.select(t);
    }
}