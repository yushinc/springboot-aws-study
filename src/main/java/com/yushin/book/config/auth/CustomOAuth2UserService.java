package com.yushin.book.config.auth;

import com.yushin.book.config.auth.dto.OAuthAttributes;
import com.yushin.book.config.auth.dto.SessionUser;
import com.yushin.book.domain.user.User;

import com.yushin.book.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. OAuth2UserService의 구현체인 DefaultOAuth2UserService 생성
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        // 2. 구현체의 loadUser() 를 통해 사용자 정보 가져옴
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 3. 현재 로그인 진행 중인 서비스를 구분하는 코드, 여러 개의 소셜 로그인 연동시 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 4. OAuth2 로그인 시 키가 되는 필드값 (= PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 5. OAuth2UserService를 통해 가져온 OAuth2User의 속성을 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 6. 유저 생성 또는 수정
        User user = saveOrUpdate(attributes);

        // 7. 세션에 사용자 정보 저장
        // SessionUser: 세션에 사용자 정보를 저장하기 위한 dto - User 엔티티 대신 사용
        httpSession.setAttribute("user", new SessionUser(user));

        // 8. OAuth2 기반 사용자 정보를 나타내는 스프링 시큐리티 구현체 생성하여 리턴
        // 이 과정을 통해 사용자의 권한과 속성을 알 수 있음
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                // 사용자의 이름이나 사진이 변경되면 업데이트
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        // 유저 저장
        return userRepository.save(user);
    }
}