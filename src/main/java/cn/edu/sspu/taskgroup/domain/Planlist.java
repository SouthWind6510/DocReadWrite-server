package cn.edu.sspu.taskgroup.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class Planlist {
    @TableId(type = IdType.NONE)
    private int id;
    private int Sno;
    private  String task;
    private Date dateBegin;
    private Date dateEnd;
    private  String studentNo;
    private  String teacherNo;
    private String remark;
}
