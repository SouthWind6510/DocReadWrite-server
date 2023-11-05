package cn.edu.sspu.signatureanddate.service.implement;

import cn.edu.sspu.signatureanddate.dao.DateTableDAO;
import cn.edu.sspu.signatureanddate.domain.DateTable;
import cn.edu.sspu.signatureanddate.service.IDateTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DateTableServiceImpl extends ServiceImpl<DateTableDAO, DateTable> implements IDateTableService {
}
