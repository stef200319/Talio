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

import commons.Card;

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
    private EditCardTitleCtrl editCardTitleCtrl;
    private Scene editCardTitle;
    private EditCardDescriptionCtrl editCardDescriptionCtrl;
    private Scene editCardDescription;

    private EditListCtrl editListCtrl;
    private Scene editList;

    private EditBoardTitleCtrl editBoardTitleCtrl;
    private Scene editBoardTitle;

    private ViewSubtaskCtrl viewSubtaskCtrl;
    private Scene viewSubtask;
    private EditSubtaskTitleCtrl editSubtaskTitleCtrl;
    private Scene editSubtaskTitle;

    /**
     * @param primaryStage
     * @param add
     * @param boardOverview
     * @param clientConnect
     * @param taskDetails
     * @param addTask
     * @param taskManagement
     * @param workspace
     * @param createBoard
     * @param editCardTitle
     * @param editCardDescription
     * @param editList
     * @param editBoardTitle
     * @param viewSubtask
     * @param editSubtaskTitle
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    public void initialize(Stage primaryStage,

                           Pair<AddListCtrl, Parent> add, Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<ClientConnectCtrl, Parent> clientConnect, Pair<TaskDetailsCtrl, Parent> taskDetails,
                           Pair<AddTaskCtrl, Parent> addTask, Pair<TaskManagementCtrl, Parent> taskManagement,
                           Pair<WorkspaceCtrl, Parent> workspace, Pair<CreateBoardCtrl, Parent> createBoard,
                           Pair<EditCardTitleCtrl, Parent> editCardTitle,
                           Pair<EditCardDescriptionCtrl, Parent> editCardDescription, Pair<EditListCtrl,
                           Parent> editList, Pair<EditBoardTitleCtrl, Parent> editBoardTitle
                           , Pair<ViewSubtaskCtrl, Parent> viewSubtask,
                           Pair<EditSubtaskTitleCtrl, Parent> editSubtaskTitle) {


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

        this.editCardTitleCtrl = editCardTitle.getKey();
        this.editCardTitle = new Scene(editCardTitle.getValue());

        this.editCardDescriptionCtrl = editCardDescription.getKey();
        this.editCardDescription = new Scene(editCardDescription.getValue());

        this.editListCtrl = editList.getKey();
        this.editList = new Scene(editList.getValue());

        this.editBoardTitleCtrl = editBoardTitle.getKey();
        this.editBoardTitle = new Scene(editBoardTitle.getValue());

        this.viewSubtaskCtrl = viewSubtask.getKey();
        this.viewSubtask = new Scene(viewSubtask.getValue());

        this.editSubtaskTitleCtrl = editSubtaskTitle.getKey();
        this.editSubtaskTitle = new Scene(editSubtaskTitle.getValue());


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
     * @param boardID boardID of list's board
     */
    public void showListAdd(Long boardID) {
        addListCtrl.setBoardToAddId(boardID);
        primaryStage.setTitle("Adding List");
        primaryStage.setScene(addList);
        //add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Show all the boards
     * @param boardID boardID of current board
     */
    public void showBoardOverview(Long boardID) {
        boardOverviewCtrl.setBoardID(boardID);
        primaryStage.setTitle("Board Overview");
        boardOverviewCtrl.refresh();
        primaryStage.setScene(boardOverview);
    }

    /**
     * Show board create
     * @param boardID boardID of the board to be in
     */
    public void showCreateBoard(long boardID) {
        createBoardCtrl.setBoardID(boardID);
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
     * @param card Card whose details have to be shown
     */
    public void showTaskDetails(Card card) {
        taskDetailsCtrl.setCardToShow(card);
        primaryStage.setTitle("Task Details");
        primaryStage.setScene(taskDetails);
    }

    /**
     * Show add task page specific to a column
     * @param columnID columnId of the column to show add task
     * @param boardID boardID of card's board
     */
    public void showAddTask(Long columnID, Long boardID) {
        addTaskCtrl.setColumnToAddId(columnID);
        addTaskCtrl.setBoardID(boardID);
        primaryStage.setTitle("Add Task");
        primaryStage.setScene(addTask);
    }

    /**
     * Show edit list page
     * @param c the list which will be changed
     * @param boardID boardID of the board to be in
     */
    public void showEditList(Column c, long boardID) {
        editListCtrl.setBoardID(boardID);
        editListCtrl.setColumnToEdit(c);
        primaryStage.setTitle("Edit Column");
        primaryStage.setScene(editList);
    }
    /**
     * Show the edit board title page
     */
    public void showEditBoardTitle(){
        primaryStage.setTitle("Edit Board Title");
        primaryStage.setScene(editBoardTitle);
    }


    /**
     * Show edit Card Title Page
     * @param cardToShow Card whose Title needs to be edited
     */
    public void showEditCardTitle(Card cardToShow) {
        editCardTitleCtrl.setCardToShow(cardToShow);
        primaryStage.setTitle("Edit Card Title");
        primaryStage.setScene(editCardTitle);
    }

    /**
     * Show edit Card Description Page
     * @param cardToShow Card whose Description needs to be edited
     */
    public void showEditCardDescription(Card cardToShow) {
        editCardDescriptionCtrl.setCardToShow(cardToShow);
        primaryStage.setTitle("Edit Card Description");
        primaryStage.setScene(editCardDescription);
    }

}