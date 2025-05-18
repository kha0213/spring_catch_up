package com.yl.pj.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(String name) {
        return memberRepository.save(Member.create(name));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
