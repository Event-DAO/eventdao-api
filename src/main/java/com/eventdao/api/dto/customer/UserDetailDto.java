package com.eventdao.api.dto.customer;

import com.eventdao.api.entity.constant.Role;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.entity.customer.CustomerPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Builder
public class UserDetailDto implements UserDetails  {

    private final Customer customer;
    @JsonIgnore
    private final CustomerPassword customerPassword;
    private final List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return customerPassword.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public CustomerPassword getCustomerPassword() {
        return customerPassword;
    }
}
