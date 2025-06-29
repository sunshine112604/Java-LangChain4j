package com.atguigu.java.ai.langchain4j;


import com.atguigu.java.ai.langchain4j.assistant.MemoryChatAssistant;
import com.atguigu.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {
    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testSystemMessage(){
        String answer = separateChatAssistant.chat(5, "今天几号");
        System.out.println(answer);
    }


    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Test
    public void testuserMessage(){
        String ansewr1 = memoryChatAssistant.chat("我是欢欢");
        System.out.println(ansewr1);
        String ansewr2 = memoryChatAssistant.chat("我18岁");
        System.out.println(ansewr2);
        String ansewr3 = memoryChatAssistant.chat("你知道我是谁吗,多大了");
        System.out.println(ansewr3);

    }



     @Test
    public void testV(){
        String ansewr1 = separateChatAssistant.chat2(6,"我是欢欢");
        System.out.println(ansewr1);
        String ansewr2 = separateChatAssistant.chat2(6,"我18岁");
        System.out.println(ansewr2);
        String ansewr3 = separateChatAssistant.chat2(6,"你知道我是谁吗,多大了");
        System.out.println(ansewr3);
    }



     @Test
    public void testuserInfo(){

        //从数据库中获取用户信息
        String username = "翠花";
        int age = 18;
        String ansewr = separateChatAssistant.chat3(7,"我是谁，我多大啦",username,age);
        System.out.println(ansewr);

    }


}

