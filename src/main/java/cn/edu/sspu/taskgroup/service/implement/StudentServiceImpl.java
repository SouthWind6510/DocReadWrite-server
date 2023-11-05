package cn.edu.sspu.taskgroup.service.implement;

import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.IStudentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StudentServiceImpl extends ServiceImpl<cn.edu.sspu.taskgroup.dao.studentdao, Student> implements IStudentService {

    @Autowired
    cn.edu.sspu.taskgroup.dao.studentdao studentdao;
    @Override//超级管理员使用，可以看任何同学
    public IPage<Student> getUserPage(int currentPage, int pageSize) {
        IPage<Student> page = new Page<>(currentPage, pageSize);
        studentdao.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<Student> getUserPage(int currentPage, int pageSize, Student student) {
        LambdaQueryWrapper<Student> userLambdaQueryWrapper = new LambdaQueryWrapper<Student>();
        userLambdaQueryWrapper.like(Strings.isNotEmpty(student.getStudentName()), Student::getStudentName, student.getStudentName());
        userLambdaQueryWrapper.like(Strings.isNotEmpty(student.getTeacherNo()), Student::getTeacherNo, student.getTeacherNo());
        IPage<Student> page = new Page<>(currentPage, pageSize);
        this.studentdao.selectPage(page, userLambdaQueryWrapper);
        return page;
    }

    @Override
    public IPage<Student> getUserPage(String userName, int currentPage, int pageSize) {
        System.out.println("loging........");

        IPage<Student> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_no", userName);
        studentdao.selectPage(page, queryWrapper);
        return page;
    }
}
