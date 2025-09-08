package com.aihelper.customer_bot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 필터를 스프링 필터체인에 등록
public class SecurityConfig{


// 옛날 버전 아래를 사용한다면 권한이 없는 페이지에 가면 403 에러를 발생한다.
//  extends WebSecurityConfigurerAdapter

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN' or hasRole('ROLE_MANAGER)')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest()
//                .permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login");
//    }


    /**
     * .formLogin(login -> login.loginPage("/login"))
     * 위와 같이 설정하는 순간 시큐리티에서 제공하는 기본 로그인 페이지를 쓰지 않고 /login URL을 개발자가 직접 제공하라로 바뀐다.
     * 즉 , 기본 로그인 페이지는 비활성화되고 , /login 엔드포인트는 직접 구현해야 한다.
     *
     * **/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/loginForm") // 커스텀 로그인 페이지
                );
        return http.build();
    }

    // 해당 메서드의 리턴되는 오브젝트를 IOC로 등록 해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
