package org.labcabrera.sample.users.interfaces.http;

import org.labcabrera.sample.users.application.cqrs.commands.CreateUserCommand;
import org.labcabrera.sample.users.application.cqrs.commands.DeleteUserCommand;
import org.labcabrera.sample.users.application.cqrs.commands.UpdateUserCommand;
import org.labcabrera.sample.users.application.cqrs.queries.GetUserByIdQuery;
import org.labcabrera.sample.users.application.cqrs.queries.GetUsersByRsqlQuery;
import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.interfaces.http.dto.CreateUserRequest;
import org.labcabrera.sample.users.interfaces.http.dto.UpdateUserRequest;
import org.labcabrera.sample.users.interfaces.http.dto.UserDto;
import org.labcabrera.sample.users.interfaces.http.dto.UserDtoPageResponse;
import org.labcabrera.sample.users.interfaces.http.mappers.UserDtoMapper;
import org.labcabrera.sample.users.shared.application.CommandBus;
import org.labcabrera.sample.users.shared.application.QueryBus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserControllerDefinition {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final UserDtoMapper mapper;

    @Override
    public ResponseEntity<UserDto> getById(String userId) {
        var query = new GetUserByIdQuery(userId);
        User user = queryBus.dispatch(query);
        var caseFolderDto = mapper.toDto(user);
        return ResponseEntity.ok(caseFolderDto);
    }

    @Override
    public ResponseEntity<UserDtoPageResponse> getByRsql(String rsql, Pageable pageable) {
        var query = new GetUsersByRsqlQuery(rsql, pageable);
        Page<User> resultPage = queryBus.dispatch(query);
        var pageDto = resultPage.map(mapper::toDto);
        return ResponseEntity.ok(new UserDtoPageResponse(pageDto));
    }

    @Override
    public ResponseEntity<UserDto> create(@Validated CreateUserRequest request) {
        var command = new CreateUserCommand(
            request.name());
        User caseFolder = commandBus.dispatch(command);
        var caseFolderDto = mapper.toDto(caseFolder);
        return ResponseEntity.status(201).body(caseFolderDto);
    }

    @Override
    public ResponseEntity<UserDto> update(String userId, UpdateUserRequest request) {
        var command = new UpdateUserCommand(
            userId,
            request.name());
        User caseFolder = commandBus.dispatch(command);
        var caseFolderDto = mapper.toDto(caseFolder);
        return ResponseEntity.ok(caseFolderDto);
    }

    @Override
    public ResponseEntity<Void> delete(String caseFolderId) {
        var command = new DeleteUserCommand(caseFolderId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
