package com.rue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rue.bean.ShareDiskFile;
import com.rue.bean.User;
import com.rue.bean.UserFile;
import com.rue.service.ShareDiskFileService;
import com.rue.service.ShareDiskService;
import com.rue.service.UserFileService;
import com.rue.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

/**
 * @author ruetrash
 */
@Controller
public class DownloadController {

    @Autowired
    UserFileService userFileService;
    @Autowired
    UserService userService;

    @Autowired
    ShareDiskFileService shareDiskFileService;
    @Autowired
    ShareDiskService shareDiskService;


    // 用户个人空间下载文件
    @GetMapping("/userFile/download/{id}")
    public String downloadUserFile(@PathVariable("id") int id, HttpSession session, HttpResponse response) throws IOException {

        //得到当前在线的用户
        User loginUser = (User) session.getAttribute("loginUser");

        //得到要下载文件的pathname
        QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        UserFile userFile = userFileService.getOne(wrapper);
        String pathname = userFile.getPathname();

        //使用桌面作为下载路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File downloadpath=fsv.getHomeDirectory();

        File file = new File(pathname);
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;

        if(file.exists()){
            try {
                fileInputStream = new FileInputStream(pathname);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                fileOutputStream = new FileOutputStream(downloadpath+"\\"+userFile.getFilename());

                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bufferedInputStream.read(buffer)) != -1){
                    fileOutputStream.write(buffer,0,len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                assert fileInputStream != null;
                fileInputStream.close();
                assert bufferedInputStream != null;
                bufferedInputStream.close();
                assert fileOutputStream != null;
                fileOutputStream.close();

            }
            return "redirect:/main.html";
        }
        return "redirect:/main.html";

    }

    // 从共享空间下载文件
    // 用户个人空间下载文件
    @GetMapping("/sharediskfiles/download/{id}")
    public String downloadShareDiskFile(@PathVariable("id") int id, HttpSession session, HttpResponse response) throws IOException {

        //得到当前在线的用户
        User loginUser = (User) session.getAttribute("loginUser");

        //得到要下载文件的pathname
        QueryWrapper<ShareDiskFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        ShareDiskFile shareDiskFile = shareDiskFileService.getOne(wrapper);
        String pathname = shareDiskFile.getPathname();

        if(!loginUser.getUsername().equals(shareDiskFile.getUsername()) && !"admin".equals(loginUser.getUsername())){
            return "redirect:/sharedisk";
        }


        //使用桌面作为下载路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File downloadpath=fsv.getHomeDirectory();

        //判断文件是否存在，并且开启三个流
        File file = new File(pathname);
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;

        if(file.exists()){

            try {
                fileInputStream = new FileInputStream(pathname);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                fileOutputStream = new FileOutputStream(downloadpath+"\\"+shareDiskFile.getFilename());

                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bufferedInputStream.read(buffer)) != -1){
                    fileOutputStream.write(buffer,0,len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                assert fileInputStream != null;
                fileInputStream.close();
                assert bufferedInputStream != null;
                bufferedInputStream.close();
                assert fileOutputStream != null;
                fileOutputStream.close();

            }
            return "redirect:/sharedisk";
        }

        return "redirect:/sharedisk";


    }
}
