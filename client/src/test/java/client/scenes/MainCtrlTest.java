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
/*package client.scenes;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commons.Column;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainCtrlTest {

    private MainCtrl sut;

    private Stage primaryStage;
    private AddListCtrl addListCtrl;
    private Scene addListScene;
    private BoardOverviewCtrl boardOverviewCtrl;
    private Scene boardOverviewScene;
    private ClientConnectCtrl clientConnectCtrl;
    private Scene clientConnectScene;
    private TaskDetailsCtrl taskDetailsCtrl;
    private AddTaskCtrl addTaskCtrl;

    private Scene addTaskScene;
    private TaskManagementCtrl taskManagementCtrl;
    private Scene taskManagementScene;
    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;
    private CreateBoardCtrl createBoardCtrl;
    private Scene createBoardScene;
    private EditListCtrl editListCtrl;
    private Scene editListScene;

    private EditCardDescriptionCtrl editCardDescriptionCtrl;

    private Scene editCardDescriptionScene;

    private EditCardTitleCtrl editCardTitleCtrl;

    private Scene editCardTitleScene;

    @BeforeEach
    public void setup() {
        primaryStage = mock(Stage.class);
        addListCtrl = mock(AddListCtrl.class);
        addListScene = mock(Scene.class);
        boardOverviewCtrl = mock(BoardOverviewCtrl.class);
        boardOverviewScene = mock(Scene.class);
        clientConnectCtrl = mock(ClientConnectCtrl.class);
        clientConnectScene = mock(Scene.class);
        taskDetailsCtrl = mock(TaskDetailsCtrl.class);
        taskManagementScene = mock(Scene.class);
        workspaceCtrl = mock(WorkspaceCtrl.class);
        workspaceScene = mock(Scene.class);
        createBoardCtrl = mock(CreateBoardCtrl.class);
        createBoardScene = mock(Scene.class);
        editListCtrl = mock(EditListCtrl.class);
        editListScene = mock(Scene.class);
        editCardDescriptionScene = mock(Scene.class);
        editCardDescriptionCtrl = mock(EditCardDescriptionCtrl.class);
        editCardTitleCtrl = mock(EditCardTitleCtrl.class);
        editCardTitleScene = mock(Scene.class);

        sut = new MainCtrl();

       sut.initialize(primaryStage,
                new Pair<>(addListCtrl, mock(Parent.class)),
                new Pair<>(boardOverviewCtrl, mock(Parent.class)),
                new Pair<>(clientConnectCtrl, mock(Parent.class)),
                new Pair<>(taskDetailsCtrl, mock(Parent.class)),
                new Pair<>(addTaskCtrl, mock(Parent.class)),
                new Pair<>(taskManagementCtrl, mock(Parent.class)),
                new Pair<>(workspaceCtrl, mock(Parent.class)),
                new Pair<>(createBoardCtrl, mock(Parent.class)),
               new Pair<>(editCardTitleCtrl, mock(Parent.class)),
               new Pair<>(editCardDescriptionCtrl, mock(Parent.class)),
                new Pair<>(editListCtrl, mock(Parent.class))



                );

    }

   /* @Test
    public void writeSomeTests() {

        // sut.initialize(null, null, null);
    }
    @Test
    public void testShowBoardOverview(){

        sut.showBoardOverview();
        verify(primaryStage).setTitle("Board Overview");
        verify(primaryStage).setScene(boardOverviewScene);
    }

  //  @Test
    /*public void testShowWorkspace() {
        sut.showWorkspace();
        verify(primaryStage).setTitle("Workspace");
        verify(primaryStage).setScene(workspaceScene);
    }
}*/