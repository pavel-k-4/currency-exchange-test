package pk.test.exchange.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpClientErrorException;
import pk.test.exchange.security.PostgresUserDetails;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public UserDetails toUserDetails() {
        return new PostgresUserDetails(id, username, password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
