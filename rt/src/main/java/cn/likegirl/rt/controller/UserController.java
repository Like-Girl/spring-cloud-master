package cn.likegirl.rt.controller;

import cn.likegirl.rt.framework.annotations.ResponseResult;
import cn.likegirl.rt.framework.exception.DataConflictException;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import cn.likegirl.rt.model.Permission;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ResponseResult
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result save(@PathVariable(value = "id") Long id){
        if(id > 50){
            throw new DataConflictException();
        }
        Permission permission = new Permission("菜单", "menu", true);
        return PlatformResult.success(permission);
    }
}
