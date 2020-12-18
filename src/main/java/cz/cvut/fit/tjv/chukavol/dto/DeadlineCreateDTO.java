package cz.cvut.fit.tjv.chukavol.dto;

import java.util.List;

public class DeadlineCreateDTO {
    private String taskDescription;
    private String deadlineDate;
    private int maxPoints;
    private List<Integer> studentsId;
    private int subjectId;

    public DeadlineCreateDTO(String taskDescription,
                       String deadlineDate,
                       int maxPoints, List<Integer> studentsId, int subjectId) {
        this.taskDescription = taskDescription;
        this.deadlineDate = deadlineDate;
        this.maxPoints = maxPoints;
        this.studentsId = studentsId;
        this.subjectId = subjectId;
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
