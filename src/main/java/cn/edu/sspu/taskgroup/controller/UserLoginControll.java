package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.service.implement.UserLoginService;
import cn.edu.sspu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/login")
public class UserLoginControll {
    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/student")
    public R accountLogin(@RequestBody Map<String,String> map){
        String loginRole = map.get("loginRole");
        String userName = map.get("userName");
        String password = map.get("password");
        return userLoginService.accountLogin(userName,password,loginRole);
    }
}
