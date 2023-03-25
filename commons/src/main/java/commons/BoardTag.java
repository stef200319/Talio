package commons;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class BoardTag extends Tag {


    @ManyToMany(mappedBy = "tags")
    private Set<Board> boards;



    /**
     * Constructor for object mappers
     */
    public BoardTag() {

    }

    public BoardTag(String title, String color) {
        super(title, color);
    }
}
