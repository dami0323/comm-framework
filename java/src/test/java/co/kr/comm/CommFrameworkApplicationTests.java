package co.kr.comm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class CommFrameworkApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void triggerRolling() throws InterruptedException {
        for (int i = 0; i < 2000; i++) {
            log.debug("롤링 테스트를 위한 대량 로그 생성 중... 번호: {}", i);
            log.info("현재 시간: {}", System.currentTimeMillis());
            // 용량 기반 롤링을 보고 싶다면 Thread.sleep 없이 실행
            // 시간 기반을 보고 싶다면 약간의 간격을 줌
            Thread.sleep(10);
        }
    }

    @Test
    void checkDatasourceConfig() throws SQLException {
        log.info("[datasource class] {}", dataSource.getClass().getName());
        try (var conn = dataSource.getConnection()) {
            var meta = conn.getMetaData();
            log.info("[JDBC URL] {}", meta.getURL());
            log.info("[DB Product] {} {}", meta.getDatabaseProductName(), meta.getDatabaseProductVersion());
            log.info("[User] {}", meta.getUserName());
        }
        // HikariCP, Tomcat JDBC 등 커넥션 풀별 상세 정보 출력 (가능한 경우)
//		if (dataSource.getClass().getName().contains("HikariDataSource")) {
//			com.zaxxer.hikari.HikariDataSource hikari = (com.zaxxer.hikari.HikariDataSource) dataSource;
//			log.info("[HikariCP] poolName={}, maxPoolSize={}, minIdle={}",
//					hikari.getPoolName(), hikari.getMaximumPoolSize(), hikari.getMinimumIdle());
//		}
        if (dataSource.getClass().getName().contains("org.apache.tomcat")) {
            org.apache.tomcat.jdbc.pool.DataSource tomcat = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
            log.info("[TomcatJDBC] maxActive={}, maxIdle={}, minIdle={}, initialSize={}",
                    tomcat.getMaxActive(), tomcat.getMaxIdle(), tomcat.getMinIdle(), tomcat.getInitialSize());
        }
    }

}
