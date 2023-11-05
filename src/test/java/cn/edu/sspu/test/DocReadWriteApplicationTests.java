package cn.edu.sspu.test;
import java.io.*;

import cn.edu.sspu.signatureanddate.domain.DateTable;
import cn.edu.sspu.signatureanddate.service.implement.DateTableServiceImpl;
import cn.edu.sspu.taskgroup.domain.Student;

import cn.edu.sspu.signatureanddate.service.implement.StudentSignatureInfoServiceImpl;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import cn.edu.sspu.taskgroup.service.implement.TaskDateServiceImpl;
import cn.edu.sspu.taskgroup.service.implement.TaskFormServiceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.util.*;

@SpringBootTest
class DocReadWriteApplicationTests {
    @Autowired
    TaskFormServiceimpl taskFormServiceimpl;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    TaskDateServiceImpl taskDateService;
    @Autowired
    private cn.edu.sspu.taskgroup.dao.studentdao studentdao;
}














