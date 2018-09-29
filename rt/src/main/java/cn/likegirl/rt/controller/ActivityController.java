package cn.likegirl.rt.controller;

import cn.likegirl.rt.framework.annotations.ResponseResult;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: ActivityController
 * @description: TODO
 * @date 2018/9/18 10:11
 */
@ResponseResult
@RestController
@RequestMapping("/")
public class ActivityController extends BaseController {

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result save() {
        return PlatformResult.success();
    }
}
