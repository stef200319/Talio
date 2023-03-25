package commons;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class BoardTag extends Tag {


    @ManyToMany(mappedBy = "boardTags")
    private Set<Board> boards;

    /**
     * Constructor for object mappers
     */
    public BoardTag() {

    }

    /**
     * Constructor for BoardTag
     * @param title title of the boardTag
     * @param color color of the boardTag
     */
    public BoardTag(String title, String color) {
        super(title, color);
    }

    /**
     * getter for the boards a boardTag belongs to
     * @return set of boards
     */
    public Set<Board> getBoards() {
        return boards;
    }

    /**
     * Checks whether other object is equal to this
     * @param o object to compare with
     * @return boolean whether objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardTag)) return false;
        if (!super.equals(o)) return false;
        BoardTag boardTag = (BoardTag) o;
        return Objects.equals(boards, boardTag.boards);
    }

    /**
     * Hash method
     * @return hash of this
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), boards);
    }

    /**
     * To string method
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "BoardTag{" +
                "boards=" + boards +
                '}';
    }
}
