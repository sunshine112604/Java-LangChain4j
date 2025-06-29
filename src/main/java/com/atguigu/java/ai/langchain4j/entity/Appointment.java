package com.atguigu.java.ai.langchain4j.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @TableId(type = IdType.AUTO)//mybatiesplus提供的一个主键生成策略，依赖于mysql的主键自增策略
    private  Long id;
    private  String username;
    private  String idCard;
    private  String department;
    private  String date;
    private  String time;
    private  String doctorName;
}
