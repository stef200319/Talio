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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);


    /**
     * @param args
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }


    /**
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws IOException
     */
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void start(Stage primaryStage) throws IOException {

        var add = FXML.load(AddListCtrl.class, "client", "scenes", "AddList.fxml");
        var boardOverview = FXML.load(BoardOverviewCtrl.class, "client", "scenes", "BoardOverview.fxml");
        var clientConnect = FXML.load(ClientConnectCtrl.class, "client", "scenes", "ClientConnect.fxml");
        var taskDetails = FXML.load(TaskDetailsCtrl.class, "client", "scenes", "TaskDetails.fxml");
        var addTask = FXML.load(AddTaskCtrl.class, "client", "scenes", "AddTask.fxml");
        var taskManagement = FXML.load(TaskManagementCtrl.class, "client", "scenes", "TagManagement.fxml");
        var workspace = FXML.load(WorkspaceCtrl.class, "client", "scenes", "Workspace.fxml");
        var createBoard = FXML.load(CreateBoardCtrl.class, "client", "scenes", "CreateBoard.fxml");
        var editCardTitle = FXML.load(EditCardTitleCtrl.class, "client", "scenes", "EditCardTitle.fxml");
        var editCardDescription = FXML.load(EditCardDescriptionCtrl.class, "client", "scenes", "EditCardDescription.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, add, boardOverview, clientConnect, taskDetails,
                addTask, taskManagement, workspace,createBoard, editCardTitle, editCardDescription);
    }
}