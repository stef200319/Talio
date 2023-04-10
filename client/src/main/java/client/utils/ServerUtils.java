/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import commons.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@SuppressWarnings({"ParameterName", "StaticVariableName"})
public class ServerUtils {

    private static String SERVER = null;

    /**
     * @throws IOException
     */
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Fetches the columns to be displayed on a board
     * @param boardId the id of the board
     * @return the list of columns on the board
     */
    public List<Column> getColumnsByBoardId(long boardId) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("board/getColumnsByBoardId/"+boardId)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<List<Column>>() {});
    }

    /**
     * Fetches the boards to be displayed on a workspace
     * @return the list of boards on the server
     */
    public List<Board> getAllBoardsWithoutServers() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/getAllBoards")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Board>>() {});
    }


    /**
     * Fetches the cards to be displayed in a column
     * @param columnID the id of the column
     * @return the list of cards in the column
     */
    public List<Card> getCardsByColumnId(long columnID) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("column/getCardsByColumnId/"+columnID)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<List<Card>>() {});
    }

    /**
     * Fetches a column frmo the database from its id
     * @param columnID the id of the column
     * @return a column
     */
    public Column getColumnByColumnId(long columnID){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/getColumnByColumnId/"+columnID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Column>(){});
    }
    /**
     * Fetch a board from database from its id
     * @param boardID the id of the board
     * @return a board
     */
    public Board getBoardByID(long boardID) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("board/getBoardByBoardId/"+boardID)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Board>() {});
    }

    /**
     * Adds a column to the database
     * @param column the column to add to the database
     * @param boardID boardID of the board of column
     * @return new Column to database
     */

    public Column addColumn(Column column, long boardID) {
        String title = column.getTitle();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/addColumn/" + title + "/" + boardID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(column, APPLICATION_JSON), Column.class);
    }

    /**
     * Adds a card to the database
     * @param card the card to add to the database
     * @param columnID columnId of column that card will be added to
     * @return new Card to databae
     */

    public Card addCard(Card card, Long columnID) {
        String title = card.getTitle();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/addCard/" + title + "/" + columnID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(card, APPLICATION_JSON), Card.class);
    }

    /**
     * Adds a board to the database
     * @param board the board to add to the database
     * @return new Board to database
     */

    public Board addBoard(Board board) {
        String title = board.getTitle();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/addBoard/" + title)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
    }


    /**

     * Method that edits the title of a board
     * @param b board to edit
     * @param title is the new title
     * @return new board entity
     */
    public Board editBoardTitle(Board b, String title) {
        long boardId = b.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardTitle/" + title + "/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(b, APPLICATION_JSON), Board.class);
    }



    /**
     * Method that edits the Background Colour of a Board
     * @param c Board to edit
     * @param bgColour new Background Colour
     * @return new Board entity
     */
    public Board editBoardCenterColour(Board c, String bgColour) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardCenterColour/"+boardId+"/"+bgColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }

    /**
     * Method that edits the Background Colour of a Board
     * @param c Board to edit
     * @param bgColour new Background Colour
     * @return new Board entity
     */
    public Board editBoardSideColour(Board c, String bgColour) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardSideColour/"+boardId+"/"+bgColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }


    /**
     * Method that edits the Background Colour of a Board
     * @param c Board to edit
     * @param bgColour new Background Colour
     * @return new Board entity
     */
    public Board editBoardFontType(Board c, String bgColour) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardFontType/"+boardId+"/"+bgColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }




    /**
     * Method that edits the Font Boldness of a Board
     * @param c Board to edit
     * @param fontStyleBold new Font Boldness
     * @return new Board entity
     */
    public Board editBoardFontStyleBold(Board c, boolean fontStyleBold) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardFontStyleBold/"+boardId+"/"+fontStyleBold)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }


    /**
     * Method that edits the Font Italicness of a Board
     * @param c Board to edit
     * @param fontStyleItalic new Font Italicness
     * @return new Board entity
     */
    public Board editBoardFontStyleItalic(Board c, boolean fontStyleItalic) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardFontStyleItalic/"+boardId+"/"+fontStyleItalic)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }

    /**
     * Method that edits the Font Colour of a Board
     * @param c Board to edit
     * @param fontColour new Font Font Colour
     * @return new Board entity
     */
    public Board editBoardFontColour(Board c, String fontColour) {
        long boardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/editBoardFontColour/"+boardId+"/"+fontColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Board.class);
    }


    /**
     * Method that deletes a column
     * @param c column to delete
     * @return response
     */
    public Response deleteColumn(Column c) {
        long columnId = c.getId();
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("column/deleteColumn/" + columnId)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .delete();
    }

    /**
     * Method that deletes a card
     * @param c card to delete
     * @return response
     */
    public Response deleteCard(Card c) {
        long cardId = c.getId();
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/deleteCard/" + cardId)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .delete();
    }

    /**
     * Method that deletes a board
     * @param b board to delete
     * @return response
     */

    public Response deleteBoard(Board b) {
        long boardID = b.getId();
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("board/deleteBoard/" + boardID)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .delete();
    }

    /**
     * Method that edits the title of a column
     * @param c column to edit
     * @param title new title
     * @return new column entity
     */
    public Column editColumnTitle(Column c, String title) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("column/editColumnTitle/"+columnId+"/"+title)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }

    /**
     * Method that edits the Background Colour of a Column
     * @param c Column to edit
     * @param bgColour new Background Colour
     * @return new Column entity
     */
    public Column editColumnBackgroundColour(Column c, String bgColour) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnBackgroundColour/"+columnId+"/"+bgColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }

    /**
     * Method that edits the Border Colour of a Column
     * @param c Column to edit
     * @param borderColour new Border Colour
     * @return new Column entity
     */
    public Column editColumnBorderColour(Column c, String borderColour) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnBorderColour/"+columnId+"/"+borderColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }


    /**
     * Method that edits the Font-type of a Column
     * @param c Column to edit
     * @param fontType new Font-type
     * @return new Column entity
     */
    public Column editColumnFontType(Column c, String fontType) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnFontType/"+columnId+"/"+fontType)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }

    /**
     * Method that edits the Font Boldness of a Column
     * @param c Column to edit
     * @param fontStyleBold new Font Boldness
     * @return new Column entity
     */
    public Column editColumnFontStyleBold(Column c, boolean fontStyleBold) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnFontStyleBold/"+columnId+"/"+fontStyleBold)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }


    /**
     * Method that edits the Font Italicness of a Column
     * @param c Column to edit
     * @param fontStyleItalic new Font Italicness
     * @return new Column entity
     */
    public Column editColumnFontStyleItalic(Column c, boolean fontStyleItalic) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnFontStyleItalic/"+columnId+"/"+fontStyleItalic)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }

    /**
     * Method that edits the Font Colour of a Column
     * @param c Column to edit
     * @param fontColour new Font Font Colour
     * @return new Column entity
     */
    public Column editColumnFontColour(Column c, String fontColour) {
        long columnId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/editColumnFontColour/"+columnId+"/"+fontColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Column.class);
    }












    /**
     * Method that edits the title of a card
     * @param c card to edit
     * @param title new title
     * @return new card entity
     */
    public Card editCardTitle(Card c, String title) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/editCardTitle/"+cardId+"/"+title)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }


        /**
     * Method that edits the description of a card
     * @param c card to edit
     * @param description new description
     * @return new card entity
     */
    public Card editCardDescription(Card c, String description) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardDescription/"+cardId+"/"+description)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }

    /**
     * Method that edits the Background Colour of a card
     * @param c card to edit
     * @param bgColour new Background Colour
     * @return new card entity
     */
    public Card editCardBackgroundColour(Card c, String bgColour) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardBackgroundColour/"+cardId+"/"+bgColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }

    /**
     * Method that edits the Border Colour of a card
     * @param c card to edit
     * @param borderColour new Border Colour
     * @return new card entity
     */
    public Card editCardBorderColour(Card c, String borderColour) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardBorderColour/"+cardId+"/"+borderColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }


    /**
     * Method that edits the Font-type of a card
     * @param c card to edit
     * @param fontType new Font-type
     * @return new card entity
     */
    public Card editCardFontType(Card c, String fontType) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardFontType/"+cardId+"/"+fontType)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }

    /**
     * Method that edits the Font Boldness of a card
     * @param c card to edit
     * @param fontStyleBold new Font Boldness
     * @return new card entity
     */
    public Card editCardFontStyleBold(Card c, boolean fontStyleBold) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardFontStyleBold/"+cardId+"/"+fontStyleBold)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }


    /**
     * Method that edits the Font Italicness of a card
     * @param c card to edit
     * @param fontStyleItalic new Font Italicness
     * @return new card entity
     */
    public Card editCardFontStyleItalic(Card c, boolean fontStyleItalic) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardFontStyleItalic/"+cardId+"/"+fontStyleItalic)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }

    /**
     * Method that edits the Font Colour of a card
     * @param c card to edit
     * @param fontColour new Font Font Colour
     * @return new card entity
     */
    public Card editCardFontColour(Card c, String fontColour) {
        long cardId=c.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/editCardFontColour/"+cardId+"/"+fontColour)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(c, APPLICATION_JSON), Card.class);
    }


     /**
     * Method that returns a card by its id
     * @param id id of the card
     * @return the card object corresponding to that id
     */
    public Card getCardById(Long id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/getCardByCardId/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Card>()  {});
    }

    /**
     * Method that changes the positionj of a card in a column
     * @param id the id of the card to be changed
     * @param pos new position of the card
     * @return the new card with updated position
     */
    public Card editCardPosition(long id, int pos) {
        return  ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/editCardPosition/" + id + "/" + pos)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(getCardById(id), APPLICATION_JSON), Card.class);
    }

    /**
     * Method that changes the column a card is attached to
     * @param cardId id of the card to be changed
     * @param columnId id of the column the card will be moved to
     * @return the new card with an updated column
     */
    public Card editCardColumn(long cardId, long columnId) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/editCardColumn/" + cardId + "/" + columnId)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(getCardById(cardId), APPLICATION_JSON), Card.class);
    }

    /** Method that adds a cardTag to a board so that cards inside of that board can make use of that tag
     * @param cardTag
     * @param boardId
     * @return CardTag that is added
     */
    public CardTag addCardTagToBoard(CardTag cardTag, long boardId) {
        String title = cardTag.getTitle();
        String color = cardTag.getColor();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/addCardTagToBoard/" + title + "/" + color + "/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(cardTag, APPLICATION_JSON), CardTag.class);
    }

    /**
     * Method that deletes a cardTag from a board
     * @param cardTag
     * @return Response
     */
    public Response deleteCardTagFromBoard(CardTag cardTag) {
        long cardTagId = cardTag.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/deleteCardTagFromBoard/" + cardTagId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Adds CardTag to Card
     * @param cardTag
     * @param cardId
     * @return the added CardTag
     */
    public CardTag addCardTagToCard(CardTag cardTag, long cardId) {
        long cardTagId = cardTag.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/addCardTagToCard/" + cardTagId + "/" + cardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(cardTag, APPLICATION_JSON), CardTag.class);
    }

    /**
     * Deletes a CardTag from a Card
     * @param cardTag
     * @param cardId
     * @return Response
     */
    public Response deleteCardTagFromCard(CardTag cardTag, long cardId){
        long cardTagId = cardTag.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/deleteCardTagFromCard/" + cardTagId + "/" + cardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Edits the CardTag title
     * @param cardTag
     * @param title
     * @return CardTag that is edited
     */
    public CardTag editCardTagTitle(CardTag cardTag, String title) {
        long cardTagId = cardTag.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/editCardTagTitle/" + cardTagId + "/" + title)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(cardTag, APPLICATION_JSON), CardTag.class);
    }

    /**
     * Edits the CardTag color
     * @param cardTag
     * @param color
     * @return the CardTag that is edited
     */
    public CardTag editCardTagColor(CardTag cardTag, String color) {
        long cardTagId = cardTag.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/editCardTagColor/" + cardTagId + "/" + color)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(cardTag, APPLICATION_JSON), CardTag.class);
    }

    /**
     * Gets cardTagsByBoardId
     * @param boardId
     * @return list of card Tags
     */
    public List<CardTag> getCardTagsByBoardId(long boardId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("cardTag/getCardTagsByBoardId/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<CardTag>>() {});
    }

    /**
     * Gets all the cardTags given a cardId
     * @param cardId
     * @return a List of cardTags
     */
    public List<CardTag> getCardTagsByCardId(Long cardId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/getCardTagsByCardId/" + cardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<CardTag>>() {});
    }













    /**
     * Adds a board tag
     * @param boardTag the board tag added
     * @return new board tag to database
     */
    public BoardTag addBoardTag(BoardTag boardTag)
    {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/addBoardTag/"+ boardTag.getTitle() + "/" + boardTag.getColor())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(boardTag, APPLICATION_JSON), BoardTag.class);
    }

    /**
     * deletes a board tag
     * @param boardTag the board tag to be deleted
     * @return response
     */
    public Response deleteBoardTag(BoardTag boardTag)
    {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/deleteBoardTag/" + boardTag.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * fetches all the board tags
     * @return a list of all the board tags
     */
    public List<BoardTag> getAllBoardTags(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/getAllBoardTags")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<BoardTag>>(){});
    }

    /**
     * adds a board tag to a board
     * @param boardId the id of the board
     * @param boardTag the board tag that is going to be added
     * @return new board tag
     */
    public BoardTag addBoardTagToBoard(long boardId, BoardTag boardTag)
    {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/addBoardTagToBoard/"+boardTag.getId()+"/"+boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(boardTag, APPLICATION_JSON), BoardTag.class);
    }

    /**
     * deletes a board tag from a board
     * @param boardId the id of the board
     * @param boardTag the board tag to be deleted
     * @return response
     */
    public Response deleteBoardTagFromBoard(long boardId, BoardTag boardTag) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/deleteBoardTagFromBoard/" + boardTag.getId() + "/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

     /**
     * edits the color of a board tag
     * @param boardTag the board tag that is going to be edited
     * @param color the new color
     * @return the board tag with the new color
     */
    public BoardTag editBoardTagColor(BoardTag boardTag, String color)
    {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/editBoardTagColor/"+boardTag.getId()+"/"+color)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(boardTag, APPLICATION_JSON), BoardTag.class);
    }

    /**
     * method that edits the title of a board tag
     * @param boardTag to be edited
     * @param title the new title
     * @return the board tag with the updated title
     */
    public BoardTag editBoardTagTitle(BoardTag boardTag, String title)
    {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("boardTag/editBoardTagTitle/"+ boardTag.getId()+"/"+title)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(boardTag, APPLICATION_JSON), BoardTag.class);
    }

    /**
     * Gets boardTgas by boardId
     * @param id
     * @return list of boardTags
     */
    public List<BoardTag> getBoardTagsByBoardId(Long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/getBoardTagsByBoardId/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<BoardTag>>(){});
    }










    /**
     * Gets the board given a certain CardId
     * @param cardId
     * @return the board
     */
    public Board getBoardByCardId(Long cardId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("board/getBoardByCardId/" + cardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Board>() {});
    }














    /**
     * Method that adds a subtask to a card
     * @param cardId id of the card to add the subtask to
     * @param subtaskTitle title of the new subtask
     * @return the new card with updated subtasks
     */
    public Card addSubtask(long cardId, String subtaskTitle) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/addSubtask/" + cardId + "/" + subtaskTitle)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(getCardById(cardId), APPLICATION_JSON), Card.class);
    }

    /**
     * Gets a subtask by its id
     * @param subtaskId id of the subtask
     * @return the subtask with that id
     */
    public Subtask getSubtaskById(long subtaskId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("subtask/getSubtaskById/" + subtaskId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Subtask>() {});
    }

    /**
     * Changes the status of a subtask
     * @param subtaskId id of the subtask to be changed
     * @param status new status of the subtask
     * @return the new status with an updated status
     */
    public Subtask editSubtaskStatus(long subtaskId, boolean status) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("subtask/editSubtaskStatus/" + subtaskId + "/" + status)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(getSubtaskById(subtaskId), APPLICATION_JSON), Subtask.class);
    }

    /**
     * Change a position of a subtask in a card
     * @param cardId id of the card the subtask is in
     * @param oldPos old position of the subtask
     * @param newPos new position of the subtask
     * @return the card with specified cardId and updated subtasks
     */
    public Card changeSubtaskPosition(long cardId, int oldPos, int newPos) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/changeSubtaskPosition/" + cardId + "/" + oldPos + "/" + newPos)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(getCardById(cardId), APPLICATION_JSON), Card.class);
    }

    /**
     * Deletes a subtask
     * @param subtaskId subtask to be deleted
     * @return response
     */
    public Response deleteSubtask(long subtaskId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("subtask/deleteSubtask/" + subtaskId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Deletes a subtask from a card
     * @param cardId id of the card the subtask is in
     * @param subtaskId id of the subtask to be deleted
     * @return response
     */
    public Response deleteSubtaskFromCard(long cardId, long subtaskId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/deleteSubtask/" + cardId + "/" + subtaskId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Method that edits the title of a Subtask
     * @param s Subtask to edit
     * @param title new title
     * @return new Subtask entity
     */
    public Subtask editSubtaskTitle(Subtask s, String title) {
        long subtaskID = s.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("subtask/editSubtaskTitle/" + subtaskID + "/" + title)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(s, APPLICATION_JSON), Subtask.class);
    }

    /**
     * Method that gets the card tag with sepcified id
     * @param id if of the tag
     * @return cardtag
     */
    public CardTag getCardTagById(long id){
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("cardTag/getCardTagById/"+id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<CardTag>(){});
    }

    /**
     * Method that checks if card exists by id
     * @param id id of the card to check for
     * @return true if it exists false otherwise
     */
    public boolean existsByIdCard(long id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER)
            .path("card/existsById/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Boolean>(){});
    }

    /**
     * Sets the server address
     * @param server new address
     */
    public void setSERVER(String server) {
        this.SERVER = "http://" + server + "/";
    }

    /**
     * getServer that should be temporarily
     * @return server
     */
    public String getServer() {
        return this.SERVER;
    }
}