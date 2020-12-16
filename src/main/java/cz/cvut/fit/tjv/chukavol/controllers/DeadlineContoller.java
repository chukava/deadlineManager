package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.DeadlineCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.DeadlineDTO;
import cz.cvut.fit.tjv.chukavol.service.DeadlineService;
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
    DeadlineDTO byId(@PathVariable int id) {
        return deadlineService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    List<DeadlineDTO> all() {
        return deadlineService.findAll();
    }

    @PostMapping("/create")
    DeadlineDTO create(@RequestBody DeadlineCreateDTO deadline) throws Exception {
        return deadlineService.create(deadline);
    }

    @PutMapping("/update/{id}")
    DeadlineDTO save(@PathVariable int id, @RequestBody DeadlineCreateDTO deadline) throws Exception {
        return deadlineService.update(id,deadline);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
        deadlineService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
