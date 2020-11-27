package cz.cvut.fit.tjv.chukavol.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;


@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;

    @NotNull
    private String studentUsername;

    @NotNull
    private String password;

    private int grade;

    @ManyToMany(mappedBy = "students")
    private List<Deadline> deadlines;

    public Student() {
    }

    public Student(String studentUsername, String password, int grade, List<Deadline> deadlines) {
        this.studentUsername = studentUsername;
        this.password = password;
        this.grade = grade;
        this.deadlines = deadlines;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }
}
