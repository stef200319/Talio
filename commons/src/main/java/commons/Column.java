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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long boardId;

    private Integer position;



    private String bgColour;
    private String defaultBgColour = "#F2F3F4";
    private String borderColour;
    private String defaultBorderColour = "#000000";
    private String fontType;
    private String defaultFontType = "Segoe UI";
    private Boolean fontStyleBold;
    private Boolean fontStyleItalic;
    private String fontColour;
    private String defaultFontColour = "#000000";



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

        this.bgColour = defaultBgColour;
        this.borderColour = defaultBorderColour;
        this.fontType = defaultFontType;
        this.fontStyleBold=false;
        this.fontStyleItalic=false;
        this.fontColour = defaultFontColour;
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
     * @return the position of the Column in the list it belongs to (at the time)
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the new position of the Column in the list it belongs to (at the time),
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



    /**
     * @return the String of the Background Colour of a Column
     */
    public String getBgColour() {
        return bgColour;
    }
    /**
     * Set the background colour of a Column
     * @param colour new background colour to replace the Column's old one
     */
    public void setBgColour(String colour) {
        this.bgColour = colour;
    }
    /**
     * @return the String of the Border Colour of a Column
     */
    public String getBorderColour() {
        return borderColour;
    }
    /**
     * Set the borderColour of a Column
     * @param borderColour new borderColour to replace the Column's old one
     */
    public void setBorderColour(String borderColour) {
        this.borderColour = borderColour;
    }
    /**
     * @return the String of the Font-type of a Column
     */
    public String getFontType() {
        return fontType;
    }
    /**
     * Set the font-type of a Column
     * @param fontType new font type to replace the Column's old one
     */
    public void setFontType(String fontType) {
        this.fontType = fontType;
    }
    /**
     * @return whether the Column is Bold
     */
    public boolean isFontStyleBold() {
        return fontStyleBold;
    }

    /**
     * Set the Column to be Bold or not
     * @param fontStyleBold value of the Boldness of the Column
     */
    public void setFontStyleBold(boolean fontStyleBold) {
        this.fontStyleBold = fontStyleBold;
    }
    /**
     * @return whether the Column is Italic
     */
    public boolean isFontStyleItalic() {
        return fontStyleItalic;
    }


    /**
     * Set the Column to be Italic or not
     * @param fontStyleItalic value of the Italicness of the Column
     */
    public void setFontStyleItalic(boolean fontStyleItalic) {
        this.fontStyleItalic = fontStyleItalic;
    }
    /**
     * @return the String of the Font Colour of a Column
     */
    public String getFontColour() {
        return fontColour;
    }

    /**
     * Set the font colour of a Column
     * @param fontColour new font colour to replace the Column's old one
     */
    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }


    /**
     * @return The default Background Colour of a Column
     */
    public String getDefaultBgColour() {
        return defaultBgColour;
    }

    /**
     * @return The default Border Colour of a Column
     */
    public String getDefaultBorderColour() {
        return defaultBorderColour;
    }

    /**
     * @return The default Font-Type of a Column
     */
    public String getDefaultFontType() {
        return defaultFontType;
    }

    /**
     * @return The default Font Colour of a Column
     */
    public String getDefaultFontColour() {
        return defaultFontColour;
    }
}
