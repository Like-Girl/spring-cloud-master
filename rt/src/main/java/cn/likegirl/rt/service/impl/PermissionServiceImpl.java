package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.model.Permission;
import cn.likegirl.rt.service.base.AbstractService;
import cn.likegirl.rt.service.PermissionService;
import cn.likegirl.rt.tools.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {


}
