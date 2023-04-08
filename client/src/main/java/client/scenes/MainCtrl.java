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

import commons.Board;
import commons.Card;

import commons.Column;
import commons.Subtask;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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

    private ConfirmDeleteColumnCtrl confirmDeleteColumnCtrl;
    private Scene confirmDeleteColumn;

    private ConfirmDeleteBoardCtrl confirmDeleteBoardCtrl;
    private Scene confirmDeleteBoard;

    private AddSubtaskCtrl addSubtaskCtrl;
    private Scene addSubtask;

    private HelpCtrl helpCtrl;
    private Scene help;

    private Scene previousScene;
    private String title;


    private CustomizeCardCtrl customizeCardCtrl;
    private Scene customizeCard;

    private CustomizeListCtrl customizeListCtrl;
    private Scene customizeList;

    private CustomizeBoardCtrl customizeBoardCtrl;
    private Scene customizeBoard;
    private EditCardTagsBoardCtrl editCardTagsBoardCtrl;
    private Scene editCardTagsBoard;

    private AddCardTagsToCardCtrl addCardTagsToCardCtrl;
    private Scene addCardTagsToCard;

    private JoinBoardByKeyCtrl joinBoardByKeyCtrl;

    private Scene joinBoardByKey;


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
     * @param viewSubtask
     * @param customizeCard
     * @param customizeList
     * @param customizeBoard
     * @param editSubtaskTitle
     * @param editBoardTitle
     * @param confirmDeleteColumn
     * @param confirmDeleteBoard
     * @param addSubtask
     * @param help
     * @param editCardTagsBoard
     * @param addCardTagsToCard
     * @param joinBoardKey
     */
    @SuppressWarnings({"checkstyle:ParameterNumber", "checkstyle:MethodLength"})
    public void initialize(Stage primaryStage,
                           Pair<AddListCtrl, Parent> add, Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<ClientConnectCtrl, Parent> clientConnect, Pair<TaskDetailsCtrl,
            Parent> taskDetails,
                           Pair<AddTaskCtrl, Parent> addTask, Pair<TaskManagementCtrl, Parent> taskManagement,
                           Pair<WorkspaceCtrl, Parent> workspace, Pair<CreateBoardCtrl, Parent> createBoard,
                           Pair<EditCardTitleCtrl, Parent> editCardTitle,
                           Pair<EditCardDescriptionCtrl, Parent> editCardDescription, Pair<EditListCtrl,
                           Parent> editList, Pair<EditBoardTitleCtrl, Parent> editBoardTitle,
                           Pair<ViewSubtaskCtrl, Parent> viewSubtask,
                           Pair<CustomizeCardCtrl, Parent> customizeCard,
                           Pair<CustomizeListCtrl, Parent> customizeList,
                           Pair<CustomizeBoardCtrl, Parent> customizeBoard,
                           Pair<EditSubtaskTitleCtrl, Parent> editSubtaskTitle,
                           Pair<ConfirmDeleteColumnCtrl, Parent> confirmDeleteColumn,
                           Pair<ConfirmDeleteBoardCtrl, Parent> confirmDeleteBoard,
                           Pair<HelpCtrl, Parent> help,
                           Pair<AddSubtaskCtrl, Parent> addSubtask,
                           Pair<EditCardTagsBoardCtrl, Parent> editCardTagsBoard,
                           Pair<AddCardTagsToCardCtrl, Parent> addCardTagsToCard,
                           Pair<JoinBoardByKeyCtrl, Parent> joinBoardKey) {
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

        this.taskDetails.setOnKeyPressed(getKeyboardShortcuts());

        this.addTask.setOnKeyPressed(getKeyboardShortcuts());

        this.editBoardTitleCtrl = editBoardTitle.getKey();
        this.editBoardTitle = new Scene(editBoardTitle.getValue());

        this.viewSubtaskCtrl = viewSubtask.getKey();
        this.viewSubtask = new Scene(viewSubtask.getValue());

        this.editSubtaskTitleCtrl = editSubtaskTitle.getKey();
        this.editSubtaskTitle = new Scene(editSubtaskTitle.getValue());

        this.helpCtrl = help.getKey();
        this.help = new Scene(help.getValue());

        this.joinBoardByKeyCtrl = joinBoardKey.getKey();
        this.joinBoardByKey = new Scene(joinBoardKey.getValue());

        this.help.setOnKeyPressed(helpCtrl.getBackToPreviousScene());
        this.joinBoardByKey.setOnKeyPressed(getKeyboardShortcuts());
        this.boardOverview.setOnKeyPressed(getKeyboardShortcuts());
        this.clientConnect.setOnKeyPressed(getKeyboardShortcuts());
        this.addTask.setOnKeyPressed(getKeyboardShortcuts());
        this.addList.setOnKeyPressed(getKeyboardShortcuts());
        this.createBoard.setOnKeyPressed(getKeyboardShortcuts());
        this.editBoardTitle.setOnKeyPressed(getKeyboardShortcuts());
        this.editCardDescription.setOnKeyPressed(getKeyboardShortcuts());
        this.editCardTitle.setOnKeyPressed(getKeyboardShortcuts());
        this.editList.setOnKeyPressed(getKeyboardShortcuts());
        this.editSubtaskTitle.setOnKeyPressed(getKeyboardShortcuts());
        this.taskDetails.setOnKeyPressed(getKeyboardShortcuts());
        this.taskManagement.setOnKeyPressed(getKeyboardShortcuts());
        this.viewSubtask.setOnKeyPressed(getKeyboardShortcuts());
        this.workspace.setOnKeyPressed(getKeyboardShortcuts());

        this.customizeCardCtrl = customizeCard.getKey();
        this.customizeCard = new Scene(customizeCard.getValue());

        this.customizeListCtrl = customizeList.getKey();
        this.customizeList = new Scene(customizeList.getValue());

        this.customizeBoardCtrl = customizeBoard.getKey();
        this.customizeBoard= new Scene(customizeBoard.getValue());


        this.confirmDeleteColumnCtrl = confirmDeleteColumn.getKey();
        this.confirmDeleteColumn = new Scene(confirmDeleteColumn.getValue());

        this.confirmDeleteBoardCtrl = confirmDeleteBoard.getKey();
        this.confirmDeleteBoard = new Scene(confirmDeleteBoard.getValue());

        this.editCardTagsBoardCtrl = editCardTagsBoard.getKey();
        this.editCardTagsBoard = new Scene(editCardTagsBoard.getValue());

        this.addCardTagsToCardCtrl = addCardTagsToCard.getKey();
        this.addCardTagsToCard = new Scene(addCardTagsToCard.getValue());
        this.addSubtaskCtrl = addSubtask.getKey();
        this.addSubtask = new Scene(addSubtask.getValue());


        showClientConnect();
        primaryStage.show();

        this.previousScene = primaryStage.getScene();
    }


    /**
     * show the overview
     *
     */
    public void showOverview() {
        primaryStage.setTitle("Board: Overview");
        primaryStage.setScene(boardOverview);
    }

    /**
     * show workspace page
     */
    public void showWorkspace() {
        primaryStage.setTitle("Workspace");
        workspaceCtrl.refresh();
        primaryStage.setScene(workspace);
    }

    /**
     * show task management page
     */
    public void showTaskManagement() {
        primaryStage.setTitle("Task management");
        primaryStage.setScene(taskManagement);
    }

    /**
     * Show add list page
     *
     * @param boardID boardID of list's board
     */
    public void showListAdd(Long boardID) {
        addListCtrl.setBoardToAddId(boardID);
        primaryStage.setTitle("Adding List");
        primaryStage.setScene(addList);
    }

    /**
     * Show all the boards
     *
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
     *
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
     *
     * @param card Card whose details have to be shown
     */
    public void showTaskDetails(Card card) {
        taskDetailsCtrl.setCardToShow(card);
        taskDetailsCtrl.refresh();
        primaryStage.setTitle("Task Details");
        primaryStage.setScene(taskDetails);
    }

    /**
     * Show the task details
     */
    public void showDetailOfTask() {
        primaryStage.setTitle("Task Details");
        primaryStage.setScene(taskDetails);
    }

    /**
     * Show add task page specific to a column
     *
     * @param columnID columnId of the column to show add task
     * @param boardID  boardID of card's board
     */
    public void showAddTask(Long columnID, Long boardID) {
        addTaskCtrl.setColumnToAddId(columnID);
        addTaskCtrl.setBoardID(boardID);
        primaryStage.setTitle("Add Task");
        primaryStage.setScene(addTask);
    }

    /**
     * Show edit list page
     *
     * @param c       the list which will be changed
     * @param boardID boardID of the board to be in
     */
    public void showEditList(Column c, long boardID) {
        editListCtrl.setBoardID(boardID);
        editListCtrl.setColumnToEdit(c);
        primaryStage.setTitle("Edit Column");
        primaryStage.setScene(editList);
    }

    /**
     * Show customize list page
     *
     * @param c       the list which will be changed
     * @param boardID boardID of the board to be in
     */
    public void showCustomizeList(Column c, long boardID) {
        customizeListCtrl.setBoardID(boardID);
        customizeListCtrl.setColumnToShow(c);
        primaryStage.setTitle("Edit Column");
        primaryStage.setScene(customizeList);
    }

    /**
     * Show the edit board title page
     *
     * @param boardID boardID of the board
     */
    public void showEditBoardTitle(long boardID) {
        editBoardTitleCtrl.setBoardToEditID(boardID);
        primaryStage.setTitle("Edit Board Title");
        primaryStage.setScene(editBoardTitle);
    }

    /**
     * Show customize board page
     *
     * @param boardID the board which will be changed
     */
    public void showCustomizeBoard(long boardID) {
        customizeBoardCtrl.setBoardToShow(boardID);
        primaryStage.setTitle("Edit Board");
        primaryStage.setScene(customizeBoard);
    }


    /**
     * Show edit Card Title Page
     *
     * @param cardToShow Card whose Title needs to be edited
     */
    public void showEditCardTitle(Card cardToShow) {
        editCardTitleCtrl.setCardToShow(cardToShow);
        primaryStage.setTitle("Edit Card Title");
        primaryStage.setScene(editCardTitle);
    }

    /**
     * Show edit Card Description Page
     *
     * @param cardToShow Card whose Description needs to be edited
     */
    public void showEditCardDescription(Card cardToShow) {
        editCardDescriptionCtrl.setCardToShow(cardToShow);
        primaryStage.setTitle("Edit Card Description");
        primaryStage.setScene(editCardDescription);
    }


    /**
     * Show edit Card Customization Page
     *
     * @param cardToShow Card whose Customization needs to be edited
     */
    public void showCustomizeCard(Card cardToShow) {
        customizeCardCtrl.setCardToShow(cardToShow);
        primaryStage.setTitle("Customize Card");
        primaryStage.setScene(customizeCard);
    }

    /**

     * private event handler for a key event that listens
     *       for the SHIFT+/, ESCAPE  keys to be pressed
     * when the "SHIFT+/" keys are pressed, the method setPreviousSceneAndTitle()
     *       to save the previous scene and scene title values and then showHelpScreen() method
     *       is called to show the help screen
     * when the ESCAPE key is pressed and the user is in the Task Details scene, the method showBoardOverview()
     *       from TaskDetailsCtrl is called to go back to the
     *       board overview
     */
    private EventHandler<KeyEvent> keyboardShortcuts = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.isShiftDown() && event.getCode()== KeyCode.SLASH)
            {
                setPreviousSceneAndTitle();
                showHelpScreen();

            }
            if(event.getCode()==KeyCode.ESCAPE && getCurrentSceneTitle().equals("Task Details"))
            {

                boardOverviewCtrl.setHighlightedByKey(false);
                HBox hbox = boardOverviewCtrl.getHighlightedTask();
                boardOverviewCtrl.setHighlightedTask(hbox);
                taskDetailsCtrl.showBoardOverview();

            }
        }
    };

    /**
     * @return the keyboardShortcuts event handler
     */

    public EventHandler<KeyEvent> getKeyboardShortcuts()
    {
        return keyboardShortcuts;
    }


    /**
     * show the help screen
     */

    public void showHelpScreen()
    {
        primaryStage.setTitle("Help");
        primaryStage.setScene(help);
    }

    /**
     * show the previous screen
     */
    public void showPreviousScreen()
    {

        primaryStage.setTitle(getPreviousSceneTitle());
        primaryStage.setScene(getPreviousScene());

    }

    /**
     * method that
     * sets the previous scene value by calling the methods that
     * set the previous scene
     */
    public void setPreviousSceneAndTitle()
    {

        setPreviousScene(getCurrentScene());
        setPreviousSceneTitle(getCurrentSceneTitle());

    }

    /**
     * method that sets the previous scene value to the current one
     * @param scene the current scene
     */
    public void setPreviousScene(Scene scene)
    {
        this.previousScene = scene;
    }

    /**
     * method that sets the previous scene title to the current scene's title
     * @param title the current scene title
     */
    public void setPreviousSceneTitle(String title)
    {
        this.title = title;
    }

    /**
     * method that returns the current scene
     * @return the current scene
     */

    public Scene getCurrentScene()
    {
        return primaryStage.getScene();
    }

    /**
     * method that returns the current scene title
     * @return the current scene title
     */

    public String getCurrentSceneTitle()
    {
        return primaryStage.getTitle();
    }

    /**
     * method that returns the previous scene
     * @return the previous scene
     */
    public Scene getPreviousScene()
    {
        return this.previousScene;
    }

    /**
     * method that returns the previous scene title
     * @return the previous scene title
     */

    public String getPreviousSceneTitle()
    {
        return this.title;
    }



    /**
     * Shows confirm delete column page
     *
     * @param c Column which would be deleted
     */
    public void showConfirmDeleteColumn(Column c) {
        confirmDeleteColumnCtrl.setColumnToDelete(c);
        primaryStage.setTitle("Confirm Delete");
        primaryStage.setScene(confirmDeleteColumn);
    }

    /**
     * Shows confirm delete board page
     *
     * @param b Board which would be deleted
     */
    public void showConfirmDeleteBoard(Board b) {
        confirmDeleteBoardCtrl.setBoardToDelete(b);
        primaryStage.setTitle("Confirm Delete");
        primaryStage.setScene(confirmDeleteBoard);
    }

    /**
     * method that  copies the id of the board in the clipboard
     * @param boardID the id of the board whose key we want to copy
     */
    public void copyCode(long boardID) {

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(String.valueOf(boardID));
        content.putHtml(String.valueOf(boardID));
        clipboard.setContent(content);
    }



    /**
     * Shows the editCardTagsBoard scene
     * @param boardId
     */
    public void showEditCardTagsBoard(Long boardId) {
        editCardTagsBoardCtrl.setBoardId(boardId);
        primaryStage.setTitle("Edit Card Tags");
        primaryStage.setScene(editCardTagsBoard);
    }

    /**
     * method that shows the join board by key screen
     * @param boardId the id of the current board
     */
    public void showJoinBoard(Long boardId){

        primaryStage.setTitle("Join board by key");
        this.primaryStage.setScene(joinBoardByKey);
        joinBoardByKeyCtrl.setPrevBoardId(boardId);
    }
    /**
     * shows the addCardTagsToCard scene
     * @param card
     */
    public void showAddCardTagsToCard(Card card) {
        addCardTagsToCardCtrl.setCard(card);
        addCardTagsToCardCtrl.refresh();
        primaryStage.setTitle("Add Card Tags to Card");
        primaryStage.setScene(addCardTagsToCard);
    }

    /**
     * Shows the view subtask page
     * @param c Card where to get the subtasks from
     */
    public void showViewSubtask(Card c) {
        viewSubtaskCtrl.setCardToShow(c);
        viewSubtaskCtrl.refresh();
        primaryStage.setTitle("Edit Subtasks");
        primaryStage.setScene(viewSubtask);
    }

    /**
     * Shows the add subtask page
     * @param c Card where to add the new subtask
     */
    public void showAddSubtask(Card c) {
        addSubtaskCtrl.setCard(c);
        primaryStage.setTitle("Add Subtask");
        primaryStage.setScene(addSubtask);
    }

    /**
     * Shows the edit subtask page
     * @param c Card where the subtask is
     * @param s Subtask which will be edited
     */
    public void showEditSubtaskTitle(Card c, Subtask s) {
        editSubtaskTitleCtrl.setCardToShow(c);
        editSubtaskTitleCtrl.setSubtaskToShow(s);
        primaryStage.setTitle("Edit Subtask");
        primaryStage.setScene(editSubtaskTitle);
    }

}

