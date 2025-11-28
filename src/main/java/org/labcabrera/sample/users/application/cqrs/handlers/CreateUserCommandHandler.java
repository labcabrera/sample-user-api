package org.labcabrera.sample.users.application.cqrs.handlers;

import org.labcabrera.sample.users.application.cqrs.commands.CreateUserCommand;
import org.labcabrera.sample.users.application.ports.UserRepository;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.shared.application.CommandHandler;
import org.labcabrera.sample.users.shared.application.SecurityPort;
import org.labcabrera.sample.users.shared.domain.exceptions.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, User> {

    private final UserRepository userRepository;
    private final SecurityPort securityPort;
    private final Validator validator;

    public User handle(@Validated CreateUserCommand command) {
        var authenticatedUser = securityPort.requireCurrentUser();
        log.info("Create case folder << {} (user: {})", command.username(), authenticatedUser.username());
        var user = User.create(command.username(), authenticatedUser.username());
        validateCaseFolder(user);
        return userRepository.save(user);
    }

    private void validateCaseFolder(User caseFolder) {
        var violations = validator.validate(caseFolder);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("case-folder.msg.err.validation-error", violations);
        }
    }

}