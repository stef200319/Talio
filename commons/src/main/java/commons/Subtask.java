package commons;

import javax.persistence.*;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private boolean done;

    /**
     * Constructor to create a subTask object
     * @param description of the subtask of the main task
     */
    public Subtask(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Get the description of the subtask
     * @return the description of the subtask as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the subtask
     * @param description of the subtask which belongs to the main task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the status of the subtask
     * @return the status of the subtask as a boolean. returns true iff the subtask is completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Set the status of the subtask
     * @param done boolean value representing whether the subtaskis done or not.
     */
    public void setDone(boolean done) {
        this.done = done;
    }
}
