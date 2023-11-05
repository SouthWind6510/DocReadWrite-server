package cn.edu.sspu.taskgroup.service.implement;

import cn.edu.sspu.taskgroup.dao.TaskDateDAO;
import cn.edu.sspu.taskgroup.domain.Planlist;
import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.service.ITaskDateService;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TaskDateServiceImpl extends ServiceImpl<TaskDateDAO, Planlist> implements ITaskDateService {

    @Autowired
    TaskDateDAO taskDateDAO;

    @Autowired
    StudentServiceImpl studentService;

    public R insertorUpdateStudentPlanlist(MultipartFile file, String teacherNo) throws IOException {
        Planlist planlist = new Planlist();

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_no", teacherNo);
        List<Student> list = studentService.list(queryWrapper);
        InputStream planlists = null;
        try {
            //将file转InputStream
            planlists = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将InputStream转XLSX对象（即表格对象）
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(planlists);
        //获取表格的第一页
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        //获取该页有多少数据
        int rowNum = xssfSheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            //获取当前行
            XSSFRow xssfRow = xssfSheet.getRow(i);
            //由于学号是纯数字，默认纯数字是以double传的，写进据库会变成科学计数法格式的，需要改为以string传进来
//            xssfRow.getCell(3).setCellType(CellType.STRING);
            xssfRow.getCell(0).setCellType(CellType.STRING);
            //以此获得该行的所有单元格
            int Sno = Integer.parseInt(xssfRow.getCell(0).toString());
            String task = xssfRow.getCell(1).toString();
            //excell中日期字段应是字符串
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");//SimpleDateFormat格式和解析日期的类
            //得到java.sql.Date类型的对象，就可以插入到数据表对应的Date字段

            String begin = xssfRow.getCell(2).toString();
            java.util.Date parse = null;//得到java.util.Date对象
            try {
                parse = sdf.parse(begin);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            long time = parse.getTime();//返回当前日期对应的long类型的毫秒数
            Date date_begin = new java.sql.Date(time);


            String end = xssfRow.getCell(3).toString();

            try {
                parse = sdf.parse(end);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            time = parse.getTime();//返回当前日期对应的long类型的毫秒数
            Date date_end = new java.sql.Date(time);

            String remark = xssfRow.getCell(4).toString();

            System.out.println(list.size());
            for (Student student : list) {
                //将数据写入实体类
                planlist.setTask(task);
                planlist.setSno(Sno);
                planlist.setDateBegin(date_begin);
                planlist.setDateEnd(date_end);
                planlist.setRemark(remark);
                planlist.setTeacherNo(teacherNo);
                planlist.setStudentNo(student.getSno());

                //将实体类更新（若有）OR插入数据库
                QueryWrapper<Planlist> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("Sno", Sno);
                queryWrapper1.eq("student_no", student.getSno());
                if (taskDateDAO.selectOne(queryWrapper1) == null)
                    taskDateDAO.insert(planlist);
                else
                    taskDateDAO.update(planlist,queryWrapper1);
            }
        }
        return R.ok(200, "工作进度上传成功！", null);
    }


}
