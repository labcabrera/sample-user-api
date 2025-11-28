package org.labcabrera.sample.users.interfaces.http.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(

    @NotBlank String name

) {
}
