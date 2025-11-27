package org.labcabrera.sample.users.shared.application;

public interface QueryBus {

    <R> R dispatch(Object query);

}
