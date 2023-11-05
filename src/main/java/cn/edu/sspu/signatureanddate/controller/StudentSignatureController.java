package cn.edu.sspu.signatureanddate.controller;

import cn.edu.sspu.signatureanddate.domain.StudentSignatureInfo;
import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureInfoServiceImpl;
import cn.edu.sspu.signatureanddate.service.implement.TeacherSignatureTableimpl;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/studentsignature")
public class StudentSignatureController {
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    StudentSignatureInfoServiceImpl studentSignatureInfoService;
    @Autowired
    TeacherSignatureTableimpl teacherSignatureTableimpl;

    @PostMapping("/updateallthestudentSignature")
    public R updateallthestudentSignature(@RequestBody StudentSignatureInfo studentSignatureInfo) {
        ArrayList<StudentSignatureInfo> studentSignatureInfos = new ArrayList<>();
        QueryWrapper<StudentSignatureInfo> quaryWapper = new QueryWrapper<>();
        quaryWapper.eq("teacher_no", studentSignatureInfo.getTeacherNo());
        List<StudentSignatureInfo> list = studentSignatureInfoService.list(quaryWapper);
        for (StudentSignatureInfo signatureInfo : list) {
            signatureInfo.setEnterpriseTeacher(studentSignatureInfo.getEnterpriseTeacher());
            signatureInfo.setChairmanoftheDefenseCommittee(studentSignatureInfo.getChairmanoftheDefenseCommittee());
            signatureInfo.setDefenseTeacher1(studentSignatureInfo.getDefenseTeacher1());
            signatureInfo.setDefenseTeacher2(studentSignatureInfo.getDefenseTeacher2());
            signatureInfo.setPaperreViewer(studentSignatureInfo.getPaperreViewer());
            signatureInfo.setDefenseTeamLeader(studentSignatureInfo.getDefenseTeamLeader());
            signatureInfo.setProfessionalOfficer(studentSignatureInfo.getProfessionalOfficer());
            signatureInfo.setRespondentRecorder(studentSignatureInfo.getRespondentRecorder());
            studentSignatureInfoService.updateById(signatureInfo);
        }
        return R.ok(200, "success", null);
    }

    @PostMapping("/updateonestudentSignature")
    public R saveoneTeacherSignature(@RequestBody StudentSignatureInfo studentSignatureInfo) {
        System.out.println(studentSignatureInfo);
        UpdateWrapper<StudentSignatureInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enterprise_teacher", studentSignatureInfo.getEnterpriseTeacher());
        if (studentSignatureInfoService.updateById(studentSignatureInfo))
            return R.ok(200, "成功", null);
        else
            return R.fail(400, "失败", null);
    }


    @RequestMapping("/teacher/{teacherNo}")
    public R getStudentSignatureInfo(@PathVariable String teacherNo) {
        ArrayList<StudentSignatureInfo> studentSignatureInfo = new ArrayList<>();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_no", teacherNo);
        List<Student> list = studentService.list(queryWrapper);
        for (Student student : list) {
            String sno = student.getSno();
            QueryWrapper<StudentSignatureInfo> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("student_no", sno);
            StudentSignatureInfo studentSig = studentSignatureInfoService.getOne(queryWrapper2);
            if (studentSig != null) {


                if (studentSig.getTeacherNo()!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(teacherNo).getTeacherName();
                    studentSig.setTeacherNo(teacherNo + "--" + teacherName);
                }

                String temp = studentSig.getEnterpriseTeacher();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setEnterpriseTeacher(temp + "--" + teacherName);
                }

                temp = studentSig.getProfessionalOfficer();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setProfessionalOfficer(temp+ "--" + teacherName);
                }

                temp = studentSig.getRespondentRecorder();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setRespondentRecorder(temp+ "--" + teacherName);
                }
                temp = studentSig.getPaperreViewer();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setPaperreViewer(temp+ "--" + teacherName);
                }

                temp = studentSig.getDefenseTeamLeader();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setDefenseTeamLeader(temp+ "--" + teacherName);
                }

                temp = studentSig.getDefenseTeacher1();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setDefenseTeacher1(temp+ "--" + teacherName);
                }

                temp = studentSig.getDefenseTeacher2();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setDefenseTeacher2(temp+ "--" + teacherName);
                }

                temp = studentSig.getChairmanoftheDefenseCommittee();
                if (temp!=null) {
                    String teacherName = teacherSignatureTableimpl.getById(temp).getTeacherName();
                    studentSig.setChairmanoftheDefenseCommittee(temp+ "--" + teacherName);
                }



                studentSignatureInfo.add(studentSig);
            } else {
                StudentSignatureInfo studentSignatureInfo1 = new StudentSignatureInfo();
                studentSignatureInfo1.setStudentNo(sno);
                studentSignatureInfo1.setStudentName(student.getStudentName());
                studentSignatureInfo1.setTeacherNo(student.getTeacherNo());
                studentSignatureInfoService.save(studentSignatureInfo1);
                studentSignatureInfo.add(studentSignatureInfo1);

            }
        }
        if (studentSignatureInfo.size() > 0)
            return R.ok(200, "成功", studentSignatureInfo);
        else
            return R.fail(400, "失败,无此教师数据", null);
    }


    @RequestMapping("/student/{studentNo}")
    public R getStudentSignatureInfo1(@PathVariable String studentNo) {

        QueryWrapper<StudentSignatureInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("student_no", studentNo);
        StudentSignatureInfo studentSig = studentSignatureInfoService.getOne(queryWrapper2);
        if (studentSig != null)
            return R.ok(200, "成功", studentSig);
        else
            return R.fail(400, "失败,无此学生数据", null);
    }
}
