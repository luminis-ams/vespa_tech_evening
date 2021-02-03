package eu.luminis.ecommdemo.vespa;

import eu.luminis.ecommdemo.vespa.json.ImmutableProduct;
import eu.luminis.ecommdemo.vespa.json.Product;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    private final Logger logger = Logger.getLogger(Runner.class.getName());

    BlockingQueue<Product> queue;
    VespaDataFeeder dataFeeder;

    public Runner() {
        queue = new LinkedBlockingQueue<>();

    }

    private boolean isConnection200(URL url) {
        try {
            return ((HttpURLConnection) url.openConnection()).getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }

    }

    private void waitForVespa() {
        int attempts = 0;
        URL vespa = null;
        try {
            vespa = new URL("http://localhost:8080/ApplicationStatus");
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            System.exit(1);
        }
        boolean success = isConnection200(vespa);
        while (!success) {
            logger.info("Unable to connect to Vespa, trying again in 20 seconds");
            attempts++;
            if (attempts >= 15) {
                logger.log(Level.SEVERE, "Failure. Cannot establish connection to Vespa");
                System.exit(1);
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
                Thread.currentThread().interrupt();
            }
            success = isConnection200(vespa);
        }
    }

    public void start() {
        waitForVespa();

        dataFeeder = new VespaDataFeeder(queue);

        dataFeeder.start();

        Product product = ImmutableProduct.builder()
                .id("8718855320960")
                .description("Deze damesjurk van Yoek is gemaakt van katoen. De jurk heeft verder een ronde hals en lange mouwen.")
                .img_src("https://cdn.jurkjes.nl/w200/h200/d200/products/909/bd69ac5a597c70d1f6d396f5bece14ab_1.jpg")
                .numberOfRatings(0)
                .price(89.9)
                .rating(0.0)
                .tags(" Yoek", "Yoek2")
                .title(" Yoek  Jersey jurk donkerblauw")
                .type("Home - Jurken online kopen")
                .vendor(" Yoek")
                .build();

        queue.add(product);
    }

    public static void main(String[] args) {
        new Runner().start();
    }
}
