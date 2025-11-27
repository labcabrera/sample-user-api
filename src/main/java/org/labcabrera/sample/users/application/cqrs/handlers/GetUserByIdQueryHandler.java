package org.labcabrera.sample.users.application.cqrs.handlers;

import org.labcabrera.sample.users.application.cqrs.queries.GetUserByIdQuery;
import org.labcabrera.sample.users.application.ports.UserRepository;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.shared.application.QueryHandler;
import org.labcabrera.sample.users.shared.application.SecurityPort;
import org.labcabrera.sample.users.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, User> {

    private final UserRepository userRepository;
    private final SecurityPort securityPort;

    public User handle(GetUserByIdQuery query) {
        var authenticatedUser = securityPort.requireCurrentUser();
        log.debug("Getting case folder {} (user: {})", query.caseFolderId(), authenticatedUser.username());
        return userRepository
            .findById(query.caseFolderId())
            .orElseThrow(() -> new NotFoundException("case-folder.msg.not-found", query.caseFolderId(), User.class));
    }

}