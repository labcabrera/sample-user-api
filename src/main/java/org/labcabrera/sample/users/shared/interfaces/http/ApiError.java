package org.labcabrera.sample.users.shared.interfaces.http;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "API error information")
public record ApiError(

    @Schema(description = "Error code", requiredMode = RequiredMode.REQUIRED, example = "case-folder.msg.err.not-found") String code,

    @Schema(description = "Detailed error message", requiredMode = RequiredMode.REQUIRED, example = "Case folder not found") String message,

    @Schema(description = "Timestamp of the error", requiredMode = RequiredMode.REQUIRED) LocalDateTime timestamp,

    @Schema(description = "Validation errors, if any", requiredMode = RequiredMode.NOT_REQUIRED) List<ApiErrorDetail> details) {
}