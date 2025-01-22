package miniproject.carrotmarket1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/kakaoLogin").permitAll() // 경로 허용
                        .requestMatchers("/**").permitAll() // 공개 URL 허용
//                        .requestMatchers("/user/**", "/products/**", "/chat/**").authenticated() // 인증 필요 경로
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/userlogin") // 커스텀 로그인 페이지 설정
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/products")
                        .permitAll()
                );

        return http.build();
    }


}
