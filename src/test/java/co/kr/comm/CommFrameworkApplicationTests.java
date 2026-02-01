package co.kr.comm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CommFrameworkApplicationTests {

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

}
