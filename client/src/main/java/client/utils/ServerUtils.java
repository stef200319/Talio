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
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import org.glassfish.jersey.client.ClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
     *
     * @param column
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
     *
     * @param card
     * @return new Card to databae
     */

    public Card addCard(Card card) {
        String title = card.getTitle();
        Long columnId = card.getColumnId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("card/addCard/" + title + "/" + columnId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(card, APPLICATION_JSON), Card.class);
    }

    /**
     *
     * @param board
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

}