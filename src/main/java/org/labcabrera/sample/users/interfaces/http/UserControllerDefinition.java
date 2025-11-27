package org.labcabrera.sample.users.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.labcabrera.sample.users.interfaces.http.dto.CreateUserRequest;
import org.labcabrera.sample.users.interfaces.http.dto.UpdateUserRequest;
import org.labcabrera.sample.users.interfaces.http.dto.UserDto;
import org.labcabrera.sample.users.interfaces.http.dto.UserDtoPageResponse;
import org.labcabrera.sample.users.shared.interfaces.http.ApiError;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/v1/users", produces = "application/json")
@Tag(name = "Users", description = "API for user management")
public interface UserControllerDefinition {

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Retrieve a specific user by its unique identifier")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<UserDto> getById(
        @Parameter(description = "Unique user identifier", required = true) @PathVariable String userId);

    @GetMapping
    @Operation(summary = "Get users by RSQL", description = "Filter users using an RSQL expression with optional pagination")
    @ApiResponse(responseCode = "200", description = "Paged users", content = @Content(schema = @Schema(implementation = UserDtoPageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid RSQL expression", content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<UserDtoPageResponse> getByRsql(
        @Parameter(description = "RSQL expression to filter users", name = "q", required = false) @RequestParam(required = false, name = "q") String rsql,
        @ParameterObject Pageable pageable);

    @PostMapping
    @Operation(summary = "Create new user", description = "Creates a new user using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<UserDto> create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User data to create", required = true, content = @Content(schema = @Schema(implementation = CreateUserRequest.class))) @RequestBody CreateUserRequest request);

    @PatchMapping("/{userId}")
    @Operation(summary = "Update user", description = "Updates an existing user using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<UserDto> update(
        @Parameter(description = "Unique user identifier", required = true) @PathVariable String userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User data to update", required = true) @RequestBody UpdateUserRequest request);

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Deletes an existing user using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<Void> delete(
        @Parameter(description = "Unique user identifier", required = true) @PathVariable String userId);

}
