package cz.cvut.fit.tjv.chukavol.dto;


public class SubjectCreateDTO {
    private String subjectCode;
    private int numberOfCredits;

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
