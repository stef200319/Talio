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

import commons.Column;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;
    private Scene overview;
    private AddListCtrl addListCtrl;
    private Scene addList;
    private Scene add;
    private BoardOverviewCtrl boardOverviewCtrl;
    private Scene boardOverview;
    private ClientConnectCtrl clientConnectCtrl;
    private Scene clientConnect;
    private TaskDetailsCtrl taskDetailsCtrl;
    private Scene taskDetails;

    private AddTaskCtrl addTaskCtrl;
    private Scene addTask;

    private TaskManagementCtrl taskManagementCtrl;
    private Scene taskManagement;

    private CreateBoardCtrl createBoardCtrl;
    private Scene createBoard;
    private WorkspaceCtrl workspaceCtrl;
    private Scene workspace;

    private EditListCtrl editListCtrl;
    private Scene editList;

    /**
     * @param primaryStage
     * @param add
     * @param boardOverview
     * @param clientConnect
     * @param taskDetails
     * @param addTask
     * @param workspace
     * @param taskManagement
     * @param createBoard
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    public void initialize(Stage primaryStage,

                           Pair<AddListCtrl, Parent> add, Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<ClientConnectCtrl, Parent> clientConnect, Pair<TaskDetailsCtrl, Parent> taskDetails,
                           Pair<AddTaskCtrl, Parent> addTask, Pair<TaskManagementCtrl, Parent> taskManagement,
                           Pair<WorkspaceCtrl, Parent> workspace, Pair<CreateBoardCtrl, Parent> createBoard,
                           Pair<EditListCtrl, Parent> editList) {

        this.primaryStage = primaryStage;

        this.addListCtrl = add.getKey();
        this.addList = new Scene(add.getValue());

        this.createBoardCtrl = createBoard.getKey();
        this.createBoard = new Scene(createBoard.getValue());

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverview = new Scene(boardOverview.getValue());

        this.clientConnectCtrl = clientConnect.getKey();
        this.clientConnect = new Scene(clientConnect.getValue());

        this.taskDetailsCtrl = taskDetails.getKey();
        this.taskDetails = new Scene(taskDetails.getValue());

        this.addTaskCtrl = addTask.getKey();
        this.addTask = new Scene(addTask.getValue());

        this.taskManagementCtrl = taskManagement.getKey();
        this.taskManagement = new Scene(taskManagement.getValue());

        this.workspaceCtrl = workspace.getKey();
        this.workspace = new Scene(workspace.getValue());

        this.editListCtrl = editList.getKey();
        this.editList = new Scene(editList.getValue());

        showClientConnect();
        primaryStage.show();
    }


    /**
     *
     */
    public void showOverview() {
        primaryStage.setTitle("Board: Overview");
        primaryStage.setScene(boardOverview);
    }
    /**
     * show workspace page
     */
    public void showWorkspace(){
        primaryStage.setTitle("Workspace");
        primaryStage.setScene(workspace);
    }
    /**
     * show task management page
     */
    public void showTaskManagement(){
        primaryStage.setTitle("Task management");
        primaryStage.setScene(taskManagement);
    }

    /**
     * Show add list page
     */
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
     * Show board create
     */
    public void showCreateBoard() {
        primaryStage.setTitle("Create board");
        primaryStage.setScene(createBoard);
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

    /**
     * Show add task page
     */
    public void showAddTask() {
        primaryStage.setTitle("Add Task");
        primaryStage.setScene(addTask);
    }

    /**
     * Show edit list page
     * @param c the list which will be changed
     */
    public void showEditList(Column c) {
        editListCtrl.setColumnToEdit(c);
        primaryStage.setTitle("Edit Column");
        primaryStage.setScene(editList);
    }

}