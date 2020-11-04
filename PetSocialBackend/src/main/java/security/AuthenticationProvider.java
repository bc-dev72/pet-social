package security;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import security.util.TokenManager;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String[] tokens = authentication.getCredentials().toString().split(Pattern.quote("|||||")); 
		if(tokens.length != 2 && tokens.length != 1)
			throw new UsernameNotFoundException("Don't work"); 

		String token = tokens[0].trim();
//		String cookie = tokens[1].trim();  //TODO need to figure out fix for safari browser 
		
		if(token == null)
			throw new UsernameNotFoundException("Cannot find user with authentication token=" + token);
		if(!TokenManager.isTokenValid(token))
			throw new UsernameNotFoundException("Cannot find user with authentication token=" + token);
//		if(CookieManager.findEmailByToken(cookie).equals(""))
//			throw new UsernameNotFoundException("Cannot find user with authentication cookie=" + cookie);
		
		
		if(TokenManager.isTokenValid(token))
			return new User("user", "password", true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
		return null;
	}

}
