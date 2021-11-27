package com.rue.controller;

import com.rue.bean.User;
import com.rue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author ruetrash
 */
@Controller
public class TableController {

    @Autowired
    UserService userService;


    @GetMapping("/user_table")
    public String dynamicTable(Model model, HttpSession session){

        List<User> users = userService.list();
        model.addAttribute("users",users);
        return "userTable/user_table";
    }

    //删除用户
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        if (id ==1){
            return "redirect:dynamic_table";
        }
        userService.removeById(id);
        //这里一定是重定向！！！！
        return "redirect:/dynamic_table";
    }
}
