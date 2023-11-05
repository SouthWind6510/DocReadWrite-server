package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.domain.Planlist;
import cn.edu.sspu.taskgroup.service.implement.TaskDateServiceImpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/taskdate")
public class TaskDateController {
    @Autowired
    TaskDateServiceImpl taskDateService;

    @PostMapping("/uploadmajorunitedplan")
    public R uploadMajorUnitedPlan(@RequestParam("photoRef") MultipartFile photoRef,
                                   @RequestPart("teacherNo") String teacherNo) throws IOException {
        System.out.println("in uploadMajorUnitedPlan");
        return taskDateService.insertorUpdateStudentPlanlist(photoRef, teacherNo);
    }

    @GetMapping("/gettaskdate/{currentStudent}")
    public List<Planlist> getTaskDateforCurrentStudent(@PathVariable String currentStudent) {
        QueryWrapper<Planlist> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("student_no", currentStudent);
        queryWrapper1.orderBy(true,true,"sno");
        return taskDateService.list(queryWrapper1);
    }

    @GetMapping("/gettaskdateone/{studentNo}/{Sno}")
    public Planlist getTaaskDateonebyID(@PathVariable String Sno, @PathVariable String studentNo) {
        QueryWrapper<Planlist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sno",Sno).eq("student_no", studentNo);
        return taskDateService.getOne(queryWrapper);
    }

    @PutMapping("/updatetaskdateone")
    public R updateTaskDateone(@RequestBody Planlist planlist) {
        QueryWrapper<Planlist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sno", planlist.getSno()).eq("student_no", planlist.getStudentNo());
        if (taskDateService.update(planlist, queryWrapper))
            return R.ok(200, "success", planlist);
        else
            return R.fail(400, "fail", null);
    }
}
