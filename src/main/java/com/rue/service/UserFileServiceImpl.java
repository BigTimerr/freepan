package com.rue.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rue.bean.UserFile;
import com.rue.mapper.UserFileMapper;
import org.springframework.stereotype.Service;

@Service
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService{
}
