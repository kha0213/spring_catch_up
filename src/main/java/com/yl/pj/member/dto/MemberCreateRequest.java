package com.yl.pj.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
    @NotBlank(message = "이름은 필수입니다")
    String name
) {
    // Compact Constructor로 추가 검증
    public MemberCreateRequest {
        if (name != null) {
            name = name.trim();
        }
    }
}
