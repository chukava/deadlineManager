package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.entity.Deadline;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> findAll() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Student> findAllByIds(List<Integer> ids){
        return studentRepository.findAllById(ids);
    }

    public Optional<Student> findById(int id){
        return studentRepository.findById(id);
    }

    public Optional<StudentDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }


    public StudentDTO create(StudentCreateDTO studentCreateDTO){
        return toDTO(studentRepository.save(
                new Student(
                        studentCreateDTO.getStudentUsername(),
                        studentCreateDTO.getPassword(),
                        studentCreateDTO.getGrade())));
    }

    @Transactional
    public StudentDTO update(int id, StudentCreateDTO studentCreateDTO) throws Exception {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new Exception("no such student");
        }

        Student student = optionalStudent.get();
        student.setStudentUsername(studentCreateDTO.getStudentUsername());
        student.setPassword(studentCreateDTO.getPassword());
        student.setPassword(studentCreateDTO.getPassword());
        return toDTO(student);
    }

    public void deleteById(int id) throws Exception {
        if (!studentRepository.existsById(id)) {
            throw new Exception("no such student");
        }
        studentRepository.deleteById(id);
    }


    private StudentDTO toDTO(Student student){
        if(student.getDeadlines() == null){
            return new StudentDTO(
                    student.getStudentId(),
                    student.getStudentUsername(),
                    student.getPassword(),
                    student.getGrade(),null);
        }
        return new StudentDTO(
                student.getStudentId(),
                student.getStudentUsername(),
                student.getPassword(),
                student.getGrade(),
                student.getDeadlines().stream().
                        map(Deadline::getDeadlineId)
                        .collect(Collectors.toList()));
    }

    private Optional<StudentDTO> toDTO(Optional<Student> optionalStudent) {
        if(optionalStudent.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalStudent.get()));
    }

}
