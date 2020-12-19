package cz.cvut.fit.tjv.chukavol.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name="deadline_id_seq", initialValue=50)
public class Deadline {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="deadline_id_seq")
    private int deadlineId;

    @NotNull
    private String taskDescription;

    @NotNull
    private String deadlineDate;

    @NotNull
    private int maxPoints;

    @NotNull
    @ManyToMany
    @JoinTable(name = "deadline_student", // name of associative table - table created as decomposed M:N relation
              joinColumns = {@JoinColumn(name = "deadline_id")}, // column name referring to this entity - will become both primary and foreign key
    inverseJoinColumns = {@JoinColumn(name = "student_id")} // column name referring to the other entity - will become both primary and foreign key
    )
    private List<Student> students;

    @NotNull
    @ManyToOne
    private Subject subject;


    public Deadline() {
    }

    public Deadline(String taskDescription,
                    String deadlineDate,
                    int maxPoints,
                    List<Student> students,
                    Subject subject) {
        this.taskDescription = taskDescription;
        this.deadlineDate = deadlineDate;
        this.maxPoints = maxPoints;
        this.students = students;
        this.subject = subject;
    }

    public int getDeadlineId() {
        return deadlineId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
