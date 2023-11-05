package cn.edu.sspu.signatureanddate.service.implement;

import cn.edu.sspu.signatureanddate.dao.TeacherSignatureTableDAO;
import cn.edu.sspu.signatureanddate.domain.TeacherSignatureTable;
import cn.edu.sspu.signatureanddate.service.ITeacherSignatureTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class TeacherSignatureTableimpl extends ServiceImpl<TeacherSignatureTableDAO, TeacherSignatureTable> implements ITeacherSignatureTableService {
}
