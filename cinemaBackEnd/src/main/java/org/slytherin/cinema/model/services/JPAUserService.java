package org.slytherin.cinema.model.services;

import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.repositories.abstractions.UserRepository;
import org.slytherin.cinema.model.services.abstraction.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class JPAUserService implements UserService {
    private UserRepository userRepository;

    public JPAUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> ou = userRepository.findById(id);
        return ou;
    }

    @Override
    public void createUser(User newUser, User user) {
        userRepository.save(newUser);
    }

    @Override
    public void deleteUserById(long userId, User user) {
        userRepository.deleteById(userId);

    }

    @Override
    public void updateUser(User updateUser, User user) {
        userRepository.save(updateUser);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
