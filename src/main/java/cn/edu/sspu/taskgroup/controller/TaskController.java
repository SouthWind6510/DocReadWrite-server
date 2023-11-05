package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    StudentServiceImpl studentService;

    @GetMapping
    public List<Student> getStudentAll() {
        return studentService.list();
    }

    @ResponseBody
    @GetMapping("{currentPage}/{pageSize}")
    IPage getUserPage(@PathVariable int currentPage, @PathVariable int pageSize, Student student) {
        System.out.println("student--------------------" + student);
        IPage page = studentService.getUserPage(currentPage, pageSize);
        return page;
    }


    @ResponseBody
    @GetMapping("/{userName}/{currentPage}/{pageSize}")
    IPage getUserPage(@PathVariable String userName,@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage page = studentService.getUserPage(userName,currentPage, pageSize);
        return page;
    }



    @GetMapping("/gettaskheadone/{currentStudentSno}")
    public Student gettaskheadone(@PathVariable String currentStudentSno) {
        return studentService.getById(currentStudentSno);
     }
}
