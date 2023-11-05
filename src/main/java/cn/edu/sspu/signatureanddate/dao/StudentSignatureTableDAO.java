package cn.edu.sspu.signatureanddate.dao;

import cn.edu.sspu.signatureanddate.domain.StudentSignatureTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StudentSignatureTableDAO extends BaseMapper<StudentSignatureTable> {
}
