package com.atguigu.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        //chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaozhi",//配置聊天记忆
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverXiaozhiPincone"//配置向量存储

)
public interface XiaoZhiAgent {

    @SystemMessage(fromResource = "xiaozhi-promte-template.txt")
    Flux<String> chat(@MemoryId Long question, @UserMessage String userMessage);
}
