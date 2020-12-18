package cz.cvut.fit.tjv.chukavol.dto;


public class SubjectDTO {
    private int subjectId;
    private  String subjectCode;
    private int numberOfCredits;

    public SubjectDTO(int subjectId,
                      String subjectCode,
                      int numberOfCredits) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.numberOfCredits = numberOfCredits;
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

}
