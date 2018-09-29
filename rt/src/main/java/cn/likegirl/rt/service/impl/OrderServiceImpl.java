package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.model.Order;
import cn.likegirl.rt.service.OrderService;
import cn.likegirl.rt.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends BaseService<Order> implements OrderService {
}
