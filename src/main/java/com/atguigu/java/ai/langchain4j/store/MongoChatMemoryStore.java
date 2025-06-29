package com.atguigu.java.ai.langchain4j.store;

import com.atguigu.java.ai.langchain4j.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    //MongoTemplate是注入进来的，MongChatMemoryStore就不能new出来，所以加上@Component初始化在spring的上下文之中
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    //这里的ChatMessage是Langchain4j,包含了userMessage和AiMessage
    public List<ChatMessage> getMessages(Object memoryId) {
        //创造查询条件
         Criteria criteria = Criteria.where("memoryId").is(memoryId);
         Query query = new Query(criteria);
         //根据查询条件，希望返回的数据是ChatMessages
        //ChatMessages是我们自己定义的数据类型，用于封装聊天记忆
        //content:存的是将聊天记忆列表转换成json字符串形式的内容
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        if(chatMessages == null){
            return new LinkedList<>();
        }

       String contentJson = chatMessages.getContent();//聊天记录的Json形式
        //将Json形式转换成List<ChatMessage>
//        List<ChatMessage> messages =  ChatMessageDeserializer.messagesFromJson(contentJson);
//        return new LinkedList<>(messages);

//        return (List<ChatMessage>) ChatMessageDeserializer.messagesFromJson(contentJson);
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());

    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
         Criteria criteria = Criteria.where("memoryId").is(memoryId);
         Query query = new Query(criteria);
         Update update = new Update();
        String string = ChatMessageSerializer.messagesToJson(list);
        update.set("content", string);

         //upsert:修改和新增
         mongoTemplate.upsert(query,update,ChatMessages.class);

    }

    @Override
    public void deleteMessages(Object memoryId) {
         Criteria criteria = Criteria.where("memoryId").is(memoryId);//按照memoryId删除，is（memoryId）是传进来的参数
         Query query = new Query(criteria);
         mongoTemplate.remove(query, ChatMessages.class);
    }
}
