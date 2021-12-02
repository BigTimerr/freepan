package com.rue.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rue.bean.UserFile;
import com.rue.mapper.UserFileMapper;
import com.rue.service.UserFileService;
import org.springframework.stereotype.Service;

@Service
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService {
}
