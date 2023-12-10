package cn.edu.sspu.instruction_record.controller;

import cn.edu.sspu.instruction_record.domain.InstructionRecord;
import cn.edu.sspu.instruction_record.service.implement.InstructionRecordServiceImpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/instruction_record")
public class InstructionRecordController {
    @Autowired
    InstructionRecordServiceImpl instructionRecordService;

    @GetMapping("/student_record/{currentStudent}")
    public List<InstructionRecord> getInstructionRecordListFromStudent(@PathVariable String currentStudent) {
        QueryWrapper<InstructionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_no", currentStudent);
        queryWrapper.orderBy(true, true, "sno");
        return instructionRecordService.list(queryWrapper);
    }

    @PutMapping("/update_record")
    public R updateInstructRecord(@RequestBody InstructionRecord instructionRecord) {
        QueryWrapper<InstructionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", instructionRecord.getId());
        if (instructionRecordService.update(instructionRecord, queryWrapper))
            return R.ok(200, "success", instructionRecord);
        else
            return R.fail(400, "fail", null);
    }

    @PostMapping("/add_record")
    public R insertInstructRecord(@RequestBody InstructionRecord instructionRecord) {
        Integer recordCount = instructionRecordService.getStudentRecordCount(instructionRecord.getStudentNo());
        instructionRecord.setSno(recordCount + 1);
        if (instructionRecordService.save(instructionRecord))
            return R.ok(200, "success", instructionRecord);
        else
            return R.fail(400, "fail", null);
    }

    @DeleteMapping("/delete_record/{recordID}")
    public R deleteInstructRecord(@PathVariable String recordID) {
        if (instructionRecordService.removeById(recordID))
            return R.ok(200, "success", null);
        else
            return R.fail(400, "fail", null);
    }
}