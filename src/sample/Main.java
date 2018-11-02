package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    private double windowWidth;
    private double windowHeight;
    private double boxWidth = 80;
    private double boxHeight = 50;

    private double gamePaneWidth;
    private double gamePaneHeight;
    private double optionsPaneWidth;
    private double optionsPaneHeight;

    private StackPane[] boxes;
    private StackPane[] boxesAlternate;
    private StackPane[] balls;

    private GridPane gridPaneBoard;

    private int numberOfBoxes = 8;
    private int numberOfBalls;

    private boolean alternateFall = true;
    final String IDLE_BUTTON_STYLE = "-fx-padding: 8 15 15 15;"+
        "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
        "-fx-background-radius: 8;"+
        "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"+
        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
        "-fx-font-weight: bold;"+
        "-fx-font-size: 1.1em;" ;

    final String HOVEROVER_BUTTON_STYLE ="-fx-padding: 8 15 15 15;"+
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
            "-fx-background-radius: 8;"+
            " -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;
    final String PRESSED_BUTTON_STYLE = "-fx-padding: 10 15 13 15;"+
            "-fx-background-insets: 2 0 0 0,2 0 3 0, 2 0 4 0, 2 0 5 0;"+
            "-fx-background-radius: 8;"+
            " -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;

    final String HOVEROVER_BUTTON_STYLE_QUIT = "-fx-padding: 8 15 15 15;"+
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
            "-fx-background-radius: 8;"+
            "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #a22312, radial-gradient(center 50% 50%, radius 100%, #a22312, #a21212);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;

    final String IDLE_BUTTON_STYLE_QUIT ="-fx-padding: 8 15 15 15;"+
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
            "-fx-background-radius: 8;"+
            " -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;
    final String PRESSED_BUTTON_STYLE_QUIT = "-fx-padding: 10 15 13 15;"+
            "-fx-background-insets: 2 0 0 0,2 0 3 0, 2 0 4 0, 2 0 5 0;"+
            "-fx-background-radius: 8;"+
            "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #a22312, radial-gradient(center 50% 50%, radius 100%, #a22312, #a21212);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;
    Stage window;
    Scene mainScreen , leaderBoards , changeSkin , pause , mainGame ;
    Button toMainScreen,toLeaderPage,toSkinChangePage, Quit, toStartGame;
    private StackPane playButton;

    private DestroyBlock createBlok(){
        DestroyBlock rect = new DestroyBlock(0, -boxHeight, boxWidth, boxHeight);

        Random rand = new Random();
        int selectColor = rand.nextInt(10);
        switch (selectColor){
            case 0: rect.setFill(Color.BLUEVIOLET);
                break;
            case 1: rect.setFill(Color.AQUA);
                break;
            case 2: rect.setFill(Color.TOMATO);
                break;
            case 3: rect.setFill(Color.PINK);
                break;
            case 4: rect.setFill(Color.ORANGE);
                break;
            case 5: rect.setFill(Color.RED);
                break;
            case 6: rect.setFill(Color.MISTYROSE);
                break;
            case 7: rect.setFill(Color.YELLOW);
                break;
            case 8: rect.setFill(Color.MAGENTA);
                break;
            case 9: rect.setFill(Color.MINTCREAM);
                break;
        }
        rect.setStroke(Color.BLACK);
        return rect;
    }
    private void createBoxes(){
        Random rand = new Random();

        if(this.alternateFall){

            for (int x = 0; x < 2*numberOfBoxes; x++) {
                DestroyBlock rect = createBlok();

                int inactiveBlock = rand.nextInt(10);
                if(inactiveBlock == 0){
                    continue;
                }

                Text text =  new Text(Integer.toString(rect.getBoxValue()));
                text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
                text.setY(-boxHeight);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, text);
                if(x < numberOfBoxes){
                    boxes[x] = stackPane;
                    boxes[x].setTranslateY(-boxHeight);
//                    boxes[x].setLayoutY(-boxHeight);
                    gridPaneBoard.add(boxes[x], x, 0);
                }
                else{
                    boxesAlternate[x-numberOfBoxes] = stackPane;
                    boxesAlternate[x-numberOfBoxes].setTranslateY(-boxHeight);
//                    boxesAlternate[x-numberOfBoxes].setLayoutY(-boxHeight);
                    gridPaneBoard.add(boxesAlternate[x-numberOfBoxes], x-numberOfBoxes, 0);
                }
            }
        }
        else {
            for (int x = 0; x < numberOfBoxes; x++) {
                DestroyBlock rect = createBlok();

                int inactiveBlock = rand.nextInt(25);
                if(inactiveBlock == 0){
                    continue;
                }

                Text text =  new Text(Integer.toString(rect.getBoxValue()));
                text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
                text.setY(-boxHeight);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, text);
                boxes[x] = stackPane;
                boxes[x].setTranslateY(-boxHeight);
//                boxes[x].setLayoutY(-boxHeight);
                gridPaneBoard.add(boxes[x], x, 0);
            }
        }

    }

    private void playgame(){
        gridPaneBoard = new GridPane();
        HBox hBox = new HBox();
     //   hbox.set
//        hBox.setTranslateX(20);
//        hBox.setTranslateY(20);
        hBox.setSpacing(40);
        //window.setResizable(false);
        Scene scene = new Scene(new Group(hBox));
        window.show();
        window.setMinHeight(721);
        window.setMinWidth(1281);
        window.setScene(scene);
        System.out.println("lmao");
        //window.setMaximized(true);
        scene.setFill(Color.DARKGREY);
        window.setTitle("Snek");

        windowWidth = window.getWidth();
        windowHeight = window.getHeight();

        gamePaneWidth = 0.6 * windowWidth - 40;
        gamePaneHeight = windowHeight - 60;
        optionsPaneWidth = 0.4 * windowWidth - 40;
        optionsPaneHeight = windowHeight - 60;

//        numberOfBoxes = (int) Math.floor(gamePaneWidth/boxWidth);
        boxWidth = Math.floor(gamePaneWidth/numberOfBoxes);

        System.out.println(gamePaneHeight + " " + gamePaneWidth);

        SplitPane gamePane = new SplitPane();
        gamePane.setOrientation(Orientation.VERTICAL);
        gamePane.setPrefSize(gamePaneWidth, gamePaneHeight);
        hBox.getChildren().add(gamePane);
        gamePane.getItems().add(gridPaneBoard);

//        gridPaneBoard.setBackground(Background.EMPTY);
        gamePane.setStyle("-fx-background-color: #000000");

        SplitPane optionsPane = new SplitPane();
        optionsPane.setOrientation(Orientation.VERTICAL);
        optionsPane.setPrefSize(optionsPaneWidth, optionsPaneHeight);
        hBox.getChildren().add(optionsPane);



        nextCycle();
    }
    private void createAnimation(){
        Path [] pathBoxes = new Path[boxes.length];
        for(int i=0; i<pathBoxes.length; i++){
            if(boxes[i] == null)
                continue;

            pathBoxes[i] = new Path();

            pathBoxes[i].getElements().add(new MoveTo(boxes[i].getLayoutX() + boxWidth/2, boxes[i].getTranslateY()));
            pathBoxes[i].getElements().add(new LineTo(boxes[i].getLayoutX() + boxWidth/2, gamePaneHeight + boxHeight/2));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setPath(pathBoxes[i]);
            pathTransition.setNode(boxes[i]);
            pathTransition.play();
            if(i == 5)
                pathTransition.setOnFinished(e -> nextCycle());
        }
        if(alternateFall){
            alternateFall = false;
            Path [] pathBoxesAlternate = new Path[boxesAlternate.length];

            for(int i=0; i<pathBoxesAlternate.length; i++){

                if(boxesAlternate[i] == null)
                    continue;

                pathBoxesAlternate[i] = new Path();

                pathBoxesAlternate[i].getElements().add(new MoveTo(boxesAlternate[i].getLayoutX() + boxWidth/2, boxesAlternate[i].getLayoutY() - boxHeight));
                pathBoxesAlternate[i].getElements().add(new LineTo(boxesAlternate[i].getLayoutX() + boxWidth/2, gamePaneHeight + boxHeight/2));
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(3000));
                pathTransition.setPath(pathBoxesAlternate[i]);
                pathTransition.setNode(boxesAlternate[i]);
                pathTransition.setDelay(new Duration(1500));
                pathTransition.play();
            }
        }
    }
    private void boxesFall(){
        createAnimation();
    }


    private void createBalls(){
        Random rand = new Random();

        for(int i=0; i<numberOfBalls; i++){
            int x = rand.nextInt(numberOfBoxes);
            int y = 0;
            while(y == 0){
                y = rand.nextInt(10);
                y *= -1;
            }

            StackPane ball = new StackPane();
            Ball rawBall = new Ball(10);
            rawBall.setFill(Color.PINK);
            Text text = new Text(Integer.toString(rand.nextInt(10)));
            ball.getChildren().addAll(rawBall, text);

            gridPaneBoard.add(ball, x, 0);
            balls[i] = ball;

            balls[i].setTranslateY(boxHeight * (y - boxHeight));
        }
    }

    private void ballsFall(){
        Path [] pathBalls = new Path[balls.length];
        Random rand = new Random();

        for(int i=0; i<pathBalls.length; i++){

            if(boxes[i] == null)
                continue;
            pathBalls[i] = new Path();

            pathBalls[i].getElements().add(new MoveTo(balls[i].getLayoutX() + boxWidth/2, balls[i].getLayoutY() - boxHeight));
            pathBalls[i].getElements().add(new LineTo(balls[i].getLayoutX() + boxWidth/2, gamePaneHeight + boxHeight));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setPath(pathBalls[i]);
            pathTransition.setNode(balls[i]);
            pathTransition.setDelay(new Duration(700 + rand.nextInt(500)));
            pathTransition.play();
//            System.out.println(balls[i].getTranslateY() + "  " + boxes[i].getTranslateY());
        }
    }


    private void nextCycle(){
        boxes = new StackPane[numberOfBoxes];
        boxesAlternate = new StackPane[numberOfBoxes];

        Random rand = new Random();
        int alternate = rand.nextInt(5);
        if(alternate == 0){
            this.alternateFall = true;
        }

        createBoxes();
        boxesFall();

        numberOfBalls = 1 + rand.nextInt(numberOfBoxes/2);
        balls = new StackPane[numberOfBalls];
//
        createBalls();
        ballsFall();
    }
    public void renderMainpage(){

        VBox mainlayout = new VBox();
        Image top = new Image(getClass().getResourceAsStream("top.png"));
        ImageView topp = new ImageView();
        topp.setImage(top);
        //mainlayout.setLayoutX(20);
        //mainlayout.setLayoutY(20);
        mainlayout.setAlignment(Pos.CENTER);
        mainlayout.setMinSize(1280,720);
        mainlayout.setMaxSize(1280,720);
        mainlayout.getChildren().add(topp);
        mainlayout.getChildren().add(playButton);
        mainlayout.getChildren().add(toLeaderPage);
        mainlayout.getChildren().add(toSkinChangePage);
        mainlayout.getChildren().add(Quit);
        mainlayout.setSpacing(75);
        //Image background = new Image(getClass().getResourceAsStream("leaderboardbutton.png"));
        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.png"),1281,720,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        mainlayout.setBackground(new Background(myBI));

        mainScreen = new Scene(mainlayout,1280,720);
        window.setTitle("SnekvsBlok");
        window.setScene(mainScreen);
        window.show();
    }

    public void exiting(){
        window.close();
    }

    public void renderLeaderboard(){
        Pane leaderboardlayout = new Pane();
        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.png"),1281,720,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        leaderboardlayout.setBackground(new Background(myBI));
        leaderboardlayout.setTranslateX(0);
        leaderboardlayout.setTranslateY(0);
        toMainScreen.setLayoutX(8);
        toMainScreen.setLayoutY(8);
        ListView<String> board = new ListView<String>();

        String[] str = new String[15];
        str[0] = "pranaay     420";
        str[1] = "aniket      69";
        str[2] = "amoghe      35";
        str[3] = "pranaay     420";
        str[4] = "aniket      69";
        str[5] = "amoghe      35";
        str[6] = "pranaay     420";
        str[7] = "aniket      69";
        str[8] = "amoghe      35";
        str[9] = "pranaay     420";
        str[10] = "aniket      69";
        str[11] = "amoghe      35";
        str[12] = "pranaay     420";
        str[13] = "aniket      69";
        str[14] = "amoghe      35";


        leaderboardlayout.getStylesheets().add(getClass().getResource("lmao.css").toExternalForm());
        board.setLayoutX(510);
        board.setLayoutY(120);
        board.setEditable(false);
       // board.set
        board.getItems().addAll(str);
        //board.



        leaderboardlayout.getChildren().add(toMainScreen);
        leaderboardlayout.getChildren().add(board);
        leaderBoards = new Scene(leaderboardlayout,1280,720);
       // System.out.println(toMainScreen.getTranslateX()+" "+toMainScreen.getTranslateY());
        window.setTitle("Leaderboards");
        window.setScene(leaderBoards);
        window.show();
    }




    private ImageView createImageView(final File imageFile)throws Exception {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitHeight(window.getHeight() - 10);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(window.getWidth());
                                newStage.setHeight(window.getHeight());
                                newStage.setTitle(imageFile.getName());
                                Scene scene = new Scene(borderPane,Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
    public void renderChangeSkin()throws Exception{

        //stage = window;

        ScrollPane root = new ScrollPane();
        TilePane tile = new TilePane();
        toMainScreen.setLayoutX(8.0);
        toMainScreen.setLayoutY(8.0);

        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.png"),1281,720,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        tile.getChildren().add(toMainScreen);
       root.setBackground(new Background(myBI));
       //tile.setBackground(Background.EMPTY);
        tile.setStyle("-fx-background-color: transparent;");
        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);

        String path = "/home/pranaaysaini/Desktop/creepy_pictures";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            tile.getChildren().addAll(imageView);
        }


        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        root.setFitToWidth(true);
        root.setContent(tile);

//        window.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
//        window.setHeight(Screen.getPrimary().getVisualBounds()
//                .getHeight());
        Scene scene = new Scene(root,1280,720);
        window.setScene(scene);
        window.show();
//        Pane changeskinlayout = new Pane();
//
//        //changeskinlayout.setAlignment(Pos.CENTER);
//        toMainScreen.setTranslateX(8.0);
//        toMainScreen.setTranslateY(8.0);
//
//        changeskinlayout.getChildren().add(toMainScreen);
//        changeSkin = new Scene(changeskinlayout ,1280,720);
//
//        window.setTitle("Change Skin");
//        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.png"),1281,720,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//
//        changeskinlayout.setBackground(new Background(myBI));
//
//
//
//
//
//
//
//        window.setScene(changeSkin);
//        window.show();
    }






    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage ;
        //window.setTitle("Hello World");
//        window.setResizable(false);
//        window.setMaximized(true);
        toMainScreen = new Button();
        toMainScreen.setText("Back to main page");

        ///////////////////////////////////
        toLeaderPage = new Button();
        toLeaderPage.setText("Leaderboards");
        toLeaderPage.setStyle(IDLE_BUTTON_STYLE);


        ///////////////////////////////////
        toSkinChangePage = new Button();
        toSkinChangePage.setText("Change Skin");
        toSkinChangePage.setStyle(IDLE_BUTTON_STYLE);
        //////////////////////////////////

        toStartGame = new Button();
        toStartGame.setStyle(IDLE_BUTTON_STYLE);
        playButton = new StackPane();
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
                0.0, 0.0,
                20.0, 10.0,
                0.0, 20.0 });
        playButton.getChildren().addAll(toStartGame, polygon);
//        toStartGame.setText("Start");
        //Image background = new Image(getClass().getResourceAsStream("finalplaybutton.png"));
        //BackgroundImage myBIi= new BackgroundImage(new Image(getClass().getResourceAsStream("finalplaybutton.png"),160,160,true,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        //toStartGame.setBackground(new Background(myBIi));
        //toStartGame.setGraphic(new ImageView(imagest));
        double r=80;
        toStartGame.setShape(new Circle(r));
        toStartGame.setMinSize(2*r, 2*r);
        toStartGame.setMaxSize(2*r, 2*r);

        //toStartGame.setMaxSize(10,10);

        ////////////////////////////exit button
        Quit = new Button();
        Quit.setText("Quit");
        Quit.setStyle(IDLE_BUTTON_STYLE_QUIT);
        toMainScreen.setStyle(IDLE_BUTTON_STYLE);




        this.renderMainpage();

/////////////////////////////////////////////////////////////////////BACK TO MAIN SCREEN BUTTON
        toMainScreen.setOnAction(e -> {
            this.renderMainpage();

        });
        toMainScreen.setOnMousePressed(e -> {
            toMainScreen.setStyle(PRESSED_BUTTON_STYLE);
        });
        toMainScreen.setOnMouseEntered(e -> toMainScreen.setStyle(HOVEROVER_BUTTON_STYLE));
        toMainScreen.setOnMouseExited(e -> toMainScreen.setStyle(IDLE_BUTTON_STYLE));


///////////////////////////////////////////////////////////////////start game button

        toStartGame.setOnMousePressed(e -> {
            toStartGame.setStyle(PRESSED_BUTTON_STYLE);
        });
        toStartGame.setOnAction((e->{
            this.playgame();
        }));
        toStartGame.setOnMouseEntered(e -> toStartGame.setStyle(HOVEROVER_BUTTON_STYLE));
        toStartGame.setOnMouseExited(e -> toStartGame.setStyle(IDLE_BUTTON_STYLE));




//////////////////////////////////////////////////////////////////////leaderpage button
        toLeaderPage.setOnAction(e -> {

            this.renderLeaderboard();

        });
        toLeaderPage.setOnMousePressed(e -> {
            toLeaderPage.setStyle(PRESSED_BUTTON_STYLE);
        });
        toLeaderPage.setOnMouseEntered(e -> toLeaderPage.setStyle(HOVEROVER_BUTTON_STYLE));
        toLeaderPage.setOnMouseExited(e -> toLeaderPage.setStyle(IDLE_BUTTON_STYLE));
/////////////////////////////////////////////////////////////////////////////quit button

        Quit.setOnAction(e -> {

            this.exiting();

        });
        Quit.setOnMousePressed(e -> {
            Quit.setStyle(PRESSED_BUTTON_STYLE_QUIT);
        });
        Quit.setOnMouseEntered(e -> Quit.setStyle(HOVEROVER_BUTTON_STYLE_QUIT));
        Quit.setOnMouseExited(e -> Quit.setStyle(IDLE_BUTTON_STYLE_QUIT));

//////////////////////////////////////////////////////////////////////change skin button
        toSkinChangePage.setOnAction(event -> {
            try {
                this.renderChangeSkin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        toSkinChangePage.setOnMousePressed(event -> {
            toSkinChangePage.setStyle(PRESSED_BUTTON_STYLE);
        });
        toSkinChangePage.setOnMouseEntered(e -> toSkinChangePage.setStyle(HOVEROVER_BUTTON_STYLE));
        toSkinChangePage.setOnMouseExited(e -> toSkinChangePage.setStyle(IDLE_BUTTON_STYLE));


    }

    public static void main(String[] args) {
        launch(args);
    }
}
