package my.ssh.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by zzy on 16/3/30.
 */
public class UserDetailsUtils {

    public static UserDetails getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication){
            return (UserDetails)authentication.getPrincipal();
        }else{
            return null;
        }

    }

    public static String getUsername(){
        UserDetails userDetails = getUserDetails();
        if(null != userDetails)
            return userDetails.getUsername();
        else
            return null;
    }

}
