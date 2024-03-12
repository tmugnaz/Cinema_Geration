package org.slytherin.cinema.model.services.abstraction;

import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    void createUser(User newUser, User user);
    void deleteUserById(long userId, User user);
    void updateUser(User updateUser, User user);

    List<User> findAllUser();

}
