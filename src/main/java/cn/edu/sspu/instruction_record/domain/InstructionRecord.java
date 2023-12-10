package cn.edu.sspu.instruction_record.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstructionRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sno;
    private String studentNo;
    private String instructionContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
