package pt.ipleiria.estg.dei.ei.dae.academics.dtos;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;
    private String dtype;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String name, String email, String dtype) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.dtype = dtype;
    }
    public static UserDTO from(User users) {
        return new UserDTO(
                users.getUsername(),
                users.getPassword(),
                users.getName(),
                users.getEmail(),
                //users.getDtype()
                Hibernate.getClass(users).getSimpleName()

        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }
}
