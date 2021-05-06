package xyz.jereznx.spring.elasticsearch.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import xyz.jereznx.spring.elasticsearch.entity.Article;

import java.util.*;

/**
 * @author liqilin
 * @since 2021/5/1 13:59
 */
@SpringBootTest
public class ArticleSearchRepositoryTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    /**
     * http://localhost:9200/article_index/_mapping
     */
    @Test
    void init() {
        System.out.println("init test");
    }

    @Test
    void save() {
        Article article = new Article();
//        不指定id的话，会生成一串字符串类型的id，很奇怪，明明指定了类型是Long
//        article.setId(1L);
        article.setId("1");
        article.setTitle("noIdTest2");
        article.setAbstracts("testA");
        article.setContent("testC");
        article.setPostTime(new Date());
        article.setClickCount(100L);
//        有则修改，无则新增
        final Article res = articleSearchRepository.save(article);
        System.out.println(article == res);
//        虽然会自动生成id，但不会回填
        System.out.println(res.getId());

    }

    @Test
    void batchSave() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
//        不指定id的话，会生成一串字符串类型的id，很奇怪，明明指定了类型是Long
//        article.setId(1L);
            article.setTitle("test" + i);
            article.setAbstracts("testA");
            article.setContent("testC");
            article.setPostTime(new Date());
            article.setClickCount(100L);
            list.add(article);
        }
        articleSearchRepository.saveAll(list);
    }

    @Test
    void query() {
        final Optional<Article> article = articleSearchRepository.findById("z7BiQHkB8JeGPyFPMlLj");
        article.ifPresent(System.out::println);
    }

    @Test
    void queryAll() {
        final Iterable<Article> all = articleSearchRepository.findAll();
        final Iterator<Article> iterator = all.iterator();
        while (iterator.hasNext()) {
            final Article article = iterator.next();
            System.out.println(article);
        }
    }

    @Test
    void delete() {
        articleSearchRepository.deleteById("z7BiQHkB8JeGPyFPMlLj");
    }

    @Test
    void deleteAll() {
        articleSearchRepository.deleteAll();
    }

    @Test
    void deleteIndex() {
        final IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Article.class);
        System.out.println(indexOperations.exists());
        final boolean b = indexOperations.delete();
        System.out.println(b);
    }
}
