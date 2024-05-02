package com.example.myBoard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    } //비밀번호 암호화
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request //5
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() //2
                        .requestMatchers("/user/**").permitAll() //3
//                        .requestMatchers("/**").permitAll())
                        .anyRequest().authenticated()) //1 -> 4

                .formLogin((form) -> form //6
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login") //login 에서 submit 보낸 내용은 Controller 가 아닌 이쪽으로 온다.
//                        .usernameParameter("email") //email 로 login 을 실행하겠다는 의미 email 은 기본키가 아니라서 Repository 에 Query 메서드를 만들어서 eamil 을 찾아야한다
                        .defaultSuccessUrl("/"))

                .logout((out) -> out //7
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout")
                );
        return http.build(); //filterChain 생성해서 반환 //8
    }
}
