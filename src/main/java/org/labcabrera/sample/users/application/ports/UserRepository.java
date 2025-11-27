package org.labcabrera.sample.users.application.ports;

import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String userId);

    Page<User> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    User save(User entity);

    User update(String userId, User entity);

    void deleteById(String userId);

}