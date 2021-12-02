package com.rue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rue.bean.ShareDisk;
import com.rue.bean.ShareDiskFile;
import com.rue.bean.User;
import com.rue.service.ShareDiskFileService;
import com.rue.service.ShareDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;



/**
 * @author ruetrash
 */
@Controller
public class ShareDiskController {

    @Autowired
    ShareDiskFileService shareDiskFileService;

    @Autowired
    ShareDiskService shareDiskService;

    @GetMapping("/sharedisk")
    public String sharedisk(HttpSession session, Model model){


        // 查询出所有的文件
        List<ShareDiskFile> filelist = shareDiskFileService.list();
        model.addAttribute("filelist",filelist);

        //查询出容量
        ShareDisk shareDisk = shareDiskService.getOne(null);
        model.addAttribute("shareDisk",shareDisk);

        return "sharedisk";
    }

    @GetMapping("/sharediskfiles/delete/{id}")
    public String deleteUserFile(@PathVariable("id") int id, HttpSession session, Model model)
    {
        //得到当前登录的用户
        User loginUser = (User) session.getAttribute("loginUser");

        // 得到数据库中这个id的文件的所有信息
        QueryWrapper<ShareDiskFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        ShareDiskFile shareDiskFile = shareDiskFileService.getOne(wrapper);

        //只有上传用户本人和admin管理员有权限删除文件
        if(!loginUser.getUsername().equals(shareDiskFile.getUsername()) && !"admin".equals(loginUser.getUsername())){

//            model.addAttribute("msg","不能删除此文件");
            return "redirect:/sharedisk";
        }

        //得到当前共享空间的信息
        ShareDisk shareDisk = shareDiskService.getOne(null);
        //整理共享空间的信息，文件数量，剩余容量，使用容量
        shareDisk.setFileCount(shareDisk.getFileCount()-1);
        shareDisk.setLeftMemory(shareDisk.getLeftMemory()+shareDiskFile.getSize());
        shareDisk.setUsedMemory(shareDisk.getUsedMemory()-shareDiskFile.getSize());
        //修改共享空间的信息
        UpdateWrapper<ShareDisk> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("total_memory", "10737418240");
        shareDiskService.update(shareDisk,updateWrapper);

        //得到文件路径,并且删除文件
        String pathname =  shareDiskFile.getPathname();
        File file = new File(pathname);
        file.delete();


        //从数据库中删除此文件
        shareDiskFileService.removeById(id);


        return "redirect:/sharedisk";
    }


}
