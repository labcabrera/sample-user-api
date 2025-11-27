package org.labcabrera.sample.users.application.cqrs.queries;

public record GetUserByIdQuery(
    String caseFolderId) {
}