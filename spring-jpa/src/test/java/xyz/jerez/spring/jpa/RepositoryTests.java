package xyz.jerez.spring.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import xyz.jerez.spring.jpa.domain.Article;
import xyz.jerez.spring.jpa.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liqilin
 * @since 2021/5/1 17:03
 */
@SpringBootTest
public class RepositoryTests {

    @Autowired
    private ArticleRepository repository;

    @Test
    void save() {
        Article article = new Article();
        article.setId("a");
        article.setTitle("noIdTest");
        article.setAbstracts("testA");
        article.setContent("testC");
        article.setPostTime(new Date());
        article.setClickCount(100L);
        final Article res = repository.save(article);
        System.out.println(article == res);
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
        repository.saveAll(list);
    }

    @Test
    void page() {
//        页码从0开始
        final Page<Article> all = repository.findAll(PageRequest.of(0, 10));
        System.out.println(all.getTotalElements());
    }

}
