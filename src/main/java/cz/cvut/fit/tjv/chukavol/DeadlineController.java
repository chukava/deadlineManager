package cz.cvut.fit.tjv.chukavol;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DeadlineController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/deadline")
    public Deadline deadline(
            @RequestParam(value="task_description", defaultValue ="Prvni kontrolni bod semestralky") String task_description,
            @RequestParam(value="deadline_date", defaultValue ="06.10.2020") String deadline_date,
            @RequestParam(value="max_points", defaultValue ="100")int  max_points,
            @RequestParam(value="is_done", defaultValue ="1")boolean is_done
    ) {
        return new Deadline(counter.incrementAndGet(), task_description, deadline_date, max_points,is_done);
    }
}


