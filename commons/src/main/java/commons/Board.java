package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "owned_tags_board",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "boardtag_id")
    )
    private Set<BoardTag> boardTags;


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
     * getter for the boardTags
     * @return all the boardTags
     */
    public List<BoardTag> getBoardTags() {
        return new ArrayList<>(boardTags);
    }

    /**
     * setter fot the boardTags
     * @param boardTags
     */
    public void setBoardTags(Set<BoardTag> boardTags) {
        this.boardTags = boardTags;
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
     * add BoardTag to board
     * @param boardTag boardTag to add
     */
    public void addBoardTag(BoardTag boardTag) {
        if (boardTags.contains(boardTag) || boardTag == null) return;
        boardTags.add(boardTag);
    }

    /**
     * Delete the boardTag
     * @param boardTag boardTag to delete
     * @return the deleted boardTag
     */
    public BoardTag deleteBoardTag(BoardTag boardTag) {
        if (!boardTags.contains(boardTag) || boardTag == null) return null;
        boardTags.remove(boardTag);
        return boardTag;
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
