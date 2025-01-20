package miniproject.carrotmarket1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("miniproject.carrotmarket1.dao.MySQL")
public class CarrotMarket1Application {

    public static void main(String[] args) {
        SpringApplication.run(CarrotMarket1Application.class, args);
    }

}
