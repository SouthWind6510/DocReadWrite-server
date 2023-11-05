package cn.edu.sspu.taskgroup.dao;

import cn.edu.sspu.taskgroup.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TeacherDAO extends BaseMapper<Teacher> {
}
