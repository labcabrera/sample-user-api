package org.labcabrera.sample.users.shared.application;

public interface QueryHandler<Q, R> {

    R handle(Q query);

}
