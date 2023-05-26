package labs.entities;

import jakarta.validation.constraints.NotNull;

public class User {
    
    private Integer userId;

    @NotNull
    private String username;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
