package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.entity.Teacher;

import java.util.List;

public class DeadlineDTO {
    private int deadlineId;
    private String taskDescription;
    private String deadlineDate;
    private int maxPoints;
    private Boolean isDone;
    private List<Student> students;
    private Subject subject;
    private Teacher teacher;

    public DeadlineDTO(int deadlineId,
                       String taskDescription,
                       String deadlineDate,
                       int maxPoints,
                       Boolean isDone,
                       List<Student> students,
                       Subject subject,
                       Teacher teacher) {
        this.deadlineId = deadlineId;
        this.taskDescription = taskDescription;
        this.deadlineDate = deadlineDate;
        this.maxPoints = maxPoints;
        this.isDone = isDone;
        this.students = students;
        this.subject = subject;
        this.teacher = teacher;
    }

    public int getDeadlineId() {
        return deadlineId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
