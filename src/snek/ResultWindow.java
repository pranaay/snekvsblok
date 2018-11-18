package snek;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains definitions for ResultWindow. How the score is managed.
 */
public class ResultWindow extends Application {

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

    private int score;

    private Button restartButton;
    private Button homeButton;
    private Button quitButton;
    private Button userSaveButton;

    private Label scoreLabel;
    private Label scoreLabelLabel;
    private Label userNameLabel;

    private TextField userName;

    private Stage window;

    private PlayGame game;
    private Main home;

	/**
	 * Set the score of the player. Invoked when game ends.
	 * @param score Score of the player
	 */
	public void setScore(int score) {
        this.score = score;
    }

	/**
	 * A method to get the top 10 people from the save file, and current save.
	 * @param date Current date
	 * @return List of type Score, containing details of top 10 scores.
	 * @throws IOException
	 */
    private ArrayList<Score> checkIfInTopTen(String date) throws IOException{
    	ArrayList<Score> scores = new ArrayList<>();
    	File file = new File("savedata.txt");
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null){
				if(st.equalsIgnoreCase("\n") || st.equalsIgnoreCase(""))
					continue;
				String[] data = st.split("\t");
				Score tempScore = new Score(Integer.parseInt(data[0]), data[1], data[2]);
				scores.add(tempScore);
			}

			Collections.sort(scores, new ScoreComparator());
			Collections.reverse(scores);

			if(scores.size() >= 10 && scores.get(scores.size()-1).getScore() > score)
				return scores;
			else if(scores.size() == 10){
				scores.remove(9);
			}
			scores.add(new Score(score, userName.getText(), date));
			Collections.sort(scores, new ScoreComparator());
			Collections.reverse(scores);
			return scores;
		} catch (FileNotFoundException e){
			scores.add(new Score(score, userName.getText(), date));
			return scores;
		}
	}

	/**
	 * Method to write and save details of top 10 scores.
	 */
    private void saveDetails(){
    	int day, month, year;

    	day = LocalDateTime.now().getDayOfMonth();
    	month = LocalDateTime.now().getMonthValue();
    	year = LocalDateTime.now().getYear();

    	String date = day + "/" + month + "/" + year;
    	ArrayList<Score> scores;

		try {
			scores = checkIfInTopTen(date);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		File saveFile = new File("savedata.txt");

		try{
			FileWriter fileWriter = new FileWriter(saveFile.getAbsoluteFile());
			for(int i=0; i<scores.size(); i++){
				fileWriter.write((scores.get(i).toString() + System.lineSeparator()));
			}
			fileWriter.close();
		} catch (Exception e){}
	}

	/**
	 * Starts the main scene.
	 * @param primaryStage Window where to show details
	 * @throws Exception
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {

		String musicFile = "roblox.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.seek(Duration.millis(1000));
        mediaPlayer.play();

        window = primaryStage;
        game = new PlayGame();

        restartButton = new Button();
        homeButton = new Button();
        quitButton = new Button();
        userSaveButton = new Button();

        userNameLabel = new Label("Please enter your name:");
		userNameLabel.setFont(Font.font("Roboto", 15));
		userNameLabel.setStyle("-fx-font-weight: bold");

		userName = new TextField();
		// Set Hint
		userName.setFocusTraversable(false);
		userName.setPromptText("User Name");
		//Set Width
		userName.setMaxWidth(200);

		userSaveButton.setText("SAVE");

		GridPane highScoreGroup = new GridPane();
		highScoreGroup.setHgap(20);
		highScoreGroup.setAlignment(Pos.CENTER);
		highScoreGroup.add(userNameLabel, 0, 0);
		highScoreGroup.add(userName, 1, 0);
		highScoreGroup.add(userSaveButton, 2, 0);

        scoreLabel = new Label(String.valueOf(score));
        scoreLabel.setFont(Font.font("Roboto", 20));

        scoreLabelLabel = new Label("SCORE");
        scoreLabelLabel.setFont(Font.font("Roboto", 32));


        VBox mainlayout = new VBox();

        Image top = new Image(getClass().getResourceAsStream("top.png"));
        ImageView topp = new ImageView();
        topp.setImage(top);

        mainlayout.setAlignment(Pos.CENTER);
        mainlayout.getChildren().add(topp);
        mainlayout.getChildren().add(scoreLabelLabel);
        mainlayout.getChildren().add(scoreLabel);
        mainlayout.getChildren().add(highScoreGroup);
        mainlayout.getChildren().addAll(restartButton, homeButton, quitButton);

        mainlayout.setSpacing(50);

        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResource("background.png").toExternalForm(),1281,720,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainlayout.setBackground(new Background(myBI));

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene mainScreen = new Scene(mainlayout);

		////////////////////////////////////////////////////////////////
		userSaveButton.setOnMousePressed(e -> {
			userSaveButton.setStyle(PRESSED_BUTTON_STYLE);
		});
		userSaveButton.setOnAction((e->{
			userSaveButton.setText("SAVED");
			userSaveButton.setDisable(true);
			userName.setDisable(true);

			// SAVE THE SHIT OUT OF IT!
			saveDetails();
		}));

		userSaveButton.setOnMouseEntered(e -> userSaveButton.setStyle(HOVEROVER_BUTTON_STYLE));
		userSaveButton.setOnMouseExited(e -> userSaveButton.setStyle(IDLE_BUTTON_STYLE));
		userSaveButton.setStyle(IDLE_BUTTON_STYLE);

        ////////////////////////////////////////////////////////////////
        restartButton.setOnMousePressed(e -> {
            restartButton.setStyle(PRESSED_BUTTON_STYLE);
        });

        restartButton.setOnAction((e->{
            game = new PlayGame();
            try {
                game.start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        restartButton.setOnMouseEntered(e -> restartButton.setStyle(HOVEROVER_BUTTON_STYLE));
        restartButton.setOnMouseExited(e -> restartButton.setStyle(IDLE_BUTTON_STYLE));
        restartButton.setStyle(IDLE_BUTTON_STYLE);

        ////////////////////////////////////////////////////////////////
        homeButton.setOnMousePressed(e -> {
            homeButton.setStyle(PRESSED_BUTTON_STYLE);
        });

        homeButton.setOnAction((e->{
            home = new Main();
            try {
                home.start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(HOVEROVER_BUTTON_STYLE));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(IDLE_BUTTON_STYLE));
        homeButton.setStyle(IDLE_BUTTON_STYLE);

        ///////////////////////////////////////////////////////////////
        quitButton.setOnMousePressed(e -> {
            quitButton.setStyle(PRESSED_BUTTON_STYLE_QUIT);
        });

        quitButton.setOnAction((e->{
            game = new PlayGame();
            try {
                Platform.exit();
                window.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

        quitButton.setOnMouseEntered(e -> quitButton.setStyle(HOVEROVER_BUTTON_STYLE_QUIT));
        quitButton.setOnMouseExited(e -> quitButton.setStyle(IDLE_BUTTON_STYLE_QUIT));
        quitButton.setStyle(IDLE_BUTTON_STYLE_QUIT);

        ///////////////////////////////////////////////////////////////

        homeButton.setText("HOME");
        quitButton.setText("QUIT");
        restartButton.setText("RESTART");

        homeButton.setPrefHeight(30);
        quitButton.setPrefHeight(30);
        restartButton.setPrefHeight(30);
        homeButton.setPrefWidth(200);
        quitButton.setPrefWidth(200);
        restartButton.setPrefWidth(200);


        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());

        window.setTitle("SnekvsBlok");
        window.setScene(mainScreen);
//        window.setMaximized(true);
//        window.setResizable(false);
        window.show();
    }
}
