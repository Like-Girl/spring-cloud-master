package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.model.Tutor;
import cn.likegirl.rt.service.TutorService;
import cn.likegirl.rt.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TutorServiceImpl extends BaseService<Tutor> implements TutorService {

}
