package com.atguigu.java.ai.langchain4j.config;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MemoryChatAssistantConfig {
    @Bean
    //chatMemory就是MemoryChatAssistant里的chatMemory，这样Bean对象才能自动注入到chatMemory的位置
   ChatMemory chatMemory(){
        return MessageWindowChatMemory.withMaxMessages(10);
    }

}
