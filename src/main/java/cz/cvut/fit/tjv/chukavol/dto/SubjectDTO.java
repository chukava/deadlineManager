package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;

import java.util.List;

public class SubjectDTO {
    private int subjectId;
    private  String subjectCode;
    private int numberOfCredits;
    private List<Deadline> deadlines;

    public SubjectDTO(int subjectId,
                      String subjectCode,
                      int numberOfCredits,
                      List<Deadline> deadlines) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.numberOfCredits = numberOfCredits;
        this.deadlines = deadlines;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }
}
