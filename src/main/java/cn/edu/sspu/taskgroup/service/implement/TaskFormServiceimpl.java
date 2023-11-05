package cn.edu.sspu.taskgroup.service.implement;

import cn.edu.sspu.DocReadWriteApplication;
import cn.edu.sspu.signatureanddate.domain.StudentSignatureInfo;
import cn.edu.sspu.signatureanddate.domain.StudentSignatureTable;
import cn.edu.sspu.signatureanddate.domain.TeacherSignatureTable;
import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureInfoServiceImpl;
import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureTableImpl;
import cn.edu.sspu.signatureanddate.service.implement.TeacherSignatureTableimpl;
import cn.edu.sspu.taskgroup.dao.TaskFormDAO;
import cn.edu.sspu.taskgroup.domain.Planlist;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.domain.TaskForm;
import cn.edu.sspu.taskgroup.service.ITaskFormService;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class TaskFormServiceimpl extends ServiceImpl<TaskFormDAO, TaskForm> implements ITaskFormService {
    @Autowired
    TaskFormServiceimpl taskFormServiceimpl;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    TaskDateServiceImpl taskDateService;
    @Autowired
    TeacherSignatureTableimpl teacherSignatureTableimpl;
    @Autowired
    StudentSignatureInfoServiceImpl studentSignatureInfoService;
    @Autowired
    StudentSignatureTableImpl studentSignatureTableService;
     StringBuilder sb = new StringBuilder(File.separator);
    String userpath = sb.append("home").append(File.separator).append("wwwserver").append(File.separator).toString();

    public R createword(String Sno) throws IOException, OpenXML4JException, XmlException {
//        Sno为学号
        QueryWrapper<TaskForm> taskFormqueryWrapper = new QueryWrapper<>();
        taskFormqueryWrapper.eq("Sno", Sno);

        QueryWrapper<Student> studentFormqueryWrapper = new QueryWrapper<>();
        studentFormqueryWrapper.eq("Sno", Sno);

        QueryWrapper<Planlist> planlistQueryWrapper = new QueryWrapper<>();
        planlistQueryWrapper.eq("Sno", Sno);

        TaskForm taskForm = taskFormServiceimpl.getOne(taskFormqueryWrapper);
        if (taskForm == null) {
            taskForm = new TaskForm();
            taskForm.setSno(Sno);
            taskFormServiceimpl.save(taskForm);
        }
        Student student = studentService.getOne(studentFormqueryWrapper);
        StudentSignatureInfo teacherSig = studentSignatureInfoService.getById(Sno);
        StudentSignatureTable studentSignature = studentSignatureTableService.getById(Sno);
        String wordFileName = "";
        String property = System.getProperty("user.dir");


        File file = new File("./assignmentbook.docx");
        FileInputStream fis = null;
        XWPFDocument document = null;
        XWPFWordExtractor extractor = null;
        UUID uuid = null;
        try {
            fis = new FileInputStream(file);
            document = new XWPFDocument(fis);
            List<XWPFTable> tables = document.getTables();
            XWPFTable table1 = tables.get(0);
            QueryWrapper<Planlist> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_no", student.getSno());

            List<Planlist> list = taskDateService.list(queryWrapper);
            for (int i = 0; i < list.size(); i++) {
                XWPFTableRow datarow = table1.getRow(6);
                List<XWPFTableCell> cells = datarow.getTableCells();
                cells.get(0).addParagraph().createRun().setText(list.get(i).getSno() + "");
                cells.get(1).addParagraph().createRun().setText(list.get(i).getTask());
                cells.get(2).addParagraph().createRun()
                        .setText(list.get(i).getDateBegin().toString()
                                + "至" + list.get(i).getDateEnd().toString());
                cells.get(0).removeParagraph(0);
                cells.get(1).removeParagraph(0);
                cells.get(2).removeParagraph(0);
                table1.addRow(datarow, i + 7);
            }
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    boolean flag = false;
                    Iterator<XWPFTableCell> iterator = tableCells.iterator();

                    while (iterator.hasNext()) {
                        XWPFTableCell next = iterator.next();
                        if (next.getText().contains("题目")) {
                            if (iterator.hasNext()) {
                                iterator.next().setText(student.getProjectTitle());
                            }
                        }
                        if (next.getText().contains("学部（院）")) {
                            if (iterator.hasNext())
                                iterator.next().setText(student.getSchoolName());
                        }

                        if (next.getText().contains("专业")) {
                            if (iterator.hasNext())
                                iterator.next().setText(student.getMajor());
                        }

                        if (next.getText().contains("班级")) {
                            if (iterator.hasNext())
                                iterator.next().setText(student.getClassName());
                        }
                        if (next.getText().contains("姓名")) {
                            if (iterator.hasNext())
                                iterator.next().setText(student.getStudentName());
                        }
                        if (next.getText().contains("学号")) {
                            if (iterator.hasNext())
                                iterator.next().setText(student.getSno());
                        }

                        if (next.getText().contains("序号")) {
                        }
                        if (next.getText().contains("课题主要研究(设计)内容：")) {
                            if (taskForm.getStudyContent() != null) {
                                String[] studentContents = taskForm.getStudyContent().split("\n");
                                for (String studentContent : studentContents) {
                                    XWPFParagraph xwpfParagraph = next.addParagraph();
                                    XWPFRun run = xwpfParagraph.createRun();
                                    run.addTab();
                                    run.setText(studentContent.trim());
                                }
                            }
                        }
                        if (next.getText().contains("三、应查阅的主要参考文献")) {
                            if (taskForm.getLiterature() != null) {
                                String[] studentLiterature = taskForm.getLiterature().split("\n");
                                for (String Literature : studentLiterature) {
                                    XWPFParagraph xwpfParagraph = next.addParagraph();
                                    XWPFRun run = xwpfParagraph.createRun();
                                    run.setText(Literature.trim());
                                }
                            }
                        }
                        if (next.getText().contains("四、毕业设计（论文）预期成果或结论性观点：")) {
                            if (taskForm.getExpectedConclusion() != null) {
                                String[] studentExpectedConclusion = taskForm.getExpectedConclusion().split("\n");
                                for (String expectedConclusion : studentExpectedConclusion) {
                                    XWPFParagraph xwpfParagraph = next.addParagraph();
                                    XWPFRun run = xwpfParagraph.createRun();
                                    run.addTab();
                                    run.setText(expectedConclusion.trim());
                                }
                            }
                        }
                        if (next.getText().contains("毕业设计（论文）完成提交材料清单：")) {
                            if (taskForm.getBillofMaterials() != null) {
                                String[] studentBillofMaterials = taskForm.getBillofMaterials().split("\n");
                                for (String billofMaterials : studentBillofMaterials) {
                                    XWPFParagraph xwpfParagraph = next.addParagraph();
                                    XWPFRun run = xwpfParagraph.createRun();
                                    run.addTab();
                                    run.setText(billofMaterials.trim());
                                }
                            }
                        }
                        if (next.getText().contains("日期xxxx")) {

                            if (taskForm.getExecDate() != null) {
                                XWPFParagraph xwpfParagraph = next.addParagraph();
                                XWPFRun run = xwpfParagraph.createRun();
                                run.addTab();
                                run.setText(taskForm.getExecDate());
                                next.removeParagraph(0);
                            }
                        }
                        if (next.getText().contains("学生（签字）：")) {
                            if (studentSignature != null) {
                                byte[] studentSig = studentSignature.getStudentSignature();
                                if (studentSig != null) {
                                    next.removeParagraph(0);
                                    XWPFParagraph title = next.addParagraph();
                                    XWPFRun run = title.createRun();
                                    String imgFile = "datum\\temp.jpg";
                                    InputStream input = new ByteArrayInputStream(studentSig);
                                    run.addPicture(input,
                                            XWPFDocument.PICTURE_TYPE_JPEG,
                                            imgFile,
                                            Units.toEMU(50),
                                            Units.toEMU(20)); // 200x200 pixels}
                                }
                            }
                        }
                        if (next.getText().contains("老师（签字）：")) {
                            TeacherSignatureTable teacherSignature = null;
                            if (teacherSig != null)
                                teacherSignature = teacherSignatureTableimpl.getById(teacherSig.getTeacherNo());
                            if (teacherSignature != null) {
                                next.removeParagraph(0);
                                XWPFParagraph title = next.addParagraph();
                                XWPFRun run = title.createRun();
                                String imgFile = "datum\\temp.jpg";
                                byte[] teacherSignature1 = teacherSignature.getTeacherSignature();
                                InputStream input = new ByteArrayInputStream(teacherSignature1);
                                run.addPicture(input,
                                        XWPFDocument.PICTURE_TYPE_JPEG,
                                        imgFile,
                                        Units.toEMU(50),
                                        Units.toEMU(20)); // 200x200 pixels}
                            }
                        }
                        if (next.getText().contains("主任（签字）：")) {

                            TeacherSignatureTable teacherSignature = null;
                            if (teacherSig != null)
                                teacherSignature = teacherSignatureTableimpl
                                        .getById(teacherSig.getProfessionalOfficer());
                            if (teacherSignature != null) {
                                next.removeParagraph(0);
                                XWPFParagraph title = next.addParagraph();
                                XWPFRun run = title.createRun();
//                            title.getCTP().removeBookmarkEnd(0);
                                String imgFile = "datum\\temp.jpg";
                                byte[] teacherSignature1 = teacherSignature.getTeacherSignature();
                                InputStream input = new ByteArrayInputStream(teacherSignature1);
                                run.addPicture(input,
                                        XWPFDocument.PICTURE_TYPE_JPEG,
                                        imgFile,
                                        Units.toEMU(50),
                                        Units.toEMU(20)); // 200x200 pixels
                            }
                        }
                    }
                }
            }
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(now);
            wordFileName = student.getSno() + "-" + student.getStudentName() + "-任务书-(" + format + ").docx";
            document.getTables().get(0).removeRow(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(200, wordFileName, document);
    }
}



