package com.rue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rue.bean.User;
import com.rue.bean.UserFile;
import com.rue.mapper.UserMapper;
import com.rue.service.UserFileService;
import com.rue.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ruetrash
 */
@Slf4j

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserFileService userFileService;


    //登录页
    @GetMapping(value = {"/", "/login"})
    public String login(){
        return "login";
    }


    //登录请求
    @PostMapping("/login")
    public String main(String username, String password, HttpSession session, Model model){
        // 在用户表中查找该用户
        List<User> userList = userService.list();
        for (User user:userList) {
            //如果数据库中有这个用户，就跳转到main页面，并且把用户信息放到session中。
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                session.setAttribute("loginUser",user);
                return "redirect:/main.html";

            }
        }


        model.addAttribute("msg","用户名或密码错误");
        //跳转到登录页面，并且提示错误
        return "login";

    }

    // 进入主页
    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model){
        User loginUser = (User)session.getAttribute("loginUser");

        //  显示用户的文件列表
        QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
        wrapper.eq("username",loginUser.getUsername());
        List<UserFile> userFileList = userFileService.list(wrapper);
        model.addAttribute("userFileList", userFileList);


        return "main";
    }


    //注册用户
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    UserMapper userMapper;

    //用户提交注册用户的表单
    @PostMapping("/registration")
    public String adduser(String username,String password,String email,Model model){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        long count = userService.count(wrapper);
        if (count>0){
            model.addAttribute("msg","用户已经存在");
            return "registration";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setTotalMemory(10737418240L);
        user.setUsedMemory(0L);
        user.setLeftMemory(10737418240L);
        user.setFileCount(0);
        boolean save = userService.saveOrUpdate(user);
        log.info(String.valueOf(save));

        return "redirect:login";
    }


    // 进入文件上传页面
    @GetMapping("/dropzone")
    public String dropzonepage(){
        return "dropzone";
    }


    @GetMapping("/uploadShare")
    public String uploadShare(){
        return "uploadShareFile";
    }


    @GetMapping("/form_layouts")
    public String form_layouts(){
        return "form_layouts";
    }

    @RequestMapping("/updateSpace")
    public String updateSpace(String totalMemory){

        log.info(String.valueOf(totalMemory));


        return "form_layouts";
    }


    //退出登录
    //登录页
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");


        return "login";
    }

}
