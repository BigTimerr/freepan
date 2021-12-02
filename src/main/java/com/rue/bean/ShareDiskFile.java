package com.rue.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "share_disk_file")
public class ShareDiskFile {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String username;
    private String pathname;
    private String filename;
    private long size;
}
