package org.gxu.design.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.gxu.design.entity.User;
import org.gxu.design.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.gxu.design.util.Result;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Transactional
public class UserController {

    UserMapper userMapper;
    RedisTemplate redisTemplate;
    @Resource(name = "redisLock")
    ReentrantLock redisLock;


    @Autowired
    public UserController(UserMapper userMapper, RedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 用户注册功能
     */
    @PostMapping("register")
    public Result<?> userRegister(@RequestBody User user){
        if (Objects.isNull(user)) {
            return Result.error("参数不合法!!!");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,user.getAccount());
        boolean exists = userMapper.exists(queryWrapper);
        // 账户是否已存在
        if (exists){
            return Result.error("用户已存在!!!");
        }else{
            userMapper.insert(user);
            return  Result.success(user);
        }
    }

    /**
     * 普通用户登录功能
     */
    @PostMapping ("login")
    public Result<?> login(@RequestBody User user){
        if (Objects.isNull(user)){
            return Result.error("参数不合法!!!!");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,user.getAccount()).eq(User::getPassword,user.getPassword());
        List<User> res = userMapper.selectList(queryWrapper);
        if (Objects.isNull(res) ||res.isEmpty()){
            return Result.error("用户信息输入信息有无！！！");
        }
        return Result.success(res);
    }

    /**
     * 用户修改个人信息
     */
    @PostMapping("alter")
    public Result<?> updateUserInfo(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success(user);
    }

}
