package com.atguigu.java.ai.langchain4j.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
//在chat_memory_db下面自动创建chat_messages集合
//给文档定义两个属性，一个是id，和存储当前聊天记录的json的字符串
//content：存所有的聊天记录列表，包含客户端发送给大模型的usermessage，也包含响应给客户端的aimessage
//转换成json，存储在content
@Document("chat_messages")
public class ChatMessages {
    //唯一标识，映射到MongDB文档的_id字段，类型Object，mongodb里面自己的id
    @Id
    private Object messageId;
    //private Long messageId;

    //聊天记忆id
    private  String memoryId;

    private String content;
}
