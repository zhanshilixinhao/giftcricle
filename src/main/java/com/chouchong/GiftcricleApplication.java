package com.chouchong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.chouchong.dao")
public class GiftcricleApplication extends SpringBootServletInitializer {

	 public static void main(String[] args) {
		SpringApplication.run(GiftcricleApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(GiftcricleApplication.class);
//	}
}
