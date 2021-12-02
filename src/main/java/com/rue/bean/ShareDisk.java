package com.rue.bean;


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
@TableName(value = "share_disk")
public class ShareDisk {

    private Long totalMemory;
    private Long usedMemory;
    private Long leftMemory;
    private int fileCount;
}
