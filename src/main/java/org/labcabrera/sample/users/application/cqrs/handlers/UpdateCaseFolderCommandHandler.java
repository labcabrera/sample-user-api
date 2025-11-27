package org.labcabrera.sample.users.application.cqrs.handlers;

import org.labcabrera.sample.users.application.cqrs.commands.UpdateUserCommand;
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
public class UpdateCaseFolderCommandHandler implements CommandHandler<UpdateUserCommand, User> {

    private final UserRepository userRepository;
    private final SecurityPort securityPort;

    public User handle(UpdateUserCommand command) {
        var user = securityPort.requireCurrentUser();
        log.info("Update user << {} (user: {})", command.userId(), user.username());
        User existing = userRepository.findById(command.userId())
            .orElseThrow(() -> new NotFoundException("user.msg.not-found", command.userId(), User.class));
        merge(existing, command);
        return userRepository.update(existing.getId(), existing);
    }

    private void merge(User existing, UpdateUserCommand command) {
        if (command.name() != null && !command.name().toUpperCase().equals(existing.getName())) {
            existing.setName(command.name());
        }
    }

}
