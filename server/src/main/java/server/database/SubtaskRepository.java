package server.database;

import commons.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}
