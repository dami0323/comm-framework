package co.kr.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommFrameworkApplication {

	private static Logger logger = LoggerFactory.getLogger(CommFrameworkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CommFrameworkApplication.class, args);
		logger.info("[CommFrameworkApplication.main] test 중");
    }

}
