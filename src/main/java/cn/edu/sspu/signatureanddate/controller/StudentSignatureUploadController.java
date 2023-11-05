package cn.edu.sspu.signatureanddate.controller;

import cn.edu.sspu.signatureanddate.domain.StudentSignatureTable;
import cn.edu.sspu.signatureanddate.domain.TeacherSignatureTable;
import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureTableImpl;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import cn.edu.sspu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/uploadstudentsignature")
public class StudentSignatureUploadController {
    @Autowired
    StudentSignatureTableImpl studentSignatureTableimpl;
    @Autowired
    StudentServiceImpl studentService;

    @RequestMapping("/getallstudentSignature")
    public R getallTeacherSignature() {
        List<StudentSignatureTable> list = studentSignatureTableimpl.list();
        if (list.size() > 0)
            return R.ok(200, "成功", list);
        else return R.fail(400, "失败", null);
    }

    @PostMapping("/upload")
    public R uploadStudentSignature(@RequestParam("photoRef") MultipartFile photoRef,
                                    @RequestPart("StudentNo") String studentNo,
                                    @RequestPart("StudentName") String studentName,
                                    @RequestPart("oldpassword") String oldpassword,
                                    @RequestPart("newpassword") String newpassword) throws IOException {
        StudentSignatureTable studentSignatureTable = new StudentSignatureTable();
        studentSignatureTable.setStudentName(studentName);
        studentSignatureTable.setStudentNo(studentNo);
        studentSignatureTable.setStudentSignature(photoRef.getBytes());
        StudentSignatureTable studentSignature = studentSignatureTableimpl.getById(studentNo);
        String filename = photoRef.getOriginalFilename();
        if (!filename.substring(filename.length() - 3).equals("png")
                && !filename.substring(filename.length() - 3).equals("jpg")) {
            return R.fail(400, "文件格式不对，请上传JPG或PNG文件！", null);
        }
        if (photoRef.getSize() >= 50 * 1024)
            return R.fail(400, "SORRY,签名文件搞哪么大，"
                    + photoRef.getSize() / 1024 + "k, 大于50K，拒绝上传！", null);
        String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        Student student = studentService.getById(studentNo);
        System.out.println(oldpassword);
        if (!oldpassword.equals(student.getPassword()))
            return R.fail(400, "原密码不正确", null);
        if (!newpassword.matches(PW_PATTERN)) {
            return R.fail(400, "密码强度不够，请重新设置", null);
        }
        if (studentSignature == null) {
            studentSignatureTableimpl.save(studentSignatureTable);
            student.setFirstTimeLogin(1);
            student.setPassword(newpassword);
            studentService.updateById(student);
            return R.ok(200, "上传学生签名成功！", null);
        } else {
            student.setFirstTimeLogin(1);
            student.setPassword(newpassword);
            studentService.updateById(student);
            return R.fail(200, "密码已修改，" + studentSignature.getStudentName() + "的签名已存在，不需上传", null);
        }
    }
}
