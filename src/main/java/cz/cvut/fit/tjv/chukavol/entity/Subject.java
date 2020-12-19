package cz.cvut.fit.tjv.chukavol.entity;
import com.sun.istack.NotNull;

import javax.persistence.*;


@Entity
@SequenceGenerator(name="subject_id_seq", initialValue=50, allocationSize = 1)
public class Subject {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="subject_id_seq")
    private int subjectId;

    @NotNull
    @Column(unique = true)
    private  String subjectCode;

    @NotNull
    private int numberOfCredits;

    public Subject() {
    }

    public Subject(String subjectCode, int numberOfCredits) {
        this.subjectCode = subjectCode;
        this.numberOfCredits = numberOfCredits;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

}
