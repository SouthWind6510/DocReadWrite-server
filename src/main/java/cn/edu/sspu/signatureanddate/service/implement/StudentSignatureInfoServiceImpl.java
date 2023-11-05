package cn.edu.sspu.signatureanddate.service.implement;

import cn.edu.sspu.signatureanddate.dao.StudentSignatureInfoDAO;
import cn.edu.sspu.signatureanddate.domain.StudentSignatureInfo;
import cn.edu.sspu.signatureanddate.service.IStudentSignatureInfoService;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class StudentSignatureInfoServiceImpl extends ServiceImpl<StudentSignatureInfoDAO, StudentSignatureInfo> implements IStudentSignatureInfoService {

    public R getOne() {
        return R.ok(200, "a", null);
    }
}
