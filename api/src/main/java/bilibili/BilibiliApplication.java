package bilibili;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "bilibili.dao")
public class BilibiliApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilibiliApplication.class, args);
	}

}
