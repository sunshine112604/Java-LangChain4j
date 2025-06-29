package com.atguigu.java.ai.langchain4j.assistant;


import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

//在配置文件中同时配置了很多个大语言模型，所以需要明确指出（EXPLICIT)模型的beanName是什么，否则它默认的ChatLanguageModel包含很多个，他不知道是哪几个）
//
//AiServices会组装Assistant接口以及其他组件，并使用反射机制创建一个实现Assistant接口的代理对象。
//这个代理对象会处理输入和输出的所有转换工作。在这个例子中，chat方法的输入是一个字符串，但是大
//模型需要一个 UserMessage 对象。所以，代理对象将这个字符串转换为 UserMessage ，并调用聊天语
//言模型。chat方法的输出类型也是字符串，但是大模型返回的是 AiMessage 对象，代理对象会将其转换
//为字符串。
//简单理解就是：代理对象的作用是输入转换和输出转换
@AiService(wiringMode = EXPLICIT,chatModel="qwenChatModel")
public interface Assistant {
    //接收的是用户的输入（字符串类型）
    //返回的是大模型的输出结果（字符串类型）
    String chat(String userMessage);
}
