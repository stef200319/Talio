package commons;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private boolean done;

    /**
     * Default constructor for object mappers
     */
    public Subtask() {}

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
     * Sets a new id for a subtask (for testing)
     * @param id new id of the subtask
     */
    public void setId(long id) {
        this.id = id;
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

    /**
     * equals method
     * @param o object we're comparing to
     * @return true if equal false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id && done == subtask.done &&
            Objects.equals(title, subtask.title);
    }

    /**
     * hashing method
     * @return hash of subtask
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, done);
    }

    /**
     * To string method for debugging purposes
     * @return representation of a subtask as a string
     */
    public String toString() {
        return "Subtask with id: " + id + " has title: " + title + " and status: " + done;
    }


}
