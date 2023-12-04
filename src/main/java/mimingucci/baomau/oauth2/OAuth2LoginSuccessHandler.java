package mimingucci.baomau.oauth2;

import java.io.IOException;

import mimingucci.baomau.entity.AuthenticationType;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	private UserService service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {
		UserOauth2User oauth2User=(UserOauth2User) authentication.getPrincipal();
		String name=oauth2User.getName();
		String email=oauth2User.getEmail();
		String clientName=oauth2User.getClientName();
		AuthenticationType authenticationType=getAuthenticationType(clientName);
		User user= null;
		try {
			user = service.findUserByEmail(email);
		} catch (UserNotFoundException e) {
			user=null;
		}
		if(user==null) {
			service.addNewUserUponOAuthLogin(name, email, authenticationType);
		}else {
			oauth2User.setFullName(user.getFullname());
			service.updateAuthenticationType(user, authenticationType);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	private AuthenticationType getAuthenticationType(String clientName) {
		if (clientName.equals("Google")) {
			return AuthenticationType.GOOGLE;
		} else {
			if(clientName.equals("Facebook")) {
				return AuthenticationType.FACEBOOK;
			}else {
				return AuthenticationType.DATABASE;
			}
			
		}
	}
}
