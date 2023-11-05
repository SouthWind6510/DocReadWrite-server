package cn.edu.sspu.signatureanddate.dao;
import cn.edu.sspu.signatureanddate.domain.DateTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DateTableDAO extends BaseMapper<DateTable> {
}
