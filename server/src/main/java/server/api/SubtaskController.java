package server.api;

import commons.Subtask;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.services.SubtaskService;


@RestController
@RequestMapping("/subtask")
public class SubtaskController {

    private final SubtaskService subtaskService;

    /**
     * @param subtaskService the service for subtask operations
     */
    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    /**Change the status of a subtask, if it exists. Receive a message on the success of the edit. The status is true
     * iff the subtask is completed
     * @param subtaskId The ID of the subtask whose title should be changed
     * @param status Status which should replace the old status of the subtask, representing whether the subtask is
     *               completed or not.
     * @return receive a message indicating the status has changed, if the subtask exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PutMapping("/editSubtaskStatus/{subtaskId}/{status}")
    @ResponseBody
    public ResponseEntity<Subtask> editSubtaskStatus(@PathVariable("subtaskId") long subtaskId,
                                              @PathVariable("status") boolean status){

        Subtask subtask = subtaskService.editStatus(subtaskId, status);
        if(subtask==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(subtask);
    }

    /**Change the title of a subtask, if it exists. Receive a message on the success of the edit
     * @param subtaskId The ID of the card whose subtask should be changed
     * @param title Title which should replace the old title of the subtask
     * @return receive a message indicating the title has changed, if the subtask exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PutMapping("/editSubtaskTitle/{subtaskId}/{title}")
    @ResponseBody
    public ResponseEntity<Subtask> editSubtaskTitle(@PathVariable("subtaskId") long subtaskId,
                                                  @PathVariable("title") String title){
        if (title == null) {
            return ResponseEntity.badRequest().build();
        }

        Subtask subtask = subtaskService.editTitle(subtaskId, title);
        if(subtask == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(subtask);
    }


    /**
     * Delete a subtask from the database and receive a conformation, using the subtask's id, if the subtask exists.
     * @param subtaskId id of the subtask that is to be deleted
     * @return Returns the deleted subtask if the subtask is found and deleted. Else, receive an
     * appropriate response.
     */
    @DeleteMapping("/deleteSubtask/{subtaskId}")
    @ResponseBody public ResponseEntity<Subtask> deleteSubtask(@PathVariable("subtaskId") long subtaskId){

        Subtask subtaskDeleted = subtaskService.delete(subtaskId);
        if(subtaskDeleted == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(subtaskDeleted);
    }

    /**
     * Gets a subtask by its id
     * @param subtaskId id of the subtask
     * @return the subtask with specified id
     */
    @GetMapping("/getSubtaskById/{subtaskId}")
    @ResponseBody public ResponseEntity<Subtask> getSubtaskById(@PathVariable("subtaskId") long subtaskId) {
        Subtask subtask = subtaskService.getById(subtaskId);
        if(subtask==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(subtask);
    }

    /**
     * Message mapping for websockets
     * @param subtask subtask that was changed
     * @return subtask
     */
    @MessageMapping("/updateSubtask")
    @SendTo("/topic/updateSubtask")
    public Subtask updateSubtask(Subtask subtask) {
        return subtask;
    }
}
