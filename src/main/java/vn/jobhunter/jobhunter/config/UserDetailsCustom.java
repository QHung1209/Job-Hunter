package vn.jobhunter.jobhunter.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.service.UserService;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService{

    private final UserService userService;

    public UserDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = this.userService.handleGetUserByUserName(username);
            if(user == null)
            {
                throw new UsernameNotFoundException("Username not found");
            }
            
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority("Role_User")));
        }
    
}
