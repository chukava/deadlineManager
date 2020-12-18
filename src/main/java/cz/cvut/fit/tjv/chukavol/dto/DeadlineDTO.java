package cz.cvut.fit.tjv.chukavol.dto;

import java.util.List;

public class DeadlineDTO {
    private int deadlineId;
    private String taskDescription;
    private String deadlineDate;
    private int maxPoints;
    private List<Integer> studentsId;
    private int subjectId;

    public DeadlineDTO(int deadlineId,
                       String taskDescription,
                       String deadlineDate,
                       int maxPoints,
                       List<Integer> studentsId,
                       int subjectId) {
        this.deadlineId = deadlineId;
        this.taskDescription = taskDescription;
        this.deadlineDate = deadlineDate;
        this.maxPoints = maxPoints;
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

    public List<Integer> getStudentsId() {
        return studentsId;
    }

    public int getSubjectId() {
        return subjectId;
    }
}
