package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;


    public SubjectService(SubjectRepository subjectRepository){//, DeadlineRepository deadlineRepository) {
        this.subjectRepository = subjectRepository;

    }

    public List<SubjectDTO> findAll() {
        return subjectRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Subject> findByIds(List<Integer> ids){
        return subjectRepository.findAllById((ids));
    }

    public Optional<Subject> findById(int id){
        return subjectRepository.findById(id);
    }

    public Optional<SubjectDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }


    public SubjectDTO create(SubjectCreateDTO subjectCreateDTO){
        return toDTO(subjectRepository.save(
                new Subject(subjectCreateDTO.getSubjectCode(),
                        subjectCreateDTO.getNumberOfCredits())));
    }

    @Transactional
    public SubjectDTO update(int id, SubjectCreateDTO subjectCreateDTO) throws Exception {
        Optional<Subject> optionalSubject = subjectRepository.findById((id));
        if (optionalSubject.isEmpty()) {
            throw new Exception("no such subject");
        }
        Subject subject = optionalSubject.get();
        subject.setSubjectCode(subjectCreateDTO.getSubjectCode());
        subject.setNumberOfCredits(subjectCreateDTO.getNumberOfCredits());
        subject.setDeadlines(subjectCreateDTO.getDeadlines());
        return toDTO(subject);
    }

    private SubjectDTO toDTO(Subject subject){
        return new SubjectDTO(subject.getSubjectId(),
                subject.getSubjectCode(),
                subject.getNumberOfCredits(),
                subject.getDeadlines());
    }

    private Optional<SubjectDTO> toDTO(Optional<Subject> optionalSubject) {
        if(optionalSubject.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalSubject.get()));
    }
}
