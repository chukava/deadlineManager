package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.DeadlineCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.DeadlineDTO;
import cz.cvut.fit.tjv.chukavol.service.DeadlineService;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/deadline")
public class DeadlineContoller {
    private final DeadlineService deadlineService;

    public DeadlineContoller(DeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    @GetMapping("/{id}")
    public DeadlineDTO byId(@PathVariable int id) {
        return deadlineService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public List<DeadlineDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return deadlineService.findAll(PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DeadlineDTO> create(@RequestBody DeadlineCreateDTO newDeadline) {
        try{
            DeadlineDTO created = deadlineService.create(newDeadline);
            return ResponseEntity
                    .created(Link.of("http://localhost:8080/deadline/" + created.getDeadlineId()).toUri())
                    .body(created);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody DeadlineCreateDTO deadline) {
        try{
            deadlineService.update(id, deadline);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            deadlineService.deleteById(id);
        }
        catch (NonExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
