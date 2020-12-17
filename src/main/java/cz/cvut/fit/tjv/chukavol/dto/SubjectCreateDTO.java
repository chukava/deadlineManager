package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;

import java.util.List;

public class SubjectCreateDTO {
    private String subjectCode;
    private int numberOfCredits;
    private List<Deadline> deadlines;

    public SubjectCreateDTO(String subjectCode,
                            int numberOfCredits){
        this.subjectCode = subjectCode;
        this.numberOfCredits = numberOfCredits;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }
}
