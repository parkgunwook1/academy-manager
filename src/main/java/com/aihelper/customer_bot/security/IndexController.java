package com.aihelper.customer_bot.security;

import com.aihelper.customer_bot.security.auth.PrincipalDetails;
import com.aihelper.customer_bot.security.model.User;
import com.aihelper.customer_bot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 return 하겠다.
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication
            , @AuthenticationPrincipal PrincipalDetails userDetails) { // DI[의존성 주입]
        System.out.println("/test/login ================");

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.getUser());

        System.out.println("userDetails : " +  userDetails.getUser());

        return "세션 정보 완료";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication
        ,@AuthenticationPrincipal OAuth2User oAuth) { // DI[의존성 주입]
        System.out.println("/test/login ================");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("oauth2User : " + oAuth.getAttributes());
        return "Oauth 세션 정보 완료";
    }

    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본 폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix),  .mustache (suffix) -> 자동으로 설정해준다.
        return "index";
    }

    // Oauth 로그인을 해도 PrincipalDetails
    // 일반 로그인을 해도 PrincipalDetails
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링시큐리티 해당주소를 낚아챈다.
    // SecurityConfig 파일 생성 후 시큐리티에서 제공하는 로그인 페이지가 없어지고 왜 아래 login이 출력 되는지 궁금함
    @GetMapping("/loginForm")
    public  String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public  String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user.toString());
        user.setRole("USER");
        String rqwPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rqwPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입 잘됨. 비밀번호 : 1234 => 시큐리티로 로그인할 수 없음. 이유는 패스워드가 암호화가 안되었기 때문이다.
        return "redirect:/loginForm";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
