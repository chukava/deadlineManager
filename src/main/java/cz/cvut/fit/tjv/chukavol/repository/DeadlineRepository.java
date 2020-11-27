package cz.cvut.fit.tjv.chukavol.repository;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline,Integer> {
}
