package cz.cvut.fit.tjv.chukavol.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue
    private int subjectId;

    @NotNull
    private  String subjectCode;

    @NotNull
    private int numberOfCredits;

    @OneToMany(mappedBy = "subject")
    private List<Deadline> deadlines;


    public Subject() {
    }

    public Subject(String subjectCode,
                   int numberOfCredits,
                   List<Deadline> deadlines) {
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

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }
}
