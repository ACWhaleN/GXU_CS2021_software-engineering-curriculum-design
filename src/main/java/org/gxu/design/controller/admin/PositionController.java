package org.gxu.design.controller.admin;

import org.gxu.design.entity.Position;
import org.gxu.design.mapper.PositionMapper;
import org.gxu.design.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位管理 Controller
 */
@RestController
@RequestMapping("/admin/position")
public class PositionController {

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 查询所有职位
     */
    @GetMapping("/all")
    public Result<List<Position>> listPositions() {
        List<Position> positions = positionMapper.selectList(null);
        return Result.success(positions);
    }

    /**
     * 添加职位
     */
    @PostMapping("/add")
    public Result<String> addPosition(@RequestParam int id, @RequestParam int status, @RequestParam String name, @RequestParam String description) {
        Position position = new Position();
        position.setId(id);
        position.setName(name);
        position.setStatus(status);
        position.setDescription(description); // 设置 description 属性
        int result = positionMapper.insert(position);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }


    /**
     * 更新职位
     */
        @PutMapping("/update")
        public Result<String> updatePosition(@RequestBody Position position) {
            int rows = positionMapper.updateById(position);
            if (rows > 0) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        }

    /**
     * 删除职位
     */
    @DeleteMapping("/delete/{positionId}")
    public Result<String> deletePosition(@PathVariable int id) {
        int result = positionMapper.deleteById(id);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}
