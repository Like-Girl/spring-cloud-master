package com.mybatis.generator.mapper;

import com.mybatis.generator.model.TmsOrder;
import java.util.List;

public interface TmsOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(TmsOrder record);

    TmsOrder selectByPrimaryKey(Long orderId);

    List<TmsOrder> selectAll();

    int updateByPrimaryKey(TmsOrder record);
}