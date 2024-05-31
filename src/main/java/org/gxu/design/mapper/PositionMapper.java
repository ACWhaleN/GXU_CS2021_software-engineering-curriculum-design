package org.gxu.design.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.gxu.design.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @description 针对表【position】的数据库操作Mapper
 * @createDate 2024-01-18 20:18:27
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {
    List<Position> selectList(@Param("ew") Wrapper<Position> queryWrapper);
    Position selectById(int id);
    int insert(Position position);
    int updateById(Position position);
    int deleteById(int id);
}