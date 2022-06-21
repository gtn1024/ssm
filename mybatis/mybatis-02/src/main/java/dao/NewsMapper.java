package dao;

import domain.News;

// 由 MyBatis 负责提供实现类
public interface NewsMapper {
    int saveNews(News news);

    int updateNews(News news);

    int deleteNews(int id);

    News getNews(int id);
}
