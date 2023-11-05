package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.domain.Teacher;
import cn.edu.sspu.taskgroup.service.implement.TeacherServiceImpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;

    @PostMapping("/login")
    public R teacherLogin(@RequestBody Map<String, String> map) {
        String loginRole = map.get("loginRole");
        String userName = map.get("userName");
        String password = map.get("password");
        QueryWrapper<Teacher> teacherwrapper = new QueryWrapper();
        teacherwrapper.eq("teacher_no", userName).eq("password", password);
        Teacher teacher = teacherService.getOne(teacherwrapper);
        System.out.println("teacher:" + teacher);
        if (teacher != null) {
            if (teacher.getFirstTimeLogin() != 1) {
                return R.ok(201, "请上传教师签名和修改密码!", teacher);
            } else
                return R.ok(200, "教师登录成功!", teacher);
        } else {
            return R.fail(400, "账号或密码错误!", null);
        }
    }
}
