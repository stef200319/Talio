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
      * Adds a column to the database
     * @param column the column to add to the database
     * @return new Column to database
     */

    public Column addColumn(Column column) {
        String title = column.getTitle();
        Long boardId = column.getBoardId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("column/addColumn/" + title + "/" + boardId)
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
     * @return response to database
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
     * @return response to database
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

}