import com.zly.MainApplication;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.DataType;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.GetLoadStateReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MainApplication.class)
public class C_MilnvsCollectionsTest {

    private static MilvusClientV2 client = null;

    static {
        ConnectConfig config = ConnectConfig.builder()
                .uri("http://localhost:19530")
                .token("root:Milvus")
                .build();
        client = new MilvusClientV2(config);
        try {
            client.useDatabase("ai_health_assistant");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createCollection() {
        CreateCollectionReq.CollectionSchema schema = client.createSchema();

        // 3.2 Add fields to schema
        schema.addField(AddFieldReq.builder()
                .fieldName("id")
                .dataType(DataType.Int64)
                .isPrimaryKey(true)
                .autoID(false)
                .build());

        schema.addField(AddFieldReq.builder()
                .fieldName("vector")
                .dataType(DataType.FloatVector)
                .dimension(1024)
                .build());

        schema.addField(AddFieldReq.builder()
                .fieldName("category")
                .dataType(DataType.VarChar)
                .maxLength(512)
                .build());
        // 3.4 Create a collection with schema and index parameters
        CreateCollectionReq dietaryGuidelines = CreateCollectionReq.builder()
                .collectionName("dietary_guidelines")
                .collectionSchema(schema)
                .indexParams(null)
                .build();

        client.createCollection(dietaryGuidelines);

        // 3.5 Get load state of the collection
        GetLoadStateReq dietaryGuidelines1 = GetLoadStateReq.builder()
                .collectionName("dietary_guidelines")
                .build();

        Boolean loaded = client.getLoadState(dietaryGuidelines1);
        System.out.println(loaded);
    }

}
