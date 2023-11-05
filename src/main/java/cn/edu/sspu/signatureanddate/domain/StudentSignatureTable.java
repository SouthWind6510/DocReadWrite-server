package cn.edu.sspu.signatureanddate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.BlobTypeHandler;

@Data
public class StudentSignatureTable {
    @TableId(type = IdType.NONE)
    public String studentNo;
    public String studentName;
    @TableField(typeHandler = BlobTypeHandler.class)
    byte[] studentSignature;
}
