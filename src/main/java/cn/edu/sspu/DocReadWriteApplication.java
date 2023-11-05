package cn.edu.sspu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//https://blog.csdn.net/m0_65563175/article/details/127354442
@SpringBootApplication
public class DocReadWriteApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocReadWriteApplication.class, args);
    }
}
//@SpringBootApplication
//public class DocReadWriteApplication extends SpringBootServletInitializer {
//    public static void main(String[] args) throws IOException {
//        // 程序启动入口
//        Properties properties = new Properties();
//        InputStream in = DocReadWriteApplication.class.getClassLoader().getResourceAsStream("application.yml");
//        properties.load(in);
//        SpringApplication app = new SpringApplication(DocReadWriteApplication.class);
//        app.setDefaultProperties(properties);
//        app.run(args);
//    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // TODO Auto-generated method stub
//        builder.sources(this.getClass());
//        return super.configure(builder);
//    }
//}
