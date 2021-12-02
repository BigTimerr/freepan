package com.rue.controller;


import com.rue.bean.ShareDisk;
import com.rue.bean.ShareDiskFile;
import com.rue.service.ShareDiskFileService;
import com.rue.service.ShareDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
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
}
