package br.com.mardoqueu.redditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}

}
