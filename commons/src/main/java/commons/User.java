package commons;

import java.util.List;

public class User {

    private String name;
    private List<Long> boardIds;

    /**
     * @param name the name of the user (generated)
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return List of all the board ids
     */
    public List<Long> getBoardIds() {
        return boardIds;
    }

    /**
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param boardIds set a new list of board ids
     */
    public void setBoardIds(List<Long> boardIds) {
        this.boardIds = boardIds;
    }

    /**
     * @param id the id of the board for which is checked that it is in the list of ids
     * @return whether the id is present
     */
    public boolean containsBoardId(long id) {
        return boardIds.contains(id);
    }

    /**
     * @param id of the board
     * @return whether it was removed successfully
     */
    public boolean removeBoardId(long id) {
        return boardIds.remove(id);
    }
}
