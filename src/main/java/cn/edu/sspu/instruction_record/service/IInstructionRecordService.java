package cn.edu.sspu.instruction_record.service;

import cn.edu.sspu.instruction_record.domain.InstructionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IInstructionRecordService extends IService<InstructionRecord> {
    public Integer getStudentRecordCount(String sno);
}
