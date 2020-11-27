package cz.cvut.fit.tjv.chukavol.controllers;


import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class StudentContoller {
    private final StudentService studentService;

    public StudentContoller(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student/{id}")
    StudentDTO byId(@PathVariable int id) {
        System.out.println("hello2");
        return studentService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/student")
    StudentDTO save(@RequestBody StudentCreateDTO student){
        System.out.println("helololo2");
        return studentService.create(student);
    }

}
