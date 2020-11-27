package cz.cvut.fit.tjv.chukavol.repository;

import cz.cvut.fit.tjv.chukavol.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
}
