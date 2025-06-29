package com.atguigu.java.ai.langchain4j.assistant;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode =EXPLICIT,
        chatModel = "qwenChatModel",
        //chatMemory = "chatMemory",只能实现聊天记忆
        chatMemoryProvider = "chatMemoryProvider",//会话ID
        tools = "calculatorTools"//配置tools，作为bean对象它的名字是小写
)
public interface SeparateChatAssistant {

    /**
     * 分离聊天记录
     * @param memoryId 聊天id
     * @param userMseeage 用户消息
     * @return
     */
    /**
     *SystemMessage注解设定角色，塑造专业的AI助手身份，明确助手的能力范围
     */
//    @SystemMessage("你是我的好朋友，请用东北话回答问题。今天是{{current_date}}")
//    @SystemMessage("你是我的好朋友，请用西安回答问题")
    //切换了系统提示词，就会失忆，即使id一样，也会失忆
//     @SystemMessage("你是我的好朋友，请用粤语回答问题。")
     @SystemMessage(fromResource = "my-promte-template.txt")
    String chat(@MemoryId int memoryId, @UserMessage String userMseeage);

      @UserMessage ("你是我的好朋友，请用粤语回答问题。并添加一些表情符号。{{message}}")
      //这是由两个参数，所以必须写V，一个参数可以it按照之前的方法
    String chat2(@MemoryId int memoryId, @V("message") String userMseeage);



      //结合使用的方式
      @SystemMessage(fromResource = "my-promte-template3.txt")
    String chat3(
            @MemoryId int memoryId,
            @UserMessage String userMseeage,
            @V("username") String username,
            @V("age")int age
      );

}
