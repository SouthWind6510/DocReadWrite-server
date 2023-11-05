package cn.edu.sspu.taskgroup.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class TaskForm {
    @TableId(type = IdType.NONE)
    public String Sno;
    public String execDate;
    public String billofMaterials;
    public String expectedConclusion;
    public String literature;
    public String studyContent;
}
