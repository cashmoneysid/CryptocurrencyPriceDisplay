import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CryptocurrencyPriceDisplayApp extends Application {

    // Replace this URL with the cryptocurrency API endpoint you choose.
    private static final String CRYPTO_API_URL = "https://api.coingecko.com/api/v3/coins/markets";

    private CryptocurrencyPriceFetcher priceFetcher;
    private VBox root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cryptocurrency Price Display");

        // Create UI components
        root = new VBox(10);
        ListView<String> cryptoList = new ListView<>();
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Label priceLabel = new Label("Selected Cryptocurrency Prices:");

        // Add components to the layout
        root.getChildren().addAll(cryptoList, startButton, stopButton, priceLabel);

        // Set up event handlers
        startButton.setOnAction(e -> startFetchingPrices(cryptoList));
        stopButton.setOnAction(e -> stopFetchingPrices());

        // Set up the scene and show the stage
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startFetchingPrices(ListView<String> cryptoList) {
        if (priceFetcher != null) {
            priceFetcher.stopFetching();
        }

        priceFetcher = new CryptocurrencyPriceFetcher(cryptoList);
        priceFetcher.startFetching();
    }

    private void stopFetchingPrices() {
        if (priceFetcher != null) {
            priceFetcher.stopFetching();
        }
    }

    public static class CryptocurrencyPriceFetcher extends Thread {
        private ListView<String> cryptoList;
        private volatile boolean running;

        public CryptocurrencyPriceFetcher(ListView<String> cryptoList) {
            this.cryptoList = cryptoList;
        }

        public void startFetching() {
            running = true;
            start();
        }

        public void stopFetching() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                // Fetch cryptocurrency prices from the API and update the list
                Platform.runLater(() -> {
                    // Replace this code with actual API fetching and data update logic
                    // Example: Fetch prices from the API and update the ListView
                    cryptoList.getItems().clear();
                    cryptoList.getItems().addAll("BTC: $50,000", "ETH: $3,000", "XRP: $1.00");
                });

                try {
                    // Set the refresh rate here (e.g., 5000 ms for 5 seconds)
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Handle the interruption gracefully if necessary
                }
            }
        }
    }
}
