package cz.cvut.fit.tjv.chukavol.controllers;


import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SubjectContoller {
    private final SubjectService subjectService;

    public SubjectContoller(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subject/{id}")
    SubjectDTO byId(@PathVariable int id) {
        System.out.println("hello");
        return subjectService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/subject")
    SubjectDTO save(@RequestBody SubjectCreateDTO subject){
        System.out.println("helololo");
        return subjectService.create(subject);
    }

}
