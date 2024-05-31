package org.gxu.design.controller.admin;

import org.gxu.design.entity.Department;
import org.gxu.design.mapper.DepartmentMapper;
import org.gxu.design.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/department")
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/all")
    public Result<List<Department>> getAllDepartments() {
        List<Department> departments = departmentMapper.selectList(null);
        return Result.success(departments);
    }

    @GetMapping("/{id}")
    public Result<?>  getDepartmentById(@PathVariable int id) {
        Department department = departmentMapper.selectById(id);
        if (department == null) {
            return Result.error("Department not found");
        }
        return Result.success(department);
    }


    /**
     * 添加部门
     */
    @PostMapping("/add")
    public Result<String> addDepartment(@RequestParam int id, @RequestParam String name, @RequestParam String description) {
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setDescription(description);
        int result = departmentMapper.insert(department);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 更新部门
     */
    @PutMapping("/update/{departmentId}")
    public Result<String> updateDepartment(@PathVariable int id, @RequestParam String name, @RequestParam String description) {
        Department department = departmentMapper.selectById(id);
        if (department == null) {
            return Result.error("部门不存在");
        }
        department.setName(name);
        department.setDescription(description);
        int result = departmentMapper.updateById(department);
        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }


    @DeleteMapping("/{id}")
    public Result<String> deleteDepartment(@PathVariable int id) {
        int rows = departmentMapper.deleteById(id);
        if (rows > 0) {
            return Result.success("Department deleted successfully");
        } else {
            return Result.error("Failed to delete department");
        }
    }
}
