package org.labcabrera.sample.users.application.cqrs.handlers;

import org.labcabrera.sample.users.application.cqrs.commands.DeleteUserCommand;
import org.labcabrera.sample.users.application.ports.UserRepository;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.shared.application.CommandHandler;
import org.labcabrera.sample.users.shared.application.SecurityPort;
import org.labcabrera.sample.users.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand, Void> {

    private final UserRepository userRepository;
    private final SecurityPort securityPort;

    @Override
    public Void handle(DeleteUserCommand command) {
        var authenticatedUser = securityPort.requireCurrentUser();
        log.debug("Deleting case folder {} (user: {})", command.userId(), authenticatedUser.username());
        userRepository.findById(command.userId())
            .orElseThrow(() -> new NotFoundException("user.msg.not-found", command.userId(), User.class));
        userRepository.deleteById(command.userId());
        return null;
    }

}
