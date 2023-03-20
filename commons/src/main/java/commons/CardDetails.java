package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class CardDetails {
    @Id
    private long cardId;
    private String cardTitle;
    private String description;
//    List<CardTag> tags;
//    List<Subtask> subtasks;

    /**
     * @param cardId
     * @param cardTitle
     */
    public CardDetails(long cardId, String cardTitle) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
    }
}
