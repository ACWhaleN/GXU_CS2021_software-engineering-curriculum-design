package org.gxu.design.controller.admin;
import org.gxu.design.entity.User;
import org.gxu.design.entity.Position;
import org.gxu.design.entity.Department;
import org.gxu.design.mapper.UserMapper;
import org.gxu.design.mapper.PositionMapper;
import org.gxu.design.mapper.DepartmentMapper;

import org.gxu.design.entity.ChangeRequest;
import org.gxu.design.mapper.ChangeRequestMapper;

import org.gxu.design.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminChangeRequestController")
@RequestMapping("/admin/change-request")
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

    @GetMapping("/pending")
    public Result<List<ChangeRequest>> listPendingChangeRequests() {
        List<ChangeRequest> pendingChangeRequests = changeRequestMapper.selectListByStatus("pending");
        return Result.success(pendingChangeRequests);
    }

    @PostMapping("/approve/{requestId}")
    public Result<String> approveChangeRequest(@PathVariable Integer requestId) {
        ChangeRequest request = changeRequestMapper.selectById(requestId);

        if (request == null) {
            return Result.error("请求不存在");
        }

        // 获取用户信息
        User user = userMapper.selectById(request.getUser().getId());
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 保存旧职位信息
        Position oldPosition = positionMapper.selectById(user.getPosition().getId());

        // 获取新职位信息
        Position newPosition = positionMapper.selectById(request.getRequestedPosition().getId());
        if (newPosition == null) {
            return Result.error("新职位不存在");
        }
        // 获取新部门信息
        Department newDepartment = departmentMapper.selectById(request.getRequestedDepartment().getId());
        if (newDepartment == null) {
            return Result.error("新部门不存在");
        }

        // 更新旧职位的状态
        if (oldPosition != null) {
            oldPosition.setStatus(oldPosition.getStatus() - 1);
            positionMapper.updateById(oldPosition);
        }

        // 更新用户的职位和部门信息
        user.setPosition(newPosition);
        user.setDepartment(newDepartment);
        userMapper.updateById(user);

        // 更新新职位的状态
        newPosition.setStatus(newPosition.getStatus() + 1);
        positionMapper.updateById(newPosition);

        // 更新请求状态为已审核
        request.setStatus("approved");
        changeRequestMapper.updateById(request);
        return Result.success("审核成功");
    }

    @PostMapping("/reject/{requestId}")
    public Result<String> rejectChangeRequest(@PathVariable Integer requestId) {
        ChangeRequest request = changeRequestMapper.selectById(requestId);
        if (request == null) {
            return Result.error("请求不存在");
        }

        // 更新请求状态为已拒绝
        request.setStatus("rejected");
        changeRequestMapper.updateById(request);

        return Result.success("已拒绝该请求");
    }

    @PutMapping("/update/{id}")
    public Result<String> updateChangeRequest(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestParam Integer positionId,
            @RequestParam Integer departmentId,
            @RequestParam String requestMessage,
            @RequestParam String status) {

        // 查找ChangeRequest实体类
        ChangeRequest changeRequest = changeRequestMapper.selectById(id);
        if (changeRequest == null) {
            return Result.error("找不到对应的请求");
        }

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

        // 更新ChangeRequest实体类的各个字段
        changeRequest.setUser(user);
        changeRequest.setPosition(position);
        changeRequest.setDepartment(department);
        changeRequest.setRequestMessage(requestMessage);
        changeRequest.setStatus(status);

        // 更新数据库中的记录
        int result = changeRequestMapper.updateById(changeRequest);

        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteChangeRequest(@PathVariable Integer id) {
        int result = changeRequestMapper.deleteById(id);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}
