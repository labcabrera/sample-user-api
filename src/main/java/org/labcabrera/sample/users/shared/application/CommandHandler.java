package org.labcabrera.sample.users.shared.application;

public interface CommandHandler<C, R> {

    R handle(C command);

}
