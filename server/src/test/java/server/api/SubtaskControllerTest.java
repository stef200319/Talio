package server.api;

import commons.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.services.SubtaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskControllerTest {
    private TestSubtaskRepository subtaskRepository;
    private SubtaskService subtaskService;
    private SubtaskController subtaskController;

    @BeforeEach
    void setUp() {
        this.subtaskRepository = new TestSubtaskRepository();
        subtaskService = new SubtaskService(subtaskRepository);
        subtaskController = new SubtaskController(subtaskService);
    }

    @Test
    void editSubtaskStatus_doesntExist() {
        ResponseEntity<Subtask> ret = subtaskController.editSubtaskStatus(10, true);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void editSubtaskStatus_successful() {
        ResponseEntity<Subtask> ret = subtaskController.editSubtaskStatus(0, true);
        Subtask expected = new Subtask("test1");
        expected.setDone(true);
        expected.setId(0);
        assertEquals(expected, ret.getBody());
    }

    @Test
    void editSubtaskTitle_doesntExist() {
        ResponseEntity<Subtask> ret = subtaskController.editSubtaskTitle(10, "test");
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void editSubtaskTitle_nullTitle() {
        ResponseEntity<Subtask> ret = subtaskController.editSubtaskTitle(0, null);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void editSubtaskTitle_successful() {
        ResponseEntity<Subtask> ret = subtaskController.editSubtaskTitle(0, "new");
        Subtask expected = new Subtask("new");
        expected.setDone(false);
        expected.setId(0);
        assertEquals(expected, ret.getBody());
    }

    @Test
    void deleteSubtask_doesntExist() {
        ResponseEntity<Subtask> ret = subtaskController.deleteSubtask(10);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void deleteSubtask_successful() {
        ResponseEntity<Subtask> ret = subtaskController.deleteSubtask(0);
        Subtask expected = new Subtask("test1");
        expected.setDone(false);
        expected.setId(0);
        assertEquals(expected, ret.getBody());
    }

    @Test
    void getById_doesntExist() {
        ResponseEntity<Subtask> ret = subtaskController.getSubtaskById(10);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void getById_successful() {
        ResponseEntity<Subtask> ret = subtaskController.getSubtaskById(0);
        Subtask expected = new Subtask("test1");
        expected.setDone(false);
        expected.setId(0);
        assertEquals(expected, ret.getBody());
        assertEquals(expected, ret.getBody());
    }

    @Test
    void saveService() {
        Subtask expected = new Subtask("new");
        subtaskService.save(expected);
        expected.setId(2);
        assertEquals(expected, subtaskController.getSubtaskById(2).getBody());
    }
}
