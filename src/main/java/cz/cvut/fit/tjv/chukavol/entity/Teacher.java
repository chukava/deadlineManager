package cz.cvut.fit.tjv.chukavol.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Teacher {

    @Id
    @GeneratedValue
    private int teacherId;

    @NotNull
    private String nameAndSurname;

    @NotNull
    private String email;

    @OneToMany(mappedBy = "teacher")
    private List<Deadline> deadlines;

    public Teacher() {
    }

    public Teacher(String nameAndSurname, String email, List<Deadline> deadlines) {
        this.nameAndSurname = nameAndSurname;
        this.email = email;
        this.deadlines = deadlines;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }
}

