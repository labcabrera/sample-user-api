package org.labcabrera.sample.users.application.cqrs.commands;

import jakarta.validation.constraints.NotNull;

public record CreateUserCommand(

    @NotNull String username

) {
}
