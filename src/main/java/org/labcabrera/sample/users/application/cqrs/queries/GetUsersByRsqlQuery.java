package org.labcabrera.sample.users.application.cqrs.queries;

import org.springframework.data.domain.Pageable;

public record GetUsersByRsqlQuery(
    String rsql,
    Pageable pageable) {
}
