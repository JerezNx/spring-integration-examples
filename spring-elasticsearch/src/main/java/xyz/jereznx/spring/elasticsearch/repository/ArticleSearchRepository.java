package xyz.jereznx.spring.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import xyz.jereznx.spring.elasticsearch.entity.Article;

/**
 * @author liqilin
 * @since 2021/5/1 13:55
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, String> {

}
