package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.util.R;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class UploadStudents {
    @Autowired
    cn.edu.sspu.taskgroup.dao.studentdao studentdao;

    @PostMapping
    @ResponseBody
    public R insertorupdatestudent(MultipartFile file) throws IOException {
//        System.out.println(file);
        Student studenten = new Student();

        InputStream student = null;
        try {
            //将file转InputStream
            student = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将InputStream转XLSX对象（即表格对象）
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(student);
        //获取表格的第一页
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        //获取该页有多少数据
        int rowNum = xssfSheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            //获取当前行
            XSSFRow xssfRow = xssfSheet.getRow(i);
            //由于学号是纯数字，默认纯数字是以double传的，写进据库会变成科学计数法格式的，需要改为以string传进来
            xssfRow.getCell(3).setCellType(CellType.STRING);
            xssfRow.getCell(5).setCellType(CellType.STRING);
            //以此获得该行的所有单元格
            String school_name = xssfRow.getCell(0).toString();
            String major = xssfRow.getCell(1).toString();
            String class_name = xssfRow.getCell(2).toString();
            String sno = xssfRow.getCell(3).toString();
            String student_name = xssfRow.getCell(4).toString();
            String teacher_no = xssfRow.getCell(5).toString();
            String enterprise_teacher = xssfRow.getCell(6).toString();
            String project_title = xssfRow.getCell(7).toString();
            String english_title = xssfRow.getCell(8).toString();
            //将数据写入实体类
            studenten.setClassName(class_name);
            studenten.setMajor(major);
            studenten.setSno(sno);
            studenten.setProjectTitle(project_title);
            studenten.setStudentName(student_name);
            studenten.setEnterpriseTeacher(enterprise_teacher);
            studenten.setSchoolName(school_name);
            studenten.setEnglishTitle(english_title);
            studenten.setTeacherNo(teacher_no);
            studenten.setLockTask(2);
            studenten.setPassword("123456");
            //将实体类删除（若有）插入数据库
            studentdao.deleteById(sno);
            studentdao.insert(studenten);
        }
        return R.ok(200,"文件上传成功！" ,null);
    }

}
