//package commons;
//
//import javax.persistence.*;
//
//@Entity
//public class Subtask {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//    private String description;
//    private boolean done;
//
//    @ManyToOne
//    @JoinColumn(name = "card_details_id")
//    private CardDetails cardDetails;
//}
