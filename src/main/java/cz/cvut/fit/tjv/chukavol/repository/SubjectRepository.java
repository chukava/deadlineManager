package cz.cvut.fit.tjv.chukavol.repository;

import cz.cvut.fit.tjv.chukavol.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query(value = "SELECT * FROM subject WHERE subject_code = ?1",nativeQuery = true)
    Optional<Subject> existsBySubjectCode(String subjectCode);
}
