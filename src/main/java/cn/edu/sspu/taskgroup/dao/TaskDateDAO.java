package cn.edu.sspu.taskgroup.dao;

import cn.edu.sspu.taskgroup.domain.Planlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TaskDateDAO extends BaseMapper<Planlist> {

}
