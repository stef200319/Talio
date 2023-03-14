package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    /**
     * constructor for object mappers
     */
    public Board() {
        // for object mappers
    }

    /**
     * @param title the title of the board
     */
    public Board(String title) {
        this.title = title;
    }

    /**
     * @return the id of the board stored in the database
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id new id of the entry
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the title of the board
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title which should replace the board's current title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @param obj other object that you want to compare to this
     * @return whether the objects are the same
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
        return "This board is called: " + getTitle();
    }
}
