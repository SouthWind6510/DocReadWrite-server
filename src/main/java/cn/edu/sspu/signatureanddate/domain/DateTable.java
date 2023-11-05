package cn.edu.sspu.signatureanddate.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class DateTable {
    @TableId
    public String StudentNo;//学号
    public String StudentName;//姓名
    public String teacherNo;//教师工号
    public Date taskBeginDate;//任务书开始日期
    public Date taskEndDate;//任务书结束日期
    public Date researchProposalTeacherDate;//开题老师日期
    public Date researchProposalDepartmentDate;//开题系日期
    public Date midCheckDate;//中期检查时间
    public Date handbookBeginDate;//手册开始时间
    public Date handbookEndDate;//手册结束时间
    public Date adviseReviewDate;//指导老师评阅日期
    public Date reviewDate;//评阅日期
    public Date defenseDate;//答辩日期
    public Date committeeDate;//委员会日期
    public Date paperCoverDate;//论文封面日期
}
