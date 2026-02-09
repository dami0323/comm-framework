package co.kr.comm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class CommFrameworkApplication {


    public static void main(String[] args) {
        SpringApplication.run(CommFrameworkApplication.class, args);
    }

//	@Bean
//	public ApplicationRunner runner() {
//		return args -> {
//
//			// 스프링 부트 로깅 초기화가 끝난 이후 테스트 시작
//			long start = System.currentTimeMillis();
//
//			for (int i = 0; i < 100_000; i++) {
//				log.info("Test log message {}", i);
//			}
//
//			// 비동기 로깅 flush (여기서 실제 IO 쓰기까지 기다림)
//
//			long end = System.currentTimeMillis();
//			log.info("REAL Elapsed: {} ms", (end - start));
//			// [SYNC] REAL Elapsed: 2263 ms
//			// [ASYNC LOGGER] REAL Elapsed: 366 ms
//			// [ASYNC APPENDER] REAL Elapsed: 1905 ms
//		};
//	}

}
