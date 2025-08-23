package com.example.education_system.auth.service.OAuth;

import com.example.education_system.auth.service.OAuth.info.OAuth2UserInfo;
import com.example.education_system.auth.service.OAuth.info.OAuth2UserInfoFactory;
import com.example.education_system.config.files.FileInfo;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.entity.UserRole;
import com.example.education_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder pswdEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        String email = oAuth2UserInfo.getEmail();

        UserEntity user;
        if (userRepository.existsByEmail(email)) {
            //Goes to OAuth Failure Handler
            // throw new OAuth2AuthenticationException("email already registered, please login");

            user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } else {
            user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        // Create new user if not exists
                        String name = oAuth2UserInfo.getName() + new Random().nextInt(900000) + 100000;
                        String password = "OAUTH_USER";
                        String url = oAuth2UserInfo.getImageUrl();
                        UserEntity newUser = new UserEntity();
                        newUser.setEmail(email);
                        newUser.setUsername(name);
                        newUser.setPassword(pswdEncoder.encode(password));
                        newUser.setImageFile(new FileInfo("", "", url, ""));
                        newUser.setRoles(Set.of(UserRole.ROLE_STUDENT));
                        return userRepository.save(newUser);
                    });
        }


        return new DefaultOAuth2User(user.getAuthorities(), attributes, "email");
    }
}

