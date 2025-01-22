package miniproject.carrotmarket1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@MapperScan("miniproject.carrotmarket1.dao.MySQL")
public class CarrotMarket1Application {

    public static void main(String[] args) {
        SpringApplication.run(CarrotMarket1Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder PasswordEncoder()
    {//사용자가 입력한 패스워드를 변환(input_pwd encrypted)
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

}
