package com.github.dotkebi.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author by myoungjin on 2016-07-29.
 */
public class AdminUser implements UserDetails {

    private String id;
    private String password;
    private String name;
    private String role;
    private Integer enabled;
    private Set<GrantedAuthority> authorities;

    public AdminUser() {
    }

    public AdminUser(String id, String password, String name, String role, Integer enabled) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.enabled = enabled;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

        SortedSet<GrantedAuthority> sortedSet = new TreeSet<>(new AuthorityComparator());
        authorities.forEach(grantedAuthority -> {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedSet.add(grantedAuthority);
        });
        return sortedSet;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        @Override
        public int compare(GrantedAuthority o1, GrantedAuthority o2) {
            if (o2.getAuthority() == null) {
                return -1;
            }
            if (o1.getAuthority() == null) {
                return 1;
            }
            return o1.getAuthority().compareTo(o2.getAuthority());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }


    public void setUserName(String username) {
        this.id = username;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled == 1;
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
