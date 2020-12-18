package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.repository.StudentRepository;
import cz.cvut.fit.tjv.chukavol.service.exception.ExistingEntityException;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
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

    public List<StudentDTO> findAll(Pageable pageable) {
        return studentRepository
                .findAll(pageable)
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

    public StudentDTO findByIdAsDTO(int id) throws NonExistingEntityException {
        Optional<StudentDTO> studentDTO = toDTO(findById(id));
        if(studentDTO.isEmpty()){
            throw new NonExistingEntityException();
        }
        return studentDTO.get();
    }

    public List<StudentDTO> findAllStudentsBySubjectId(int subjectId){
        return studentRepository.findAllBySubjectId(subjectId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentDTO create(StudentCreateDTO studentCreateDTO) throws ExistingEntityException {
        Optional<Student> studentOptional = studentRepository.existsByStudentUsername(studentCreateDTO.getStudentUsername());
        if(!studentOptional.isEmpty()){
            throw new ExistingEntityException();
        }
        return toDTO(studentRepository.save(
                new Student(
                        studentCreateDTO.getStudentUsername(),
                        studentCreateDTO.getPassword(),
                        studentCreateDTO.getGrade())));
    }

    @Transactional
    public StudentDTO update(int id, StudentCreateDTO studentCreateDTO) throws NonExistingEntityException, ExistingEntityException {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty()) {
            throw new NonExistingEntityException();
        }
        Optional<Student> studentOptional2 = studentRepository.existsByStudentUsername(studentCreateDTO.getStudentUsername());
        if(!studentOptional2.isEmpty() &&
                studentOptional2.get().getStudentUsername() != studentOptional.get().getStudentUsername()){
            throw new ExistingEntityException();
        }

        Student student = studentOptional.get();
        student.setStudentUsername(studentCreateDTO.getStudentUsername());
        student.setPassword(studentCreateDTO.getPassword());
        student.setGrade(studentCreateDTO.getGrade());
        return toDTO(student);
    }

    public void deleteById(int id) throws NonExistingEntityException {
        if (!studentRepository.existsById(id)) {
            throw new NonExistingEntityException();
        }
        studentRepository.deleteById(id);
    }


    private StudentDTO toDTO(Student student){
        return new StudentDTO(
                student.getStudentId(),
                student.getStudentUsername(),
                student.getPassword(),
                student.getGrade());
    }

    private Optional<StudentDTO> toDTO(Optional<Student> optionalStudent) {
        if(optionalStudent.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalStudent.get()));
    }

}
