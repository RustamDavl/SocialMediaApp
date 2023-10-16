package com.rustdv.socialmediaapp.config;

import com.rustdv.socialmediaapp.entity.UserInfo;
import com.rustdv.socialmediaapp.service.CustomUserDetails;
import com.rustdv.socialmediaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final UserInfo userInfo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/styles/**", "/js/**", "/img/**")
                        .permitAll()
                        .requestMatchers("/login", "/registration", "/signUp")
                        .permitAll()
                        .requestMatchers("/**")
                        .authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                )
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserOAuth2UserService())
                        )
                        .successHandler(customAuthenticationSuccessHandler)
                );
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserOAuth2UserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            CustomUserDetails userDetails = (CustomUserDetails)userService.loadUserByUsername(email);
            userInfo.setId(userDetails.getId());
            // TODO: 28.07.2023 if user does not exist - save him/her
            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> methodSet = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(
                    SecurityConfig.class.getClassLoader(), new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> methodSet.contains(method) ? method.invoke(userDetails, args) :
                            method.invoke(oidcUser, args)
            );
        };

    }


}
