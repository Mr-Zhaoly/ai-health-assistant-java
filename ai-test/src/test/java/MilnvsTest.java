import com.zly.AiTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = AiTestApplication.class)
public class MilnvsTest {

    @Test
    public void testMilnvs(@Autowired VectorStore vectorStore) {
//        List<Document> documents = List.of(
//                Document.builder()
//                        .text("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!")
//                        .metadata(Map.of("Spring1", "meta1"))
//                        .build(),
//                Document.builder()
//                        .text("The World is Big and Salvation Lurks Around the Corner")
//                        .metadata(Map.of("World2", "meta2"))
//                        .build(),
//                Document.builder()
//                        .text("You walk forward facing the past and you turn back toward the future.")
//                        .metadata(Map.of("Walk3", "meta3"))
//                        .build()
//                );
//
//        // Add the documents to Milvus Vector Store
//        vectorStore.add(documents);

        // Retrieve documents similar to a query
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
        System.out.println(results);
    }
}
