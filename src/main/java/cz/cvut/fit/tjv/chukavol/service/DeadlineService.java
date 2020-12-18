package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.*;
import cz.cvut.fit.tjv.chukavol.entity.Deadline;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.DeadlineRepository;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeadlineService {
    private final DeadlineRepository deadlineRepository;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public DeadlineService(DeadlineRepository deadlineRepository, StudentService studentService, SubjectService subjectService) {
        this.deadlineRepository = deadlineRepository;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    public List<DeadlineDTO> findAll(Pageable pageable) {
        return deadlineRepository
                .findAll(pageable)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public Optional<Deadline> findById(int id){
        return deadlineRepository.findById(id);
    }

    public Optional<DeadlineDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }


    @Transactional
    public DeadlineDTO create(DeadlineCreateDTO deadlineCreateDTO) throws NonExistingEntityException {
        List<Student> student = studentService.findAllByIds(deadlineCreateDTO.getStudentsId());
        if( student.size() != deadlineCreateDTO.getStudentsId().size() || student.size() == 0) {
            throw new NonExistingEntityException();
        }
        Subject subject = subjectService.findById(deadlineCreateDTO.getSubjectId())
                .orElseThrow(NonExistingEntityException::new);

        return toDTO(deadlineRepository.save(
                new Deadline(
                        deadlineCreateDTO.getTaskDescription(),
                        deadlineCreateDTO.getDeadlineDate(),
                        deadlineCreateDTO.getMaxPoints(),
                        student,
                        subject)));
    }



    @Transactional
    public DeadlineDTO update(int id, DeadlineCreateDTO deadlineCreateDTO) throws NonExistingEntityException {
        Optional<Deadline> optionalDeadline = deadlineRepository.findById(id);
        if (optionalDeadline.isEmpty()) {
            throw new NonExistingEntityException();
        }
        List<Student> students = studentService.findAllByIds(deadlineCreateDTO.getStudentsId());
        if( students.size() != deadlineCreateDTO.getStudentsId().size() || students.size() == 0 ) {
            throw new NonExistingEntityException();
        }
        Subject subject = subjectService.findById(deadlineCreateDTO.getSubjectId())
                .orElseThrow(NonExistingEntityException::new);

        Deadline deadline = optionalDeadline.get();
        deadline.setTaskDescription(deadlineCreateDTO.getTaskDescription());
        deadline.setDeadlineDate(deadlineCreateDTO.getDeadlineDate());
        deadline.setMaxPoints(deadlineCreateDTO.getMaxPoints());
        deadline.setStudents(students);
        deadline.setSubject(subject);

        return toDTO(deadline);
    }

    public void deleteById(int id) throws NonExistingEntityException {
        if (!deadlineRepository.existsById(id)) {
            throw new NonExistingEntityException();
        }
        deadlineRepository.deleteById(id);
    }


    private DeadlineDTO toDTO(Deadline deadline){
        return new DeadlineDTO(deadline.getDeadlineId(),
                deadline.getTaskDescription(),
                deadline.getDeadlineDate(),
                deadline.getMaxPoints(),
                deadline.getStudents().stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList()),
                deadline.getSubject().getSubjectId());
    }

    private Optional<DeadlineDTO> toDTO(Optional<Deadline> optionalDeadline) {
        if(optionalDeadline.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalDeadline.get()));
    }

}
