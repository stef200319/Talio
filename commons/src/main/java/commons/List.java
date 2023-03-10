package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private long boardId;

    public List() {
        // for object mappers
    }

    /**
     * Constructor for a list to organize cards in
     * @param title Title of the list
     * @param boardId Id of the board the list belongs to
     */
    public List(String title, long boardId) {
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
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
