package com.atguigu.java.ai.langchain4j.tools;


import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import dev.langchain4j.service.MemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(name = "加法",value = "将两个参数a和b相加并返回运算结果")
    double sum(
            //**value**：参数的描述信息，这是必填字段。
            //- **required**：表示该参数是否为必需项，默认值为 `true`，此为可选字段。
            //AIService方法中有一个参数使用 `@MemoryId` 注解，那么你也可以使用 `@ToolMemoryId` 注解 `@Tool` 方法中的一个参数
            //提供给AIService方法的值将自动传递给 `@Tool` 方法。如果你有多个用户，
            //或每个用户有多个聊天记忆，并且希望在 `@Tool` 方法中对它们进行区分，那么这个功能会很有用。
            @ToolMemoryId int memoryId,
            @P(value = "加数1",required = true) double a,
            @P(value = "加数1",required = true)double b){
        System.out.println("调用加法运算"+memoryId);
        return a+b;
    }

    @Tool(name = "平方根",value = "计算给定参数的平方根，并返回结果")
    double squareRoot( @ToolMemoryId int memoryId,double x){
        System.out.println("调用平方根运算"+memoryId);
        return Math.sqrt(x);
    }
}
