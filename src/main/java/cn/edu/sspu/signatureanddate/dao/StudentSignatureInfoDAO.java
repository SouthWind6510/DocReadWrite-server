package cn.edu.sspu.signatureanddate.dao;

import cn.edu.sspu.signatureanddate.domain.StudentSignatureInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StudentSignatureInfoDAO extends BaseMapper<StudentSignatureInfo> {
}
