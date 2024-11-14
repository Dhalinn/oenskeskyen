package Wishlist.M.service;

import Wishlist.M.model.User;
import Wishlist.M.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[0-9].*") && password.matches(".*[a-zA-Z].*");
    }

    public boolean registerUser(User user) {
        if (!isEmailRegistered(user.getEmail()) && isValidPassword(user.getPassword())) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    public User authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

}
