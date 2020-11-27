package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;

import java.util.List;

public class TeacherCreateDTO {
    private String nameAndSurname;
    private String email;
    private List<Deadline> deadlines;

    public TeacherCreateDTO(String nameAndSurname,
                      String email,
                      List<Deadline> deadlines) {
        this.nameAndSurname = nameAndSurname;
        this.email = email;
        this.deadlines = deadlines;
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
