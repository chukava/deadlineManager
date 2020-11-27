package cz.cvut.fit.tjv.chukavol.controllers;

import cz.cvut.fit.tjv.chukavol.dto.DeadlineCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.DeadlineDTO;
import cz.cvut.fit.tjv.chukavol.service.DeadlineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DeadlineContoller {
    private final DeadlineService deadlineService;

    public DeadlineContoller(DeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    @GetMapping("/deadline/{id}")
    DeadlineDTO byId(@PathVariable int id) {
        System.out.println("hello1");
        return deadlineService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/deadline")
    DeadlineDTO save(@RequestBody DeadlineCreateDTO deadline) throws Exception {
        System.out.println("helololo1");
        return deadlineService.create(deadline);
    }


}
