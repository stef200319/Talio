package commons;

import javax.persistence.*;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private boolean done;

    /**
     * Constructor to create a subTask object
     * @param title Title of the subtask of the main task
     */
    public Subtask(String title) {
        this.title = title;
        this.done = false;
    }

    /**
     * Get the id of the subtask
     * @return the id of the subtask as a long
     */
    public long getId(){
        return id;
    }

    /**
     * Get the title of the subtask
     * @return the title of the subtask as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the subtask
     * @param title of the subtask which belongs to the main task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the status of the subtask
     * @return the status of the subtask as a boolean. returns true iff the subtask is completed.
     */
    public boolean getDone() {
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
