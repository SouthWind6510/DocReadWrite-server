package cn.edu.sspu.signatureanddate.domain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class StudentSignatureInfo {
    @TableId
    private String studentNo;//学号
    private String studentName;//学号
    private String teacherNo;//教师工号
    private String professionalOfficer;//专业主任
    private String paperreViewer;  //评阅人
    private String respondentRecorder; //答辩记录人
    private String defenseTeamLeader;//答辩组长
    private String defenseTeacher1;//答辩教师1
    private String defenseTeacher2;//答辩教师2
    private String chairmanoftheDefenseCommittee; //答辩委员会主任
    private String enterpriseTeacher; //企业导师
    private int lockSignature;
}
