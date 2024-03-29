package recipes.casestudy.sequirity;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import recipes.casestudy.database.dao.UserDAO;
import recipes.casestudy.database.entity.User;

@Slf4j
@Component
public class AuthenticatedUserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * this function will return the username of the currently logged in user
     * or it will return null if no user is logged in
     * in our case the username is the email
     */
    public String getCurrentUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            Object objPrincipal = context.getAuthentication().getPrincipal();
            if (objPrincipal instanceof String) {
                // there is no spring security context for this user so they are not logged in
                log.debug(objPrincipal.toString());//anonymousUser
                return null;
            } else {
                final org.springframework.security.core.userdetails.User principal =
                        (org.springframework.security.core.userdetails.User) objPrincipal;
                return principal.getUsername();
            }
        }
        return null;
    }

    public User loadCurrentUser() {
        User user = userDao.findByEmailIgnoreCase(getCurrentUsername());
        return user;
    }

    public void authenticateNewUser(HttpSession session, String username, String unencryptedPassword) {
        // this function is called in the create user process to authenticate a brand new user
        Authentication request = new UsernamePasswordAuthenticationToken(username, unencryptedPassword);
        Authentication result = authenticationManager.authenticate(request);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(result);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
    }

    public User checkAuthCurrentUser() {
        User user = loadCurrentUser();
        if (user == null) {
            throw new SessionAuthenticationException("Authorized user not found. Session expired");
        }
        return user;
    }


}
