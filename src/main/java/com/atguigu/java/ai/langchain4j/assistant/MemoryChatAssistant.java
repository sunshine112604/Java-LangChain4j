package com.atguigu.java.ai.langchain4j.assistant;


import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;


//初级智能体
@AiService(
        wiringMode = EXPLICIT,
        chatModel =  "qwenChatModel",
        chatMemory = "chatMemory"//用bean
)
public interface  MemoryChatAssistant{

//    @UserMessage("你是我的好朋友，请用上海话回答问题。并添加一些表情符号{{it}}")只
//    能传递一个参数所以用这个，可以传递两个就最好用SeparateChatAssistant
    @UserMessage("你是我的好朋友，请用上海话回答问题。并添加一些表情符号{{message}}")
    //基于内存级别的聊天记录，是没办法在mongdb上存储，聊天记忆在内存里面
    //{{it}}表示这里唯一的参数的占位符
    //需要联网操作


    //**@V** 明确指定传递的参数名称
    //如果有两个或两个以上的参数，我们必须要用`@V`，在`SeparateChatAssistant`中定义方法`chat2`
    String chat(@V("message")String message);
}
