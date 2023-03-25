package commons;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CardTag extends Tag {

    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    Board board;

    @ManyToMany(mappedBy = "tags")
    Set<Card> cards;

    /**
     * Constructor for object mappers
     */
    public CardTag() {

    }

    public CardTag(String title, String color, Board board) {
        super(title, color);
        this.board = board;
    }
}
