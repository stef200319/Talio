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
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private BoardOverviewCtrl overviewCtrl;
    private Scene overview;
    private AddListCtrl addListCtrl;
    private Scene addList;

    private AddQuoteCtrl addCtrl;
    private Scene add;
    private BoardOverviewCtrl boardOverviewCtrl;
    private Scene boardOverview;
    private ClientConnectCtrl clientConnectCtrl;
    private Scene clientConnect;
    private TaskDetailsCtrl taskDetailsCtrl;
    private Scene taskDetails;


    /**
     * @param primaryStage
     * @param overview
     * @param add
     * @param boardOverview
     * @param clientConnect
     * @param taskDetails
     */
    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,

                           Pair<AddQuoteCtrl, Parent> add, Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<ClientConnectCtrl, Parent> clientConnect, Pair<TaskDetailsCtrl, Parent> taskDetails) {

        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addListCtrl = add.getKey();
        this.addList = new Scene(add.getValue());

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverview = new Scene(boardOverview.getValue());

        this.clientConnectCtrl = clientConnect.getKey();
        this.clientConnect = new Scene(clientConnect.getValue());

        this.taskDetailsCtrl = taskDetails.getKey();
        this.taskDetails = new Scene(taskDetails.getValue());

//        showOverview();
        showClientConnect();
        primaryStage.show();
    }


    /**
     *
     */
    public void showOverview() {
        primaryStage.setTitle("Board: Overview");
        primaryStage.setScene(overview);
    }

    /**
     *
     */
    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    public void showListAdd() {
        primaryStage.setTitle("Adding List");
        primaryStage.setScene(addList);
        //add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Show all the boards
     */
    public void showBoardOverview() {
        primaryStage.setTitle("Board Overview");
        primaryStage.setScene(boardOverview);
    }

    /**
     * Connect to client
     */
    public void showClientConnect() {
        primaryStage.setTitle("Client Connect");
        primaryStage.setScene(clientConnect);
    }

    /**
     * Show the task details
     */
    public void showTaskDetails() {
        primaryStage.setTitle("Task Details");
        primaryStage.setScene(taskDetails);
    }

}