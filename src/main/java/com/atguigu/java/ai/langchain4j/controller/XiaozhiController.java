package com.atguigu.java.ai.langchain4j.controller;


import com.atguigu.java.ai.langchain4j.assistant.XiaoZhiAgent;
import com.atguigu.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "小智")//服务于前端的测试页面
@RestController
@RequestMapping("/xiaozhi")//定义路径
public class XiaozhiController {

    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;
    @Operation(summary = "对话")//给测试页面
    @PostMapping(value = "/chat",produces = "text/stream;charset=utf-8")//定义路径
    public Flux<String> chat(@RequestBody ChatForm chatForm){
        return xiaoZhiAgent.chat(chatForm.getMemoryId(),chatForm.getMessage());
    }
}
