package org.gxu.design.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.gxu.design.entity.ChangeRequest;

import java.util.List;

@Mapper
public interface ChangeRequestMapper extends BaseMapper<ChangeRequest> {
    List<ChangeRequest> selectListByStatus(String pending);

}
