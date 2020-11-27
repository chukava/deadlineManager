package cz.cvut.fit.tjv.chukavol.dto;

import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;

import java.util.List;

public class DeadlineDTO {
    private int deadlineId;
    private String taskDescription;
    private String deadlineDate;
    private int maxPoints;
    private Boolean isDone;
    private List<Integer> studentsId;
    private int subjectId;

    public DeadlineDTO(int deadlineId,
                       String taskDescription,
                       String deadlineDate,
                       int maxPoints,
                       Boolean isDone,
                       List<Integer> studentsId,
                       int subjectId) {
        this.deadlineId = deadlineId;
        this.taskDescription = taskDescription;
        this.deadlineDate = deadlineDate;
        this.maxPoints = maxPoints;
        this.isDone = isDone;
        this.studentsId = studentsId;
        this.subjectId = subjectId;
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

    public List<Integer> getStudentsId() {
        return studentsId;
    }

    public int getSubjectId() {
        return subjectId;
    }
}
