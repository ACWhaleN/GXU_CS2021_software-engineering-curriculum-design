package org.gxu.design.mapper;

import org.gxu.design.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 针对表【Department】的数据库操作Mapper
 * @createDate 2024-01-18 20:18:27
 * @Entity org.gxu.design.entity.User
 */
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {
}

