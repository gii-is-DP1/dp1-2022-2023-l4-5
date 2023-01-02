/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.nt4h.user;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.exceptions.IncorrectPasswordException;
import org.springframework.samples.nt4h.game.exceptions.UserInAGameException;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        if (user.getEnable() == null) user.setEnable("1");
        if (user.getTier()== null) user.setTier(Tier.IRON);
        if (user.getAuthority()== null) user.setAuthority("USER");
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {

        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    // Obtener los usuarios de cinco en cinco.
    public Page<User> getAllUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    @Transactional
    public void deleteUser(User user) {
        user.onDeleteSetNull();
        userRepository.save(user);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUserById(int id) {
        User user = getUserById(id);
        deleteUser(user);
    }

    @Transactional(readOnly = true)
    public boolean userExists(int id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        if (ud != null) {
            return getUserByUsername(ud.getUsername());
        } else {
            return new User();
        }
    }

    @Transactional
    public List<User> getFriends() {
        return getLoggedUser().getFriends();
    }

    @Transactional
    public Page<User> getFriendsPaged(Pageable page) {
        int limit = (int) page.getOffset() + page.getPageSize();
        limit = limit > getFriends().size() ? getFriends().size() : limit;
        return new PageImpl<>(getLoggedUser().getFriends().subList((int) page.getOffset(), limit), page, getLoggedUser().getFriends().size());
    }

    @Transactional
    public void addFriend(int friendId) {
        User user = getLoggedUser();
        User friend = getUserById(friendId);
        if(!getLoggedUser().getFriends().contains(friend)){
            user.getFriends().add(friend);
            friend.getFriends().add(user);
        }
        saveUser(friend);
        saveUser(user);
    }

    @Transactional
    public void removeFriend(int friendId) {
        User user = getLoggedUser();
        User friend = getUserById(friendId);
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            saveUser(user);
            saveUser(friend);
        }
    }

    @Transactional
    public void addUserToGame(User user, Game game, String password) throws UserInAGameException, IncorrectPasswordException {
        if (user.getGame() != null && !user.getGame().equals(game))
            throw new UserInAGameException();
        if (!(Objects.equals(game.getPassword(), password) || game.getAccessibility() == Accessibility.PUBLIC))
            throw new IncorrectPasswordException();
        if (user.getGame() == null) {
            user.setGame(game);
            saveUser(user);
        }
    }


    public void removeUserFromGame(User user) {
        user.setGame(null);
        saveUser(user);
    }
}
