package labs.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public class UserModel {
    
    @NotNull
    @Valid
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotNull
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
