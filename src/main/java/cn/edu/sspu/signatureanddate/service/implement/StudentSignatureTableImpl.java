package cn.edu.sspu.signatureanddate.service.implement;

import cn.edu.sspu.signatureanddate.dao.StudentSignatureTableDAO;
import cn.edu.sspu.signatureanddate.domain.StudentSignatureTable;
import cn.edu.sspu.signatureanddate.service.IStudentSignatureTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class StudentSignatureTableImpl extends ServiceImpl<StudentSignatureTableDAO, StudentSignatureTable> implements IStudentSignatureTableService {
}
