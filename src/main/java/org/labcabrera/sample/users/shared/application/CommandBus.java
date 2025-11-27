package org.labcabrera.sample.users.shared.application;

public interface CommandBus {

    <R> R dispatch(Object command);

}
