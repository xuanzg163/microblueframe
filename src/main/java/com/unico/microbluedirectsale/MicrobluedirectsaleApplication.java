package com.unico.microbluedirectsale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@MapperScan("com.unico.microbluedirectsale.mapper")
@EnableOpenApi
public class MicrobluedirectsaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrobluedirectsaleApplication.class, args);
    }

}
