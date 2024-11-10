package myBank.myBank.model;

import myBank.myBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email : " + user.getEmail() + " is already registered");
        }
        switch (user.getRole()) {
            case "client":
                user.setCreditLimit(110000);
                break;
            case "admin":
                user.setCreditLimit(220000);
                break;
            case "owner":
                user.setCreditLimit(440000);
                break;
        }
        user.setDateOfCreated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new IllegalArgumentException("User with this email : " + user.getEmail() + " is not registered");
        }
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        existingUser.setDateOfLastEnter(LocalDateTime.now());
        userRepository.save(existingUser);
    }

    public String getUserRole(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        return existingUser.getRole();
    }


    public UserInfoResponse showUserInfo(User user) {
        switch (user.getRole()) {
            case "client":
                return new UserInfoResponse(userRepository.findById(user.getId()));
            case "admin":
                return new UserInfoResponse(userRepository.findAllExceptOwners());
            case "owner":
                return new UserInfoResponse(userRepository.findAll());
        }
        return null;
    }

    public User getUser (User user) {
        User foundUser  = userRepository.findByEmail(user.getEmail());

        if (foundUser  != null) {
            return foundUser ;
        }

        return null;
    }

    public void getMoney(User user, int amount) {
        user.setAccountAmount(user.getAccountAmount() + amount);
        user.setCreditLimit(user.getCreditLimit() - amount);
    }
}
