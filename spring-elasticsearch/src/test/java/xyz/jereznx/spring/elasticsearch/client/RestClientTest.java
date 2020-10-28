package xyz.jereznx.spring.elasticsearch.client;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import xyz.jereznx.spring.elasticsearch.entity.Bank;
import xyz.jereznx.spring.elasticsearch.entity.MyTest;

/**
 * @author LQL
 * @since Create in 2020/8/23 15:54
 */
@SpringBootTest
public class RestClientTest {

    @Autowired
    RestHighLevelClient highLevelClient;

//    RestClient lowLevelClient = highLevelClient.getLowLevelClient();

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void test1() {
//        elasticsearchOperations
//                .search(Query);

//        final Bank bank = elasticsearchOperations
//                .queryForObject(GetQuery.getById("1"), Bank.class);
        Bank bank = elasticsearchOperations.get("1",Bank.class);
        System.out.println(bank);
        final SearchHits<Bank> list = elasticsearchOperations.search(Query.findAll(), Bank.class);
        System.out.println(list.getTotalHits());
        final SearchHit<Bank> searchHit = list.getSearchHit(0);
        final Bank content = searchHit.getContent();
//        elasticsearchOperations.sa
    }

    @Test
    void test2() {
        final IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(MyTest.class);
        System.out.println(indexOperations.exists());
        final boolean b = indexOperations.create();
        System.out.println(b);
    }

    @Test
    void test3() {
        MyTest myTest = new MyTest();
        myTest.setId("2");
        myTest.setName("zxz");
        myTest.setDesc("l");
        myTest.setPro("123213");
        final MyTest save = elasticsearchRestTemplate.save(myTest);
    }
}
