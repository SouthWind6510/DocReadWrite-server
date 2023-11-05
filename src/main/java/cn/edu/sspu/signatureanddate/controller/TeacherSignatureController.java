package cn.edu.sspu.signatureanddate.controller;

import cn.edu.sspu.signatureanddate.domain.TeacherSignatureTable;
import cn.edu.sspu.signatureanddate.service.implement.TeacherSignatureTableimpl;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.domain.Teacher;
import cn.edu.sspu.taskgroup.service.implement.TeacherServiceImpl;
import cn.edu.sspu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/uploadteachersignature")
public class TeacherSignatureController {
    @Autowired
    TeacherSignatureTableimpl teacherSignatureTableimpl;
    @Autowired
    TeacherServiceImpl teacherService;

    @RequestMapping("/getallteacherSignature")
    public R getallTeacherSignature() {
        List<TeacherSignatureTable> list = teacherSignatureTableimpl.list();
        if (list.size() > 0)
            return R.ok(200, "成功", list);
        else return R.fail(400, "失败", null);
    }


    @PostMapping("/upload")
    public R uploadTeacherSignature(@RequestParam("photoRef") MultipartFile photoRef,
                                    @RequestPart("TeacherNo") String teacherNo,
                                    @RequestPart("TeacherName") String teacherName,
                                    @RequestPart("oldpassword") String oldpassword,
                                    @RequestPart("newpassword") String newpassword) throws IOException {
        TeacherSignatureTable teacherSignatureTable = new TeacherSignatureTable();
        teacherSignatureTable.setTeacherName(teacherName);
        teacherSignatureTable.setTeacherNo(teacherNo);
        teacherSignatureTable.setTeacherSignature(photoRef.getBytes());
        TeacherSignatureTable teacherSignature = teacherSignatureTableimpl.getById(teacherNo);
        String filename = photoRef.getOriginalFilename();
        if (!filename.substring(filename.length() - 3).equals("png")
                && !filename.substring(filename.length() - 3).equals("jpg")) {
            return R.fail(400, "文件格式不对，请上传JPG或PNG文件！", null);
        }
        if (photoRef.getSize() >= 50 * 1024)
            return R.fail(400, "SORRY,签名文件搞哪么大，"
                    + photoRef.getSize() / 1024 + "k, 大于50K，拒绝上传！", null);

        String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        Teacher teacher = teacherService.getById(teacherNo);
        System.out.println(oldpassword);
        if (!oldpassword.equals(teacher.getPassword()))
            return R.fail(400, "原密码不正确", null);
        if (!newpassword.matches(PW_PATTERN)) {
            return R.fail(400, "密码强度不够，请重新设置", null);
        }


        if (teacherSignature == null) {
            teacherSignatureTableimpl.save(teacherSignatureTable);
            teacher.setFirstTimeLogin(1);
            teacher.setPassword(newpassword);
            teacherService.updateById(teacher);
            return R.ok(200, "上传教师签名成功！", null);
        } else {

            teacher.setFirstTimeLogin(1);
            teacher.setPassword(newpassword);
            teacherService.updateById(teacher);
            return R.fail(200, "密码已修改，" + teacherSignature.getTeacherName() + "的签名已存在，不需上传", null);
        }
    }
}
