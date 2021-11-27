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
@TableName(value = "user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    private Long totalMemory;
    private Long usedMemory;
    private Long leftMemory;
    private int fileCount;

}
