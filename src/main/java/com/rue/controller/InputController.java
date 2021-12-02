package com.rue.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rue.bean.ShareDisk;
import com.rue.bean.ShareDiskFile;
import com.rue.bean.User;
import com.rue.bean.UserFile;
import com.rue.service.ShareDiskFileService;
import com.rue.service.ShareDiskService;
import com.rue.service.UserFileService;
import com.rue.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author ruetrash
 *
 * */


@Slf4j
@Controller
public class InputController {

    @Autowired
    UserFileService userFileService;
    @Autowired
    UserService userService;

    @Autowired
    ShareDiskFileService shareDiskFileService;
    @Autowired
    ShareDiskService shareDiskService;

    @PostMapping("/upload")
    public String upload(@RequestPart("files") MultipartFile[] files, HttpSession session, Model model) throws IOException {

        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    //得到session中的用户
                    User user =  (User)session.getAttribute("loginUser");

                    // 得到文件的存储位置
                    String originalFilename = file.getOriginalFilename();
                    String pathname = "E:\\cache\\" +user.getUsername()+"\\"+ originalFilename;

                    File filepath = new File("E:\\cache\\" +user.getUsername()+"\\");
                    if(!filepath.exists()) {
                        filepath.mkdir();
                    }


                    // 查看数据库中是否有同名的文件，如果有,就不能上传
                    QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
                    wrapper.eq("filename",pathname);
                    long count = userFileService.count(wrapper);
                    if (count>0){
                        model.addAttribute("msg","已经有同名文件了");
                        //未登录跳转到error页面
                        return "/dropzone";
                    }



                    // 查看文件的大小
                    long size = file.getSize();
                    log.info(String.valueOf(size));


                    //查看文件大小是否大于剩余空间
                    if(size > user.getLeftMemory())
                    {
                        model.addAttribute("msg","文件大小大于剩余空间大小");
                        //未登录跳转到error页面
                        return "/dropzone";
                    }// 如果小于生与空间，就修改user的属性
                    else{
                        user.setLeftMemory(user.getLeftMemory()-size);
                        user.setUsedMemory(user.getUsedMemory()+size);
                        user.setFileCount(user.getFileCount()+1);
                        session.setAttribute("loginUser",user);
                    }


                    // 把用户上传的文件信息保存到数据库
                    UserFile userFile = new UserFile();
                    userFile.setUsername(user.getUsername());
                    userFile.setFilename(pathname);
                    userFile.setSize(size);
                    // 先保存文件，在上传到数据库
                    file.transferTo(new File(pathname));
                    userFileService.saveOrUpdate(userFile);

                    //
                    userService.saveOrUpdate(user);

                }
            }
            return "redirect:/dropzone";
        }
        return "redirect:/dropzone";
    }

    @GetMapping("/userFile/delete/{id}")
    public String deleteUserFile(@PathVariable("id") int id, HttpSession session)
    {
        // 得到数据库中这个id的文件的所有信息
        QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        UserFile userFile = userFileService.getOne(wrapper);

        //得到当前登录的用户
        User loginUser = (User)session.getAttribute("loginUser");
        loginUser.setUsedMemory(loginUser.getUsedMemory()-userFile.getSize());
        loginUser.setFileCount(loginUser.getFileCount()-1);
        loginUser.setLeftMemory(loginUser.getLeftMemory()+userFile.getSize());

        userFileService.removeById(id);

        return "redirect:/main.html";
    }




    @PostMapping("/uploadShareFile")
    public String uploadShareFile(@RequestPart("files") MultipartFile[] files, HttpSession session, Model model) throws IOException {

        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    //得到session中的用户
                    User user =  (User)session.getAttribute("loginUser");
                    // 得到数据库中共享网盘的信息
                    ShareDisk shareDisk = shareDiskService.getOne(null);


                    // 得到文件的存储位置
                    String originalFilename = file.getOriginalFilename();
                    String pathname = "E:\\cache\\ShareDisk\\"+ originalFilename;



                    // 查看数据库中是否有同名的文件，如果有,就不能上传
                    QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
                    wrapper.eq("filename",pathname);
                    long count = userFileService.count(wrapper);
                    if (count>0){
                        model.addAttribute("msg","已经有同名文件了");
                        //未登录跳转到error页面
                        return "/uploadShareFile";
                    }



                    // 查看文件的大小
                    long size = file.getSize();
                    log.info(String.valueOf(size));


                    //查看文件大小是否大于剩余空间
                    if(size > shareDisk.getLeftMemory())
                    {
                        model.addAttribute("msg","文件大小大于剩余空间大小");
                        //未登录跳转到error页面
                        return "/uploadShareFile";
                    }// 如果小于剩余空间，就修改shareDisk的属性
                    else{
                        shareDisk.setLeftMemory(user.getLeftMemory()-size);
                        shareDisk.setUsedMemory(user.getUsedMemory()+size);
                        shareDisk.setFileCount(user.getFileCount()+1);
                        session.setAttribute("shareDisk",shareDisk);
                    }


                    // 把用户上传的文件信息保存到数据库
                    ShareDiskFile shareDiskFile = new ShareDiskFile();
                    shareDiskFile.setFilename(pathname);
                    shareDiskFile.setUsername(user.getUsername());
                    shareDiskFile.setSize(size);


                    // 先保存文件，在上传到数据库
                    file.transferTo(new File(pathname));
                    shareDiskFileService.saveOrUpdate(shareDiskFile);

                    //
                    shareDiskService.saveOrUpdate(shareDisk);

                }
            }
            return "redirect:/uploadShareFile";
        }
        return "redirect:/uploadShareFile";
    }
}
