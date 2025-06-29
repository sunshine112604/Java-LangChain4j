package com.atguigu.java.ai.langchain4j.mapper;


import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//mybatiesplus里面有baseMapper的功能，baseMapper里面有很多已经实现好的增删改查
//AppointmentMapper管理Appointment
public interface AppointmentMapper extends BaseMapper<Appointment> {

}
