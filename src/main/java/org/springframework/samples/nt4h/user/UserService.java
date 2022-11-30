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
    public List<User> getUserByAuthority(String authority) {
        return userRepository.findByAuthority(authority);
    }

    @Transactional(readOnly = true)
    public List<User> getUserByTier(Tier tier) {
        return userRepository.findByTier(tier);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
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

    @Transactional(readOnly = true)
    public int getIdFromLoggedUser() {
        return getLoggedUser().getId();
    }

    @Transactional
    public void addFriend(int friendId) {
        User user = getLoggedUser();
        user.addFriend(getUserById(friendId));
        saveUser(user);
    }

    @Transactional
    public void removeFriend(int friendId) {
        User user = getLoggedUser();
        User friend = getUserById(friendId);
        if (user.getFriends().contains(friend)) {
            user.removeFriend(friend);
            saveUser(user);
        }
    }
}
