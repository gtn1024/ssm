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
        final var sqlSession = sqlSessionFactory.openSession();
        final var news = new HashMap<String, String>();
        news.put("title", "MyBatis");
        news.put("content", "MyBatis is a Java based framework for building SQL mapped XML files.");
        final var n = sqlSession.insert("dao.NewsMapper.saveNews", news);
        System.out.printf("Inserted %d rows.%n", n);
        // 提交事务
        sqlSession.commit();
        // 关闭Session
        sqlSession.close();
    }

    private static void updateTest() {
        final var sqlSession = sqlSessionFactory.openSession();
        final var news = new HashMap<String, Object>();
        news.put("id", 1);
        news.put("title", "MyBatis");
        news.put("content", "I love MyBatis.");
        final var n = sqlSession.update("dao.NewsMapper.updateNews", news);
        System.out.printf("Updated %d rows.%n", n);
        // 提交事务
        sqlSession.commit();
        // 关闭Session
        sqlSession.close();
    }

    private static void deleteTest() {
        final var sqlSession = sqlSessionFactory.openSession();
        final var n = sqlSession.delete("dao.NewsMapper.deleteNews", 2);

        System.out.printf("Deleted %d rows.%n", n);
        // 提交事务
        sqlSession.commit();
        // 关闭Session
        sqlSession.close();
    }

    private static void selectTest() {
        final var sqlSession = sqlSessionFactory.openSession();
        final var news = sqlSession.selectOne("dao.NewsMapper.getNewsById", 1);
        System.out.printf("%s%n", news);
        // 关闭Session
        sqlSession.close();
    }
}
