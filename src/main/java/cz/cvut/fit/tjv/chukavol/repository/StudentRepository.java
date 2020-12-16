package cz.cvut.fit.tjv.chukavol.repository;

import cz.cvut.fit.tjv.chukavol.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query(value = "SELECT * FROM student WHERE student_id IN (" +
            "SELECT student_id FROM deadline_student" +
            " JOIN deadline d on d.deadline_id = deadline_student.deadline_id " +
            "AND subject_subject_id = ?1)"
            , nativeQuery = true)
    List<Student> findAllBySubjectId(int subjectId);
}
