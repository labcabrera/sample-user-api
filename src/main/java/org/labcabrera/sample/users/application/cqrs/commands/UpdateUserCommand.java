package org.labcabrera.sample.users.application.cqrs.commands;

public record UpdateUserCommand(
    String userId,
    String name) {
}
