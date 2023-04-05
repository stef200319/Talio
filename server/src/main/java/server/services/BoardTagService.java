package server.services;

import commons.Board;
import commons.BoardTag;
import commons.CardTag;
import org.springframework.stereotype.Service;
import server.database.BoardTagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardTagService {
    private final BoardTagRepository boardTagRepository;


    public BoardTagService(BoardTagRepository boardTagRepository){

        this.boardTagRepository = boardTagRepository;

    }

    public List<BoardTag> getAll(){
        return boardTagRepository.findAll();
    }

    public boolean existsById(long id)
    {
        return boardTagRepository.existsById(id);
    }

    public BoardTag getById(long id){
        Optional<BoardTag> boardTag = boardTagRepository.findById(id);
        return boardTag.orElse(null);
    }

    public BoardTag add(String title, String color)
    {
        BoardTag boardTag = new BoardTag(title, color);
        return save(boardTag);

    }
    public BoardTag editTitle(long id, String title)
    {
        if(!boardTagRepository.existsById(id))
            return null;
        BoardTag boardTag = getById(id);
        boardTag.setTitle(title);
        return save(boardTag);
    }
    public BoardTag editColor(long Id, String color)
    {
        if(!boardTagRepository.existsById(Id))
            return null;
        BoardTag boardTag = getById(Id);
        boardTag.setColor(color);
        return save(boardTag);
    }
    public BoardTag delete(long Id)
    {
        if(!boardTagRepository.existsById(Id))
            return null;
        BoardTag boardTag = getById(Id);
        boardTagRepository.deleteById(Id);
        return boardTag;
    }
    public BoardTag save(BoardTag boardTag)
    {
        return boardTagRepository.save(boardTag);
    }
}