package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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



    private String centerColour;

    private String sideColour;

    private String fontType;
    private Boolean fontStyleBold;
    private Boolean fontStyleItalic;
    private String fontColour;


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
        this.boardTags = new HashSet<>();
        this.centerColour = "#FFFFFF";
        this.sideColour = "#F5DEB3";
        this.fontType = "Segoe UI";
        this.fontStyleBold=false;
        this.fontStyleItalic=false;
        this.fontColour = "#000000";

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



    /**
     * @return the String of the center Colour of a Board
     */
    public String getCenterColour() {
        return centerColour;
    }
    /**
     * Set the center colour of a Board
     * @param colour new background colour to replace the Board's old one
     */
    public void setCenterColour(String colour) {
        this.centerColour = colour;
    }
    /**
     * @return the String of the sideColour of a Board
     */
    public String getSideColour() {
        return sideColour;
    }
    /**
     * Set the sideColour of a Board
     * @param sideColour new sideColour to replace the Board's old one
     */
    public void setSideColour(String sideColour) {
        this.sideColour = sideColour;
    }
    /**
     * @return the String of the Font-type of a Board
     */
    public String getFontType() {
        return fontType;
    }
    /**
     * Set the font-type of a Board
     * @param fontType new font type to replace the Board's old one
     */
    public void setFontType(String fontType) {
        this.fontType = fontType;
    }
    /**
     * @return whether the Board is Bold
     */
    public boolean isFontStyleBold() {
        return fontStyleBold;
    }

    /**
     * Set the Board to be Bold or not
     * @param fontStyleBold value of the Boldness of the Board
     */
    public void setFontStyleBold(boolean fontStyleBold) {
        this.fontStyleBold = fontStyleBold;
    }
    /**
     * @return whether the Board is Italic
     */
    public boolean isFontStyleItalic() {
        return fontStyleItalic;
    }


    /**
     * Set the Board to be Italic or not
     * @param fontStyleItalic value of the Italicness of the Board
     */
    public void setFontStyleItalic(boolean fontStyleItalic) {
        this.fontStyleItalic = fontStyleItalic;
    }
    /**
     * @return the String of the Font Colour of a Board
     */
    public String getFontColour() {
        return fontColour;
    }

    /**
     * Set the font colour of a Board
     * @param fontColour new font colour to replace the Board's old one
     */
    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }
}
