package org.labcabrera.sample.users.application.cqrs.handlers;

import org.labcabrera.sample.users.application.cqrs.queries.GetUsersByRsqlQuery;
import org.labcabrera.sample.users.application.ports.UserRepository;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.shared.application.QueryHandler;
import org.labcabrera.sample.users.shared.application.SecurityPort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUsersByRsqlQueryHandler implements QueryHandler<GetUsersByRsqlQuery, Page<User>> {

    private final UserRepository userRepository;
    private final SecurityPort securityPort;

    public Page<User> handle(GetUsersByRsqlQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Getting case folders by RSQL <<< {} (user: {})", query.rsql(), user.username());
        return userRepository.findByRsql(query.rsql(), query.pageable(), user);
    }
}
