package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.service.SubjectService;
import cz.cvut.fit.tjv.chukavol.service.exception.ExistingEntityException;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.data.domain.PageRequest;
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
    public List<SubjectDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return subjectService.findAll(PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubjectDTO> create(@RequestBody SubjectCreateDTO subject) {
        try {
            SubjectCreateDTO newSubject = new SubjectCreateDTO(subject.getSubjectCode(),subject.getNumberOfCredits());
            SubjectDTO created = subjectService.create(newSubject);
            return ResponseEntity
                    .created(Link.of("http://localhost:8080/orders/" + created.getSubjectId()).toUri())
                    .body(created);
        }
        catch (ExistingEntityException a){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody SubjectCreateDTO subject){
        try {
            subjectService.update(id, subject);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        catch (ExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try{
            subjectService.deleteById(id);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
