package commons;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class CardTag extends Tag {

    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardTag)) return false;
        if (!super.equals(o)) return false;
        CardTag cardTag = (CardTag) o;
        return Objects.equals(getBoard(), cardTag.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBoard());
    }

    @Override
    public String toString() {
        return "CardTag{" +
                "board=" + board +
                '}';
    }
}
