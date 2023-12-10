package cn.edu.sspu.instruction_record.dao;

import cn.edu.sspu.instruction_record.domain.InstructionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface InstructionRecordDAO extends BaseMapper<InstructionRecord> {
}
