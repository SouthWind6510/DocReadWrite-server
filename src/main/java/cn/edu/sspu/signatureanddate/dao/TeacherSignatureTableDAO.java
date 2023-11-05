package cn.edu.sspu.signatureanddate.dao;

import cn.edu.sspu.signatureanddate.domain.TeacherSignatureTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TeacherSignatureTableDAO extends BaseMapper<TeacherSignatureTable> {
}
