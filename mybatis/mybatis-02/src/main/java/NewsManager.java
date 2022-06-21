import dao.NewsMapper;
import domain.News;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.HashMap;

public class NewsManager {
    private static SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) throws IOException {
        var resource = "mybatis-config.xml";
        // 从类加载路径下加载文件
        final var stream = Resources.getResourceAsStream(resource);
        // 构建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
        insertTest();
        updateTest();
        deleteTest();
        selectTest();
    }

    private static void insertTest() {
        try (final var sqlSession = sqlSessionFactory.openSession()) {
            final var news = new News(null, "MyBatis", "MyBatis is a Java based framework for building SQL mapped XML files.");
            final var newsMapper = sqlSession.getMapper(NewsMapper.class);
            final var n = newsMapper.saveNews(news);
            System.out.printf("Inserted %d rows.%n", n);
            // 提交事务
            sqlSession.commit();
        }
    }

    private static void updateTest() {
        try (final var sqlSession = sqlSessionFactory.openSession()) {
            final var news = new News(1, "MyBatis", "I love MyBatis.");
            final var newsMapper = sqlSession.getMapper(NewsMapper.class);
            final var n = newsMapper.updateNews(news);
            System.out.printf("Updated %d rows.%n", n);
            // 提交事务
            sqlSession.commit();
        }
    }

    private static void deleteTest() {
        try (final var sqlSession = sqlSessionFactory.openSession()) {
            final var newsMapper = sqlSession.getMapper(NewsMapper.class);
            final var n = newsMapper.deleteNews(2);

            System.out.printf("Deleted %d rows.%n", n);
            // 提交事务
            sqlSession.commit();
        }
    }

    private static void selectTest() {
        try (final var sqlSession = sqlSessionFactory.openSession()) {
            final var newsMapper = sqlSession.getMapper(NewsMapper.class);
            final var news = newsMapper.getNews(1);
            System.out.printf("%s%n", news);
        }
    }
}
