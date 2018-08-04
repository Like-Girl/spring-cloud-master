package cn.likegirl.rt.service;

import cn.likegirl.rt.model.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by HD on 2018/1/12.
 * @author HD
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Test
    public void test2(){
        boolean result = Optional.ofNullable(userService.listByPage(1, 2)).map(users->{
            users.forEach(user->System.err.println(user.getUsername()));
            return true;
        }).orElse(false);
        System.err.println(result);

    }

    @Test
    public void test(){
        System.out.println(permissionService.getById(1L));
        // 插入一条实验
        Permission permission = new Permission("user:hello3","测试3",true);
        permissionService.insert(permission);
        System.out.println(permission);
    }

    @Test
    public void test5(){
        permissionService.find(new Permission(null,null,null)).forEach(System.out::println);
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test4(){
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set("sex", "女");
        System.err.println(vo.get("sex"));
    }

}
