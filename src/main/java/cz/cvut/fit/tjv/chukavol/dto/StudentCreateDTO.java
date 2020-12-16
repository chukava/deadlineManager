package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Deadline;

import java.util.List;

public class StudentCreateDTO {
    private String studentUsername;
    private String password;
    private int grade;

    public StudentCreateDTO(String studentUsername,
                      String password,
                      int grade) {
        this.studentUsername = studentUsername;
        this.password = password;
        this.grade = grade;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getPassword() {
        return password;
    }

    public int getGrade() {
        return grade;
    }
}
