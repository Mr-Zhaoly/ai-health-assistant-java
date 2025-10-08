import com.zly.MainApplication;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.database.request.CreateDatabaseReq;
import io.milvus.v2.service.database.request.DescribeDatabaseReq;
import io.milvus.v2.service.database.request.DropDatabaseReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MainApplication.class)
public class A_MilnvsDatabaseTest {

    private static MilvusClientV2 client = null;

    static {
        ConnectConfig config = ConnectConfig.builder()
                .uri("http://localhost:19530")
                .token("root:Milvus")
                .build();
        client = new MilvusClientV2(config);
    }

    @Test
    public void listDatabase() {
        System.out.println(client.listDatabases());
    }

    @Test
    public void detailsDatabase() {
        System.out.println(client.describeDatabase(DescribeDatabaseReq.builder()
                .databaseName("my_database_1")
                .build()));
    }

    @Test
    public void createDatabase() {
        CreateDatabaseReq createDatabaseReq = CreateDatabaseReq.builder()
                .databaseName("ai_health_assistant")
                .build();
        client.createDatabase(createDatabaseReq);
    }

    @Test
    public void deleteDatabase() {
        client.dropDatabase(DropDatabaseReq.builder()
                .databaseName("my_database_1")
                .build());
        System.out.println(client.listDatabases());
    }

}
