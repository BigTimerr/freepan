package com.rue.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author ruetrash
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_file")
public class UserFile {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String username;
    private String filename;
    private long size;
}
