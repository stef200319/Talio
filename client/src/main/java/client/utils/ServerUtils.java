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

import commons.Board;
import commons.Card;
import commons.Column;
import commons.Subtask;
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

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

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

}