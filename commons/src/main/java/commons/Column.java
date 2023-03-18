package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private long boardId;

    private Integer position;

    /**
     * Constructor for object mappers
     */
    public Column() {
        // for object mappers
    }

    /**
     * Constructor for a list to organize cards in
     * @param title Title of the list
     * @param boardId id of the board the list belongs to
     */
    public Column(String title, long boardId) {
        this.title = title;
        this.boardId = boardId;
    }

    /**
     * @return the ID of the current object
     */
    public long getId() {
        return id;
    }

    /**
     * @return The title of the list
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the ID of the board on which the list is situated
     */
    public long getBoardId() {
        return boardId;
    }

    /**
     * @param title the new title of the list
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param id new id of the entry
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param boardId the id of the board that replaces the current board the list belongs to
     */
    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    /**
     * @return the position of the card in the list it belongs to (at the time)
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the new position of the card in the list it belongs to (at the time),
     *                 which replaces the old position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Return whether the calling object is equals to the parameter (obj)
     * @param obj Object to be compared to for equality
     * @return True if obj is equals to the calling object, false if
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * @return a hashcode of the current object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @return a string of all the properties in the method
     */
    @Override
    public String toString() {
        return "The title of this List is: " + getTitle() +
                ", and the ID of the Board this List belongs to is: " + getBoardId();
    }
}
