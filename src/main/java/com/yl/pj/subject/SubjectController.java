package com.yl.pj.subject;

import com.yl.pj.subject.dto.SubjectCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public Subject save(@RequestBody SubjectCreateRequest request) {
        return subjectService.save(request.getName());
    }
}
