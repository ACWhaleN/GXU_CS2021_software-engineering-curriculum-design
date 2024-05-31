package org.gxu.design.design;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.gxu.design.entity.*;
import org.gxu.design.mapper.*;
import org.gxu.design.service.UserService;
import org.gxu.design.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class DataGenerate {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public UserService userService;

    // 图片上传路径
    @Value("${myProject.imgUpload.path}")
    String uploadImgSrc;

    @Test
    public void generateUsers(){
        List<User> res = new ArrayList<>();
        for(int i = 0 ; i < 20;i++){
            User user = new User();
            user.setAccount(RandomUtil.randomString("li",10));
            user.setPassword("123456");
            user.setName("xgc");

            user.setSex("男");
            user.setPhone("11111");
            user.setMailbox("dwdad@qq");

            res.add(user);

        }
        userService.saveBatch(res);
    }

    @Test
    public void selectUser(){
        User user = new User();
        user.setAccount("2007310446");
        user.setPassword("123456");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, user.getAccount()).eq(User::getPassword, user.getPassword());
        List<User> res = userMapper.selectList(queryWrapper);
        System.out.println(res);
    }






}
