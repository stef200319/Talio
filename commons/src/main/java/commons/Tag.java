package commons;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String color;

    /**
     * constructor for object mappers
     */
    public Tag() {
        // for object mappers
    }

    /**
     * Constructor for a Tag
     * @param title title of the tag
     * @param color the String representation of the hex value
     */
    public Tag(String title, String color) {
        this.title = title;
        this.color = color;
    }

    /**
     * getter for the id
      * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * getter for the title
     * @return title
     */
    public String getTitle() {
        return title;
    }


    /**
     * getter for the color
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * setter for the title
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setter for the color
     * @param color the new color
     */
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getId(), tag.getId()) && Objects.equals(getTitle(), tag.getTitle()) && Objects.equals(getColor(), tag.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getColor());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
