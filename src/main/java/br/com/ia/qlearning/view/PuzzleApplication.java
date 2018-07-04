package br.com.ia.qlearning.view;

import br.com.ia.qlearning.QLearning;
import br.com.ia.qlearning.model.MapaRecompensas;
import br.com.ia.qlearning.model.Transicao;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

public class PuzzleApplication extends Application {

    private static final int FIELD_SIZE_PIXELS = 50;

    private Pane puzzle;

    private QLearning qLearning;

    private MapaRecompensas mapaRecompensas;

    public PuzzleApplication() {
        qLearning = new QLearning();
        mapaRecompensas = qLearning.getTabela().getMapaRecompensas();
        puzzle = buildPuzzle();
    }

    @Override
    public void start(Stage primaryStage) {

        final BorderPane borderPane = new BorderPane();
        borderPane.setCenter(puzzle);

        final Scene scene = new Scene(borderPane, FIELD_SIZE_PIXELS * 10, FIELD_SIZE_PIXELS * 5);
        primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("icon.png")));

        primaryStage.setTitle("QLearning");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("puzzle.css");
        primaryStage.show();

        start();
    }

    private Pane buildPuzzle() {
        final Pane gamePane = new Pane();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 5; y++) {
                final Integer indexAsArray = x * 5 + y;
                final String styleClass = RecompensaStyle.valueOf(mapaRecompensas.get(indexAsArray).name()).getStyleClass();
                final Rectangle rectangle = new Rectangle(FIELD_SIZE_PIXELS * x, FIELD_SIZE_PIXELS * y,
                        FIELD_SIZE_PIXELS, FIELD_SIZE_PIXELS);
                rectangle.getStyleClass().add(styleClass);
                final StackPane stackPane = new StackPane();
                final Text text = new Text();
                text.setText(String.valueOf(indexAsArray));
                text.getStyleClass().add("conteudo-estado");
                final Integer layoutX = FIELD_SIZE_PIXELS * x;
                final Integer layoutY = FIELD_SIZE_PIXELS * y;
                stackPane.setLayoutX(layoutX);
                stackPane.setLayoutY(layoutY);
                stackPane.getChildren().addAll(rectangle, text);
                gamePane.getChildren().add(stackPane);
            }
        }
        return gamePane;
    }

    private void start() {
        new Thread(() -> {
            IntStream.range(0, 200)
                    .forEach(any -> {
                        final LocalDateTime start = LocalDateTime.now();
                        qLearning.episodio();
                        final List<Transicao> caminhoEpisodio = qLearning.getCaminhoEpisodio();
                        caminhoEpisodio.forEach(this::moverPeca);
                        clearPuzzle();
                        System.out.println("Resultados:");
                        System.out.println("Tempo Decorrido >> " +
                                start.until(LocalDateTime.now(), ChronoUnit.SECONDS) + " segundos.");
                        System.out.println("Transições realizadas >> " + caminhoEpisodio.size());
                    });
        }).start();
    }

    private void clearPuzzle() {
        IntStream.range(0, 50)
                .forEach(i -> {
                    final StackPane node = (StackPane) puzzle.getChildren().get(i);
                    final Rectangle rectangle = (Rectangle) node.getChildren().get(0);
                    rectangle.getStyleClass().removeAll("ativo", "visitado");
                });
    }

    private void moverPeca(final Transicao transicao) {
        final StackPane node = (StackPane) puzzle.getChildren().get(transicao.getPosicao());
        final Rectangle rectangle = (Rectangle) node.getChildren().get(0);

        rectangle.getStyleClass().add("ativo");
        delay(100);
        rectangle.getStyleClass().remove("ativo");
        rectangle.getStyleClass().add("visitado");
    }

    private void delay(final Integer delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}