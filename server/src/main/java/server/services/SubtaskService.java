package server.services;

import commons.Subtask;
import org.springframework.stereotype.Service;
import server.database.SubtaskRepository;

@Service
public class SubtaskService {
    private SubtaskRepository subtaskRepository;

    /**
     * @param subtaskRepository the table which contains all subtasks
     */
    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository=subtaskRepository;
    }

    /**
     * Checks if the subtask exists
     * @param subtaskId the subtask to check for
     * @return true if it exists, false otherwise
     */
    public boolean existsById(long subtaskId) {
        return subtaskRepository.existsById(subtaskId);
    }

    /**
     * Finds the subtask with given id
     * @param subtaskId id to search for
     * @return subtask with that id
     */
    public Subtask getById(long subtaskId) {
        if(!existsById(subtaskId))
            return null;
        return subtaskRepository.findById(subtaskId).get();
    }

    /**
     * Changes the subtask status
     * @param subtaskId id of the subtask to be changed
     * @param status new status of the subtask
     * @return The new updated subtask
     */
    public Subtask editStatus(long subtaskId, boolean status) {
        if(!subtaskRepository.existsById(subtaskId))
            return null;

        Subtask subtask = getById(subtaskId);
        subtask.setDone(status);
        subtaskRepository.save(subtask);
        return subtask;
    }

    /**
     * Changes the title of the subtask
     * @param subtaskId id of the subtask to be changed
     * @param title the new title of the subtask
     * @return the new updated subtask
     */
    public Subtask editTitle(long subtaskId, String title) {
        if(!subtaskRepository.existsById(subtaskId))
            return null;

        Subtask subtask = getById(subtaskId);
        subtask.setTitle(title);
        subtaskRepository.save(subtask);
        return subtask;
    }

    /**
     * Deletes a subtask from database
     * @param subtaskId id of the subtask to be deleted
     * @return the deleted subtask
     */
    public Subtask delete(long subtaskId) {
        if(!subtaskRepository.existsById(subtaskId))
            return null;
        Subtask subtask = getById(subtaskId);
        subtaskRepository.deleteById(subtaskId);
        return subtask;
    }

    /**
     * Saves a subtask to the database
     * @param subtask subtask to be saved
     * @return the subtask that was saved
     */
    public Subtask save(Subtask subtask) {
        subtaskRepository.save(subtask);
        return subtask;
    }
}
