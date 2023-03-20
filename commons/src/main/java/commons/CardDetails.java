package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
@Entity
public class CardDetails {
    @Id
    long cardId;
    String cardTitle;
    String description;
//    List<CardTag> tags;
//    List<Subtask> subtasks;

    public CardDetails(long cardId, String cardTitle) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
    }
}
