package cn.edu.sspu.signatureanddate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.BlobTypeHandler;
@Data
public class TeacherSignatureTable {
    @TableId(type = IdType.NONE)
    public String teacherNo;
    public String teacherName;
    @TableField(typeHandler = BlobTypeHandler.class)
    byte[] teacherSignature;
}
