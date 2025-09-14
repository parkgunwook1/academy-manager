package com.aihelper.customer_bot.security.config.oauth;

import com.aihelper.customer_bot.security.auth.PrincipalDetails;
import com.aihelper.customer_bot.security.model.User;
import com.aihelper.customer_bot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    @Autowired
    private UserRepository userRepository;

    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.

    // 구글로 부터 받은 UserRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : " + userRequest.getClientRegistration());
        System.out.println("AccessToken : " + userRequest.getAccessToken().getTokenValue());


        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-Client 라이브러리) -> AccessToken요청
        // userRequest 정보 -> 회원프로필 받아야함(loadUser 함수 호출) -> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        // 회원가입을 강제로 진행예정
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + providerId; // google_1097428... -> 충돌되지 않는 방법
        String email = oAuth2User.getAttribute("email");
        String role = "USER";
        String phone = oAuth2User.getAttribute("phone");


        User userEntitiy = userRepository.findByUsername(username);

        if(userEntitiy == null) {
            // 구글 로그인이 최초입니다.
            System.out.println("구글 로그인이 최초입니다.");
            userEntitiy = User.builder().
                    email(email)
                    .phone(phone)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .username(username)
                    .build();
            userRepository.save(userEntitiy);
        } else {
            System.out.println("구글 로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
        }

        // 회원가입을 강제로 진행 예정
        return new PrincipalDetails(userEntitiy , oAuth2User.getAttributes());
    }
}
