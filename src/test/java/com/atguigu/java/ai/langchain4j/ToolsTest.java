package com.atguigu.java.ai.langchain4j;


import com.atguigu.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testCalcutorTools(){
        String answer = separateChatAssistant.chat(3, "1+2等于几，475695037565的平方根是多少");
         System.out.println(answer);
    }
}

