package com.atguigu.java.ai.langchain4j.service;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}

//Mybaties中有一个IService，里面有一些有预定义的增删改查
//这个里面拓展了一个方法getOne，除了基本的增删改查
//如果预约订单存在，Appointment返回非空的对象，否则，返回null
//在AppointServiceImpl就是对订单的一个查询