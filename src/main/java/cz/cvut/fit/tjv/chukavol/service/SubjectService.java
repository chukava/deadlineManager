package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.SubjectRepository;
import cz.cvut.fit.tjv.chukavol.service.exception.ExistingEntityException;
import cz.cvut.fit.tjv.chukavol.service.exception.NonExistingEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;


    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> findAll(Pageable pageable) {
        return subjectRepository
                .findAll(pageable)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Subject> findById(int id){
        return subjectRepository.findById(id);
    }

    public Optional<SubjectDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }


    public SubjectDTO create(SubjectCreateDTO subjectCreateDTO) throws ExistingEntityException {
        Optional<Subject> subject = subjectRepository.existsBySubjectCode(subjectCreateDTO.getSubjectCode());
        if(!subject.isEmpty()){
            throw new ExistingEntityException();
        }
        return toDTO(subjectRepository.save(
                new Subject(subjectCreateDTO.getSubjectCode(),
                        subjectCreateDTO.getNumberOfCredits())));
    }

    @Transactional
    public SubjectDTO update(int id, SubjectCreateDTO subjectCreateDTO) throws NonExistingEntityException, ExistingEntityException {
        Optional<Subject> optionalSubject = subjectRepository.findById((id));
        if (optionalSubject.isEmpty()) {
            throw new NonExistingEntityException();
        }
        Optional<Subject> optionalSubject2 = subjectRepository.existsBySubjectCode(subjectCreateDTO.getSubjectCode());
        if(!optionalSubject2.isEmpty()){
            throw new ExistingEntityException();
        }
        Subject subject = optionalSubject.get();
        subject.setSubjectCode(subjectCreateDTO.getSubjectCode());
        subject.setNumberOfCredits(subjectCreateDTO.getNumberOfCredits());
        return toDTO(subject);
    }

    public void deleteById(int id) throws NonExistingEntityException {
        if (!subjectRepository.existsById(id)) {
            throw new NonExistingEntityException();
        }
        subjectRepository.deleteById(id);
    }


    private SubjectDTO toDTO(Subject subject){
        return new SubjectDTO(subject.getSubjectId(),
                subject.getSubjectCode(),
                subject.getNumberOfCredits());
    }

    private Optional<SubjectDTO> toDTO(Optional<Subject> optionalSubject) {
        if(optionalSubject.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalSubject.get()));
    }
}
