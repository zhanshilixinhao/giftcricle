package com.chouchong;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.chouchong.dao")

@EnableScheduling
public class GiftcricleApplication extends SpringBootServletInitializer {

	 public static void main(String[] args) {
		SpringApplication.run(GiftcricleApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(GiftcricleApplication.class);
//	}
}
