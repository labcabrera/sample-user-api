package org.labcabrera.sample.users.interfaces.http.dto;

import org.labcabrera.sample.users.shared.interfaces.http.PageResponse;
import org.springframework.data.domain.Page;

public class UserDtoPageResponse extends PageResponse<UserDto> {

    public UserDtoPageResponse(Page<UserDto> page) {
        super(page);
    }

}
