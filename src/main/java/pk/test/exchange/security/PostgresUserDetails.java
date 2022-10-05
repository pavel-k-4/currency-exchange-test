package pk.test.exchange.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pk.test.exchange.model.User;

import java.util.Collection;
import java.util.List;

public class PostgresUserDetails implements UserDetails {
    private final long id;
    private final String username;
    private final String password;

    public PostgresUserDetails(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User toUser() {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
}
