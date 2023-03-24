package commons;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cardTitle;
    private String description;


    @OneToOne(mappedBy = "cardDetails")
    private Card card;

    /**
     * Default constructor
     */
    public CardDetails() {
    }

    /**
     * Constructor with cardTitle parameter
     * @param cardTitle the title of the card
     */
    public CardDetails(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    /**
     * Get the id of the CardDetails object
     * @return the id of the CardDetails object
     */
    public long getId() {
        return id;
    }

    /**
     * Get the title of the card
     * @return the title of the card
     */
    public String getCardTitle() {
        return cardTitle;
    }

    /**
     * Set the title of the card
     * @param cardTitle the new title of the card
     */
    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    /**
     * Get the description of the card
     * @return the description of the card
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the card
     * @param description the new description of the card
     */
    public void setDescription(String description) {
        this.description = description;
    }

//    /**
//     * Get the list of subtasks for the card
//     * @return the list of subtasks for the card
//     */
//    public List<Subtask> getSubtasks() {
//        return subtasks;
//    }
//
//    /**
//     * Set the list of subtasks for the card
//     * @param subtasks the new list of subtasks for the card
//     */
//    public void setSubtasks(List<Subtask> subtasks) {
//        this.subtasks = subtasks;
//    }
//

}
