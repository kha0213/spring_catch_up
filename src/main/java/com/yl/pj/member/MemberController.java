package com.yl.pj.member;

import com.yl.pj.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public List<Member> getMembers() {
        return memberService.findAll();
    }

    @PostMapping
    public Member createMember(@RequestBody MemberCreateRequest request) {
        return memberService.save(request.name());
    }
}
