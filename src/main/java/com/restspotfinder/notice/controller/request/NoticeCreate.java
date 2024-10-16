package com.restspotfinder.notice.controller.request;

import jakarta.validation.constraints.NotNull;

public record NoticeCreate(
        @NotNull String title,
        @NotNull String content
) {
}