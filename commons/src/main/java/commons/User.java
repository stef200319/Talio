package commons;

import java.util.List;

public class User {

    private String name;
    private List<Long> boardIds;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBoardIds() {
        return boardIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoardIds(List<Integer> boardIds) {
        this.boardIds = boardIds;
    }

    public boolean containsBoardId(long id) {
        return boardIds.contains(id);
    }
}
