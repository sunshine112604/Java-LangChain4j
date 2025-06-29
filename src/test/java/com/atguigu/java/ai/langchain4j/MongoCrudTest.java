package com.atguigu.java.ai.langchain4j;


import com.atguigu.java.ai.langchain4j.bean.ChatMessages;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档
     */
//    @Test
//    public void testInsert(){
//        //insert里面接收一个对象，是之前的bean下面的ChatMessage，id是Long型
//        mongoTemplate.insert(new ChatMessages(1L,"聊天记录"));
//    }

     @Test
    public void testInsert2(){
        //Id是Object类型
         ChatMessages chatMessages = new ChatMessages();
         chatMessages.setContent("聊天记录列表");
         mongoTemplate.insert(chatMessages);
    }

     @Test
     /**
      * 根据id查询文档
      */
    public void testFindById(){
         ChatMessages chatMessages = mongoTemplate.findById("684ec0fcafddeb782d3516e8", ChatMessages.class);
         System.out.println(chatMessages);
     }


     @Test
     /**
      * 修改或新增文档
      */
    public void testUpdate(){
         Criteria criteria = Criteria.where("_id").is("100");
         Query query = new Query(criteria);
         Update update = new Update();
         update.set("content","新的聊天记录列表");

         //upsert:修改和新增
         mongoTemplate.upsert(query,update,ChatMessages.class);
     }

       @Test
     /**
      * 删除文档
      */
    public void testDelete(){
         Criteria criteria = Criteria.where("_id").is("100");
         Query query = new Query(criteria);

         mongoTemplate.remove(query,ChatMessages.class);
     }




}
