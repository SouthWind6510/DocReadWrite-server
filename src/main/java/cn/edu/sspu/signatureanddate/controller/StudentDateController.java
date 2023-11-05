package cn.edu.sspu.signatureanddate.controller;

import cn.edu.sspu.signatureanddate.domain.DateTable;
import cn.edu.sspu.signatureanddate.domain.StudentSignatureInfo;
import cn.edu.sspu.signatureanddate.service.implement.DateTableServiceImpl;
import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureInfoServiceImpl;
import cn.edu.sspu.signatureanddate.service.implement.TeacherSignatureTableimpl;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/studentdate")
public class StudentDateController {
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    DateTableServiceImpl dateTableService;

    @PostMapping("/updateallthestudentdate")
    public R updateallthestudentdate(@RequestBody DateTable date) {
        ArrayList<DateTable> studentSignatureInfos = new ArrayList<>();
        QueryWrapper<DateTable> quaryWapper = new QueryWrapper<>();
        quaryWapper.eq("teacher_no", date.getTeacherNo());
        List<DateTable> list = dateTableService.list(quaryWapper);
        for (DateTable dateTable : list) {
            DateTable temp = new DateTable();
            BeanUtils.copyProperties(date, temp);
            temp.setStudentNo(dateTable.getStudentNo());
            temp.setStudentName(dateTable.getStudentName());
            temp.setTeacherNo(date.getTeacherNo());
            System.out.println(temp);
            dateTableService.updateById(temp);
        }
               return R.ok(200, "success", null);
    }


    @PostMapping("/updateonestudentdate")
    public R saveoneStudentDate(@RequestBody DateTable dateTable) {
        System.out.println(dateTable);
        UpdateWrapper<DateTable> updateWrapper = new UpdateWrapper<>();


        boolean b = dateTableService.updateById(dateTable);
        if (b)
            return R.ok(200, "成功", null);
        else
            return R.fail(400, "失败", null);
    }


    @RequestMapping("/teacher/{teacherNo}")
    public R getStudentDateInfo(@PathVariable String teacherNo) {
        System.out.println("in datetable controller");
        ArrayList<DateTable> dateTables = new ArrayList<>();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_no", teacherNo);
        List<Student> list = studentService.list(queryWrapper);
        for (Student student : list) {
            String sno = student.getSno();
            QueryWrapper<DateTable> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("student_no", sno);
            DateTable dateTable = dateTableService.getOne(queryWrapper2);
            if (dateTable != null) {
               dateTables.add(dateTable);
            } else {
                DateTable dateTable1 = new DateTable();
                dateTable1.setStudentNo(sno);
                dateTable1.setStudentName(student.getStudentName());
                dateTable1.setTeacherNo(student.getTeacherNo());
                dateTableService.save(dateTable1);
                dateTables.add(dateTable1);

            }
        }
        if (dateTables.size() > 0)
            return R.ok(200, "成功", dateTables);
        else
            return R.fail(400, "失败,无此教师数据", null);
    }





    @PostMapping("/gettaskexecdate")
    public R getTaskExecDate(@RequestBody Map<String,String> student){
        String studentNo = student.get("currentStudentSno");
        QueryWrapper<DateTable> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("student_no", studentNo);
        DateTable studentExecDate = dateTableService.getOne(queryWrapper2);
        if (studentExecDate != null){
           String resultDate =  "自" + studentExecDate.getTaskBeginDate()
                   + "起，至" +studentExecDate.getTaskEndDate()+"止。";
            return  R.ok(200,"success", resultDate);
        }
        else
            return  R.fail(400, "fail","请联系指导老师设定时间！");

    }


    @RequestMapping("/student/{studentNo}")
    public R getStudentdate(@PathVariable String studentNo) {
        QueryWrapper<DateTable> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("student_no", studentNo);
        DateTable studentdate = dateTableService.getOne(queryWrapper2);
        if (studentdate != null)
            return R.ok(200, "成功", studentdate);
        else
            return R.fail(400, "失败,无此学生数据", null);
    }
}
