package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String description;
    boolean done;
}
