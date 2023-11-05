package cn.edu.sspu.taskgroup.dao;

import cn.edu.sspu.taskgroup.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface studentdao extends BaseMapper<Student> {
//    @Select("select * from student where Sno=#{id}")
//    public Student getStudentByID(String id);
}
