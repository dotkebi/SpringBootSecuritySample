package com.github.dotkebi.authentication;

import com.github.dotkebi.dao.AdminDao;
import com.github.dotkebi.models.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author by myoungjin on 2016-08-08.
 */
@Service
public class AuthServiceImpl implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        AdminUser user = (AdminUser) users.get(0);
        Set<GrantedAuthority> dbAuthSet = new HashSet<>();
        dbAuthSet.addAll(loadUserAuthorities(user.getUsername()));

        /*if (getEnableGroups()) {
            dbAuthSet.addAll(loadGroupAuthorities(user.getUsername()));
        }*/

        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthSet);
        user.setAuthorities(dbAuths);

        if (dbAuths.size() == 0) {
            throw new UsernameNotFoundException("Username " + username + " has no GrantedAuthority");
        }

        return user;
    }

    private List<UserDetails> loadUsersByUsername(String id) {
        return adminDao.selectUserById(id).stream()
                .map(item -> (UserDetails) item)
                .collect(Collectors.toList())
        ;
    }

    private List<GrantedAuthority> loadUserAuthorities(String id) {
        return adminDao.selectUserById(id).stream()
                .map(item -> new SimpleGrantedAuthority(getRolePrefix() + item.getRole()))
                .collect(Collectors.toList())
        ;
    }

    private String getRolePrefix() {
        return "";
    }

}
