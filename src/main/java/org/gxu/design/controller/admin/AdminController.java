package org.gxu.design.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.gxu.design.entity.Admin;
import org.gxu.design.entity.Position;
import org.gxu.design.entity.User;
import org.gxu.design.entity.Department;
import org.gxu.design.mapper.AdminMapper;
import org.gxu.design.mapper.UserMapper;
import org.gxu.design.mapper.PositionMapper;
import org.gxu.design.mapper.DepartmentMapper;
import org.gxu.design.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用户信息管理
 */
@RestController(value = "user_admin")
@RequestMapping("/admin")
@CrossOrigin
@Slf4j
@Data
@Transactional
@ConfigurationProperties("myproject.database")
// TODO: 2024/4/9 测试完毕
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;
    UserMapper userMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private DepartmentMapper departmentMapper;


    @GetMapping("user/all")
    public Result<List<User>> getAllUsers() {
        List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }

    @GetMapping("user/{id}")
    public Result<?> getUserById(@PathVariable Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("User not found");
        }
        return Result.success(user);
    }

    @PostMapping("user/add")
    public Result<String> addUser(@RequestParam Integer id, @RequestParam String account, @RequestParam String password,
                                  @RequestParam String name, @RequestParam String nickName,
                                  @RequestParam Integer positionId, @RequestParam Integer departmentId,
                                  @RequestParam String sex, @RequestParam String phone,
                                  @RequestParam String mailbox) {
        User user = new User();
        user.setId(id);
        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setNickName(nickName);

        Position position = positionMapper.selectById(positionId);
        Department department = departmentMapper.selectById(departmentId);
        if (position == null || department == null) {
            return Result.error("Invalid position or department ID");
        }
        //更新职位状态
        position.setStatus(position.getStatus()+1);
        positionMapper.updateById(position);
        user.setPosition(position);
        user.setDepartment(department);

        user.setSex(sex);
        user.setPhone(phone);
        user.setMailbox(mailbox);

        int result = userMapper.insert(user);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    @PutMapping("user/update/{id}")
    public Result<String> updateUser(@PathVariable Integer id, @RequestParam String account, @RequestParam String password,
                                     @RequestParam String name, @RequestParam String nickName,
                                     @RequestParam Integer positionId, @RequestParam Integer departmentId,
                                     @RequestParam String sex, @RequestParam String phone,
                                     @RequestParam String mailbox) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("User not found");
        }

        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setNickName(nickName);
        //找到原先的职位将status-1，存入数据库后再找出新职位再+1
        Position oldPosition = positionMapper.selectById(user.getPosition().getId());
        if (oldPosition == null ) {
            return Result.error("Invalid position ID");
        }
        oldPosition.setStatus(oldPosition.getStatus()-1);
        positionMapper.updateById(oldPosition);

        Position newPosition = positionMapper.selectById(positionId);
        Department department = departmentMapper.selectById(departmentId);
        if (newPosition == null || department == null ) {
            return Result.error("Invalid position or department ID");
        }
        newPosition.setStatus(newPosition.getStatus()+1);
        positionMapper.updateById(newPosition);
        user.setPosition(newPosition);
        user.setDepartment(department);
        user.setSex(sex);
        user.setPhone(phone);
        user.setMailbox(mailbox);

        int result = userMapper.updateById(user);
        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @DeleteMapping("user/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        User user=userMapper.selectById(id);
        Position position = positionMapper.selectById(user.getPosition().getId());
        position.setStatus(position.getStatus()-1);
        positionMapper.updateById(position);
        int rows = userMapper.deleteById(id);
        if (rows > 0) {
            return Result.success("User deleted successfully");
        } else {
            return Result.error("Failed to delete user");
        }
    }

    /**
     * 管理员登录
     *
     * @param admin
     * @return
     */
    // TODO: 2024/4/9  已测试
    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin) {
        log.info("data:{}", admin);
        if (Objects.isNull(admin)) {
            return Result.error("提交的参数为空！！！");
        }
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAccount, admin.getAccount()).eq(Admin::getPassword, admin.getPassword());
        List<Admin> admins = adminMapper.selectList(queryWrapper);
        if (Objects.isNull(admins) || admins.isEmpty()) {
            return Result.error("没有此账户!!!");
        }
        return Result.success(admins.get(0));
    }

    /**
     * 获取管理员账户的信息
     *
     * @return
     */
    // TODO: 2024/4/9 已测试
    @RequestMapping("/getAdminMsg")
    public Result<?> getAdminMsg() {
        List<Admin> admin = adminMapper.selectList(null);
        if (Objects.isNull(admin)) {
            return Result.error("获取信息异常");
        }
        return Result.success(admin);
    }

    /**
     * 管理员更新个人信息
     *
     * @param admin
     * @return
     */
    // TODO: 2024/4/9 已测试
    @RequestMapping({"/updateAdminMsg", "/updateAdminPwd"})
    public Result<String> updateAdminMsg(@RequestBody Admin admin) {
        if (Objects.isNull(admin)) {
            return Result.error("请求参数不合法!");
        }
        int row = adminMapper.updateById(admin); // 受影响行数
        if (row == 0) {
            return Result.error("修改失败");
        }
        return Result.success("操作成功!!!");
    }

}