package com.atguigu.java.ai.langchain4j;


import com.atguigu.java.ai.langchain4j.assistant.Assistant;
import com.atguigu.java.ai.langchain4j.assistant.MemoryChatAssistant;
import com.atguigu.java.ai.langchain4j.assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;


//普通的java类，构造消息对象，发送给大模型，用于业务逻辑代码之中
//import dev.langchain4j.data.message.UserMessage;
//注解，标记接口参数含义，定义接口
//import dev.langchain4j.service.UserMessage


@SpringBootTest
public class ChatMemoryTest {
    @Autowired
    private Assistant assistant;
    @Test
    public void testChatMemory(){
        String answer1 = assistant.chat("我是欢欢");
        System.out.println(answer1);

        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }
    
    
    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testChatMemory2() {
        //第一轮对话
        UserMessage userMessage1 = UserMessage.userMessage("我是环环");
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage1.text());
        //第二轮对话
        UserMessage userMessage2 = UserMessage.userMessage("你知道我是谁吗");
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage2.text());
    }

     @Test
     //借助于人工智能服务实现聊天记忆，使用chatMemory实现聊天记忆
    public void testChatMemory3() {

         //创建chatMemory，这个是表示可以解释10个聊天记忆
         //聊天记忆基于内存级别实现的。默认SingleSlotChatMemoryStore（把聊天记忆放在了列表之中）
         //它的另外一个实现类 InMemoryChatMemoryStore，
         //本质与SingleSlotChatMemoryStore一样，只是他将聊天记忆放在了hashmap之中（键：memoryI，值：聊天列表）
         //都是放在了内存之中
         //切换，在separateChatAssistant配置文件之中 ，找到之前配置聊天记忆的地方，见SeparateChatAssistantConfig文件

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

         //创建Aiservice,传统创建Aiservice的方式没有chatMemory，也就没有记忆功能
         //问题:如果还是这样创建Assistant，那么之前的Assistant接口上的注解白写了
        Assistant assistant = AiServices
                 .builder(Assistant.class)
                 .chatLanguageModel(qwenChatModel)
                 .chatMemory(chatMemory)
                 .build();

        //调用service接口
        String answer1 = assistant.chat("我是欢欢");
        System.out.println(answer1);
        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);

     }


     //使用AiServices实现聊天记忆

    @Autowired
    //注入接口
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testChatMemory4(){
        String answer1 = memoryChatAssistant.chat("我是lina");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }

      @Autowired
    //注入接口
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory5(){
        String answer1 = separateChatAssistant.chat(1,"我是lina");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);
         String answer3 = separateChatAssistant.chat(2,"我是谁");
        System.out.println(answer3);
    }


    
    
    
}
