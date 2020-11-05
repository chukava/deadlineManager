package cz.cvut.fit.tjv.chukavol;

public class Deadline {

    private final String taskDescription;
    private final String deadlineDate;
    private final int maxPoints;
    private final boolean isDone;

    public Deadline(long l, String taskDescription, String deadlineDate, int maxPoints, boolean isDone) {
        this.taskDescription = taskDescription; // kratky popis zadani k vyplneni
        this.deadlineDate = deadlineDate; // datum odevzdani
        this.maxPoints = maxPoints; // maximalni pocet bodu za vyplnene zadani
        this.isDone = isDone;
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

    public boolean getIsDone() {
        return isDone;
    }
}
