package cn.edu.sspu.taskgroup.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class Student {//ctl+F12
    private String className;
    private String schoolName;
    private String major;
    @TableId(type = IdType.NONE)
    private String Sno;
    private String teacherNo;
    private String studentName;
    private String enterpriseTeacher;
    private String projectTitle;
    private String englishTitle;
    private String token;
    private String password;
    private int lockTask;
    private int firstTimeLogin;//首次登录后，将其设为1
}
