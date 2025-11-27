package org.labcabrera.sample.users.shared.interfaces.http;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "API error detail")
public record ApiErrorDetail(

    @Schema(description = "Error key", requiredMode = RequiredMode.REQUIRED, example = "name") String key,

    @Schema(description = "Error message", requiredMode = RequiredMode.REQUIRED, example = "Field is required") String message) {
}