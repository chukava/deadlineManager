package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SubjectContoller {
    private final SubjectService subjectService;

    public SubjectContoller(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subject/{id}")
    SubjectDTO byId(@PathVariable int id) {
        return subjectService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/subject/all")
    List<SubjectDTO> all() {
        return subjectService.findAll();
    }

    @PostMapping("/subject")
    SubjectDTO save(@RequestBody SubjectCreateDTO subject) {
        return subjectService.create(subject);
    }

    @PutMapping("/subject/{id}")
    SubjectDTO save(@PathVariable int id, @RequestBody SubjectCreateDTO subject) throws Exception {
        return subjectService.update(id,subject);
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
        subjectService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
