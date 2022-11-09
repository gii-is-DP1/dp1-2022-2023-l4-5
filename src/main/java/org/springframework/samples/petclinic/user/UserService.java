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
package org.springframework.samples.petclinic.user;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.player.Tier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
    public User getById(int id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> getByAuthority(String authority) {
        return userRepository.findByAuthority(authority);
    }

    @Transactional(readOnly = true)
    public List<User> getByTier(Tier tier) {
        return userRepository.findByTier(tier);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
