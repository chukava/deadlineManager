package cz.cvut.fit.tjv.chukavol.controllers;


import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentContoller {
    private final StudentService studentService;

    public StudentContoller(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/student/{id}")
    StudentDTO byId(@PathVariable int id) {
        System.out.println(id);
        return studentService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/student/all")
    List<StudentDTO> all(){
        return studentService.findAll();
    }

    @GetMapping(value = "/student/all/{subjectId}")
    List<StudentDTO> allBySubjectId(@PathVariable int subjectId){
        return studentService.findAllStudentsBySubjectId(subjectId);
    }

    @PostMapping("/student")
    StudentDTO save(@RequestBody StudentCreateDTO student){
        return studentService.create(student);
    }

    @PutMapping("/student/{id}")
    StudentDTO save(@PathVariable int id, @RequestBody StudentCreateDTO student) throws Exception {
        return studentService.update(id,student);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
        studentService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
