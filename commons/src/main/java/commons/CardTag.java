package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long boardId;
    private String title;
    private String colour;

}
