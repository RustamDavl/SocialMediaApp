package com.rustdv.socialmediaapp.config;

import com.rustdv.socialmediaapp.entity.UserInfo;
import com.rustdv.socialmediaapp.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final String defaultTargetUrl = "/users"; // Change this to your desired default URL
    private final UserInfo userInfo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        appendUserIdToDefaultUrl(request, response, authentication);
    }

    private void appendUserIdToDefaultUrl(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        response.sendRedirect(response.encodeRedirectURL(targetUrl));
    }

    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {
        // Get the user's ID here (replace 'getUserId()' with your own method to obtain the ID)
        String userId = getUserInfo(authentication);

        return defaultTargetUrl + "/" + userId;

    }

    // Replace this method with your own logic to retrieve the user's ID from the authentication object
    private String getUserInfo(Authentication authentication) {
        if(userInfo.getId() != null) {

            return userInfo.getId().toString();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getId().toString();
    }
}
