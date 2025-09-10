package com.aihelper.customer_bot.security.config;

import com.aihelper.customer_bot.security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 필터를 스프링 필터체인에 등록
// @EnableGlobalMethodSecurity(securedEnabled = true) // => secured 어노테이션 활성화인데 시큐리티 5 이하 버전에 사용된다.
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true , prePostEnabled = true) // 컨트롤러나 서비스 메서드에 붙는 @PreAuthorize, @Secured, @RolesAllowed 같은 메서드 보안 어노테이션을 해석하도록 스프링 시큐리티에게 알려주는 역할을 한다.
// prePostEnabled = true 방식중 @PreAuthorize 방식을 추천한다. 이 방식은 메서드 실행 이전에 조건을 검사한다.

public class SecurityConfig{

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    /**
     * .formLogin(login -> login.loginPage("/login"))
     * 위와 같이 설정하는 순간 시큐리티에서 제공하는 기본 로그인 페이지를 쓰지 않고 /login URL을 개발자가 직접 제공하라로 바뀐다.
     * 즉 , 기본 로그인 페이지는 비활성화되고 , /login 엔드포인트는 직접 구현해야 한다.
     *
     * **/

    // 시큐리티
    // 1.코드받기(인증) , 2.액세스토큰(권한) ,
    // 3.사용자 프로필정보 가져오고 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도함.
    // 4-2 기본적인 정보 : (이메일 , 전화번호 , 이름, 아이디) 쇼핑몰 -> 집주소 , 백화점몰 -> vip 등급 , 일반등급 필요함.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/loginForm") // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                        .usernameParameter("username")
                        .defaultSuccessUrl("/")       // success가 되면 루트로 보낸다.
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/loginForm")    // 구글 로그인이 완료된 뒤의 후처리가 필요함. 액세스토큰 + 사용자프로필정보 받는다.
                        .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
                );
        return http.build();
    }

    // 해당 메서드의 리턴되는 오브젝트를 IOC로 등록 해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
