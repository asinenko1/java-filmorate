package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User findUserById(Integer id) {
        User user = userStorage.getById(id);

        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }

        return user;
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        log.info("Пользователь с id {} Добавил в друзья пользователя с id {}", userId, friendId);

    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public Collection<User> findFriends(Integer userId) {
        User user = findUserById(userId);

        return user.getFriends()
                .stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> findCommonFriends(Integer userId, Integer otherUserId) {
        User user = findUserById(userId);
        User otherUser = findUserById(otherUserId);

        Set<Integer> otherFriends = otherUser.getFriends();

        return user.getFriends()
                .stream()
                .filter(otherFriends::contains)
                .map(this::findUserById)
                .collect(Collectors.toList());
    }
}
