package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.service.StudentService;
import cz.cvut.fit.tjv.chukavol.service.exception.ExistingEntityException;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentContoller {
    private final StudentService studentService;

    public StudentContoller(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/{id}")
    public StudentDTO byId(@PathVariable int id) {
        try{
            StudentDTO studentDTO =  studentService.findByIdAsDTO(id);
            return studentDTO;
        }
        catch (NonExistingEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public List<StudentDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        return studentService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/all/{subjectId}")
    public List<StudentDTO> allBySubjectId(@PathVariable int subjectId){
        return studentService.findAllStudentsBySubjectId(subjectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentDTO> create(@RequestBody StudentCreateDTO student){
        try {
            StudentDTO created = studentService.create(student);
            return ResponseEntity
                    .created(Link.of("http://localhost:8080/student/" + created.getStudentId()).toUri())
                    .body(created);
        }
        catch (ExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody StudentCreateDTO student)  {
        try{
            studentService.update(id,student);
        }
        catch (NonExistingEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        catch (ExistingEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        try{
            studentService.deleteById(id);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
