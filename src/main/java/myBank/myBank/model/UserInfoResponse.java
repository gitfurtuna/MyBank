package myBank.myBank.model;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserInfoResponse {
    private Optional<User> user;
    private List<User> users;

    public UserInfoResponse() {
    }

    public UserInfoResponse(Optional<User> user) {
        this.user = user;
    }

    public UserInfoResponse(List<User> users) {
        this.users = users;
    }

    public Optional<User> getUser () {
        return user;
    }

    public List<User> getUsers() {
        return users;
    }
}
