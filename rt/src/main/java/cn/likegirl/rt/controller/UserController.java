package cn.likegirl.rt.controller;

import cn.likegirl.rt.framework.annotations.ResponseResult;
import cn.likegirl.rt.framework.exception.DataConflictException;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import cn.likegirl.rt.model.Permission;
import cn.likegirl.rt.model.User;
import cn.likegirl.rt.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

@ResponseResult
@RestController
public class UserController {

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result save(@PathVariable(value = "id") Long id){
        if(id > 50){
            throw new DataConflictException();
        }
        Permission permission = new Permission("菜单", "menu", true);
        return PlatformResult.success(permission);
    }

    @RequestMapping(value = "/login1",method = RequestMethod.POST)
    public Result login1(@RequestBody User user){
        System.out.println(user);
        return PlatformResult.success(JwtUtil.buildJWT(user.getUsername()));
    }
}
