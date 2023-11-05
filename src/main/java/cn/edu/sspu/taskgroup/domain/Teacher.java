package cn.edu.sspu.taskgroup.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Teacher {
    @TableId(type = IdType.NONE)
    public String teacherNo;
    public String password;
    public String teacherName;
    public int firstTimeLogin;
}
