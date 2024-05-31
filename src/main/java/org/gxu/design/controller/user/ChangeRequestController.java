package org.gxu.design.controller.user;

import org.gxu.design.entity.ChangeRequest;
import org.gxu.design.entity.User;
import org.gxu.design.entity.Position;
import org.gxu.design.entity.Department;
import org.gxu.design.mapper.ChangeRequestMapper;
import org.gxu.design.mapper.DepartmentMapper;
import org.gxu.design.mapper.PositionMapper;
import org.gxu.design.mapper.UserMapper;
import org.gxu.design.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/change-request")
public class ChangeRequestController {

    @Autowired
    private ChangeRequestMapper changeRequestMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @GetMapping("/all")
    public Result<List<ChangeRequest>> listChangeRequests() {
        List<ChangeRequest> changeRequests = changeRequestMapper.selectList(null);
        return Result.success(changeRequests);
    }

    @PostMapping("/add")
    public Result<String> addChangeRequest(@RequestParam Integer userId,
                                           @RequestParam Integer positionId,
                                           @RequestParam Integer departmentId,
                                           @RequestParam String requestMessage) {
        // 查找关联的User实体类
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("找不到对应的用户");
        }

        // 查找关联的Position实体类
        Position position = positionMapper.selectById(positionId);
        if (position == null) {
            return Result.error("找不到对应的职位");
        }

        // 查找关联的Department实体类
        Department department = departmentMapper.selectById(departmentId);
        if (department == null) {
            return Result.error("找不到对应的部门");
        }

        // 创建一个新的ChangeRequest实体对象
        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setUser(user);
        changeRequest.setPosition(position);
        changeRequest.setDepartment(department);
        changeRequest.setRequestMessage(requestMessage);
        changeRequest.setStatus("pending");

        // 插入数据库中的记录
        int result = changeRequestMapper.insert(changeRequest);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }


}
