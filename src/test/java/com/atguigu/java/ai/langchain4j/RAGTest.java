package com.atguigu.java.ai.langchain4j;


import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.HTML;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

@SpringBootTest
public class RAGTest {

    @Test
    public void testReadDocument(){

        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器TextDocumentParser对文档进行解析

        //loadDocument默认使用了DEFAULT_DOCUMENT_PARSER这个文档解析器，他的名字是TextDocumentParser
        //只能解析txt，html，md等纯文本格式的文件
        //如果是ppt，pdf，xls，xlsx，doc，docx等微软系列的文件，要使用其他的解析器
//        Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/小智医疗/test/test.txt");
//        System.out.println(document.text());

//    // 加载单个文档
//        //明确指定文档解析器，TextDocumentParser，但是因为是默认的，所以也可以不指定
//    Document document = FileSystemDocumentLoader.loadDocument
//            ("D:/BaiduNetdiskDownload/小智医疗/test/test.txt", new TextDocumentParser());
//
//    // 从一个目录中加载所有文档
//    List<Document> documents = FileSystemDocumentLoader.loadDocuments
//            ("D:/BaiduNetdiskDownload/小智医疗/test", new TextDocumentParser());

    // 从一个目录中加载所有的.txt文档
        //全局查找*.txt这样的文件，通过PathMatcher，
    PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
    List<Document> documents = FileSystemDocumentLoader.loadDocuments
            ("D:/BaiduNetdiskDownload/小智医疗/test", pathMatcher, new TextDocumentParser());

    for(Document document:documents){
        System.out.println("==============");
        System.out.println(document.metadata());//打印文档的源数据信息
        System.out.println(document.text());
    }

//    // 从一个目录及其子目录中加载所有文档
//        // loadDocumentsRecursively，加载test里的文档，以及test里面子目录的文档
//    List<Document> documents = FileSystemDocumentLoader.loadDocumentsRecursively
//            ("D:/BaiduNetdiskDownload/小智医疗/test", new TextDocumentParser());
    }

    @Test
public void testParsePDF() {
    Document document = FileSystemDocumentLoader.loadDocument(
            "D:/BaiduNetdiskDownload/小智医疗/test/test.pdf",
            new ApachePdfBoxDocumentParser()
    );
    System.out.println(document.metadata());
    System.out.println(document.text());
    }


    //langchain4j内置的向量存储In-memory
    //加载文档并存入向量数据库
    @Test
    public void testReadDocumentAndStore(){
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/小智医疗/test/test.txt");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();


        //ingest
        //1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        //3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)

        //把 document 和 embeddingStore 这两个参数传递给 EmbeddingStoreIngestor 类中的 ingest 方法，用于执行「向嵌入存储中写入/注册向量」的操作
        //ingest:三个操作，一个分割文档，一个文本向量化，然后将原始文本和向量存在向量数据库中
        EmbeddingStoreIngestor.ingest(document,embeddingStore);
        //查看向量数据库内容
        System.out.println(embeddingStore);
    }

    @Test
    public void testDocumentSplitter(){
        //从本地文件系统中读取文件 "test.txt"，并将其加载为 Document 对象
        Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/小智医疗/test/test.txt");
        //创建一个内存中的嵌入存储，用来保存生成的文本向量（embedding）
        //文本段对象，通常表示被拆分的文档片段
        //embeddingStore: 存储每个文本段（TextSegment）的嵌入向量的容器
        //得到一个空的内存向量存储，用于后续存入嵌入
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //创建一个文档分段器，按照段落粒度对文档进行拆分，并设置每段的长度控制
        //documentSplitter: 文档分段器实例
        //300: 每个段落最大 token 数（超过就切分）
        //30: 允许的段落之间最大重叠 token 数（提升上下文连贯性）
        //HuggingFaceTokenizer(): 一个用于将文本分割成 token 的分词器，可能是 BERT、GPT 等模型的 tokenizer。
        //得到一个能将长文分成多个小段的工具，支持控制段落大小和重叠
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
                300,
                30,
                //token分词器，按照token计算
                new HuggingFaceTokenizer());

        //按照字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);

        //构建一个文档处理器（Ingestor），负责执行“拆分文档 -> 对每段生成嵌入 -> 存入 embedding store”的整个流程
        //EmbeddingStoreIngestor.builder()：启动一个构建器，用于配置 ingest 操作。
        //.embeddingStore(embeddingStore)：设置向量存储目标为上面创建的 embeddingStore
        //.documentSplitter(documentSplitter)：设置文档拆分逻辑为前面创建的 documentSplitter。
        //.build()：构建完整的 EmbeddingStoreIngestor 实例
        //.ingest(document)：执行嵌入提取过程：对文档拆段、生成嵌入并保存
        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);
        System.out.println(embeddingStore);
    }

    @Test
            //基于千问大模型的token分词器
    public void testTokenCount(){
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
        HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();
        //QwenTokenizer tokenizer = new QwenTokenizer("sk-185858a7ded1479f80fffc6a4753ea42","qwen-max");
        int count = tokenizer.estimateTokenCountInMessage(userMessage);
        System.out.println("token长度"+ count);
    }
}


//文档解析器
//langchain4j 模块的文本文档解析器（TextDocumentParser），它能够解析纯文本格式的文件（例如 TXT、HTML、MD 等）
//langchain4j-document-parser-apache-pdfbox 模块的 Apache PDFBox 文档解析器（ApachePdfBoxDocumentParser），它可以解析 PDF 文件
//langchain4j-document-parser-apache-poi 模块的 Apache POI 文档解析器（ApachePoiDocumentParser），它能够解析微软办公软件的文件格式（例如 DOC、DOCX、PPT、PPTX、XLS、XLSX 等）
//langchain4j-document-parser-apache-tika 模块的 Apache Tika 文档解析器（ApacheTikaDocumentParser），它可以自动检测并解析几乎所有现有的文件格式

//RAG过程
//索引阶段
//加载解析文档---文档分割---片段通过向量模型转换成向量+文档分割的片段----存入到向量存储（嵌入存储）
//检索阶段
//