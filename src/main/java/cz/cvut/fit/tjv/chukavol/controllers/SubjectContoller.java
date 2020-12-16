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
@RequestMapping("/subject")
public class SubjectContoller {
    private final SubjectService subjectService;

    public SubjectContoller(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/{id}")
    SubjectDTO byId(@PathVariable int id) {
        return subjectService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    List<SubjectDTO> all() {
        return subjectService.findAll();
    }

    @PostMapping("/create")
    SubjectDTO create(@RequestBody SubjectCreateDTO subject) {
        return subjectService.create(subject);
    }

    @PutMapping("/update/{id}")
    SubjectDTO update(@PathVariable int id, @RequestBody SubjectCreateDTO subject) throws Exception {
        return subjectService.update(id,subject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
        subjectService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
