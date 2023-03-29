package commons;

import javax.persistence.Entity;

@Entity
public class BoardTag extends Tag {

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


}
