package org.labcabrera.sample.users.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class User {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static User create(String name, String createdBy) {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .createdBy(createdBy)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }

}