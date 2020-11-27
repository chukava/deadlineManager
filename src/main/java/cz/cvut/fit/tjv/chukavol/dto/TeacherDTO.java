package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;

import java.util.List;

public class TeacherDTO {
    private int teacherId;
    private String nameAndSurname;
    private String email;
    private List<Deadline> deadlines;

    public TeacherDTO(int teacherId,
                      String nameAndSurname,
                      String email,
                      List<Deadline> deadlines) {
        this.teacherId = teacherId;
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

    public String getEmail() {
        return email;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }
}
