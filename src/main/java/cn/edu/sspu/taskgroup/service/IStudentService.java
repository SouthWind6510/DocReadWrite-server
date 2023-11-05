package cn.edu.sspu.taskgroup.service;
import cn.edu.sspu.taskgroup.domain.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
public interface IStudentService extends IService<Student>{
    IPage<Student> getUserPage(int currentPage, int pageSize);
    IPage<Student> getUserPage(int currentPage, int pageSize, Student student);
    IPage<Student> getUserPage(String userName,int currentPage, int pageSize);
}
