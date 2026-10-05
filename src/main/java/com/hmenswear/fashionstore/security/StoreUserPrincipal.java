package com.hmenswear.fashionstore.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class StoreUserPrincipal implements UserDetails {
    public enum Type { CUSTOMER, EMPLOYEE }
    private final Long id;
    private final String email;
    private final String password;
    private final String displayName;
    private final String roleLabel;
    private final Type type;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public StoreUserPrincipal(Long id, String email, String password, String displayName, String roleLabel,
                              Type type, Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        this.id=id; this.email=email; this.password=password; this.displayName=displayName;
        this.roleLabel=roleLabel; this.type=type; this.authorities=authorities; this.enabled=enabled;
    }
    public Long getId(){ return id; }
    public String getDisplayName(){ return displayName; }
    public String getRoleLabel(){ return roleLabel; }
    public Type getType(){ return type; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities(){ return authorities; }
    @Override public String getPassword(){ return password; }
    @Override public String getUsername(){ return email; }
    @Override public boolean isAccountNonExpired(){ return true; }
    @Override public boolean isAccountNonLocked(){ return enabled; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled(){ return enabled; }
}
