package cn.edu.sspu.taskgroup.service.implement;

import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureTableImpl;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.util.JWTUtils;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginService {
    @Autowired
    StudentServiceImpl studentService;
//    @Autowired
//    RedisTemplate redisTemplate;

    @Autowired
    StudentSignatureTableImpl studentSignatureTable;


    @Transactional
    public R accountLogin(String userName, String password, String teacherstudentflag) {
        QueryWrapper<Student> studentwrapper = new QueryWrapper<Student>();
        studentwrapper.eq("Sno", userName).eq("password", password);
        Student student = studentService.getOne(studentwrapper);
        if (student != null) {
            String token = JWTUtils.getToken(student.getSno(), student.getStudentName());
//            System.out.println(token);
            student.setToken(token);
            if (student.getFirstTimeLogin() != 1) {
                return R.ok(201, "请上传学生签名和修改密码!", student);
            } else
                return R.ok(200, "学生登录成功!", student);
        } else {
            return R.fail(400, "账号或密码错误!", null);
        }
    }
}
