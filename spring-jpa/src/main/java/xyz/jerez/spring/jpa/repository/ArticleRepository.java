package xyz.jerez.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.jerez.spring.jpa.domain.Article;

import java.util.List;

/**
 * @author liqilin
 * @since 2021/5/1 16:11
 */
public interface ArticleRepository extends JpaRepository<Article, String> {

    List<Article> findAllByTitleContains(String title);

}
