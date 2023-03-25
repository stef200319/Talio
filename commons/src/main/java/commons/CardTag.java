package commons;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CardTag extends Tag {

    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    @ManyToMany(mappedBy = "tags")
    private Set<Card> cards;

    /**
     * Constructor for object mappers
     */
    public CardTag() { }

    /**
     * Constructor for cardTag
     * @param title title fo the cardTag
     * @param color color of the cardTag
     * @param board board it the is stored in
     */
    public CardTag(String title, String color, Board board) {
        super(title, color);
        this.board = board;
    }

    /**
     * getter for the board
     * @return board the card is stored in
     */
    public Board getBoard() {
        return board;
    }

    /**
     * getter for all the cards a tag belongs to
     * @return set of cards the this belongs to
     */
    public Set<Card> getCards() {
        return cards;
    }
}
