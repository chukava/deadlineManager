package cz.cvut.fit.tjv.chukavol.repository;

import cz.cvut.fit.tjv.chukavol.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
