package com.atguigu.java.ai.langchain4j.config;


import com.atguigu.java.ai.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeparateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;
    @Bean
    ChatMemoryProvider chatMemoryProvider(){
        //将memoryId传递到MessageWindowChatMemory的id之中用->
        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(10)
                //通过这种方式配置，聊天记忆以键值对的方式存储
                //都是基于内存，将聊天记忆持久化在磁盘中；（内存空间不足，应用程序重启，聊天记忆丢失）
                //持久化聊天记忆

                //可以自己写一个实现类，来自己配置在这里，模仿 InMemoryChatMemoryStore或者SingleSlotChatMemoryStore
                //get message,update message,delete message
                //实现持久层
//                .chatMemoryStore(new InMemoryChatMemoryStore())

                //这是我们自定义的持久化，配置聊天记忆的1持久化
                //这里不能使用new，因为MongChatMemoryStore注入了MongoTemplate，如果用new的方式创建，MongoTemplate就不能注入了
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }
}

//持久化的存储介质
//MySQL,
// Redis:实时性，从内存存取，占用内存
// MongDB:文档型NoSQL数据库，（非关系型数据库），应对大数据量、高性能和灵活性需求，这个不用内存。
// 数据以 JSON - like 的文档形式存储，具有高度的灵活性和可扩展性。它不需要预先定义严格的表结构，适合存储半结构化或非结构化的数据。
// Cassanadra：分布式
