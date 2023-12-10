package cn.edu.sspu.instruction_record.service.implement;

import cn.edu.sspu.instruction_record.dao.InstructionRecordDAO;
import cn.edu.sspu.instruction_record.domain.InstructionRecord;
import cn.edu.sspu.instruction_record.service.IInstructionRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructionRecordServiceImpl extends ServiceImpl<InstructionRecordDAO, InstructionRecord> implements IInstructionRecordService {
    @Autowired
    InstructionRecordDAO instructionRecordDAO;

    @Override
    public Integer getStudentRecordCount(String sno) {
        QueryWrapper<InstructionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_no", sno);
        return instructionRecordDAO.selectCount(queryWrapper);
    }
}
