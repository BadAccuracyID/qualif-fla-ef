package net.slc.ef.fla.qualif.datastore;

import net.slc.ef.fla.qualif.model.data.RestaurantData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Datastore {

    private static Datastore instance;
    private final List<RestaurantData> restaurantData;
    private final File saveFile;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Datastore() {
        this.restaurantData = new ArrayList<>();

        this.saveFile = new File("highscore.txt");
        if (!this.saveFile.exists()) {
            try {
                this.saveFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }

        return instance;
    }

    public List<RestaurantData> getRestaurantData() {
        return restaurantData;
    }

    public void registerRestaurantData(RestaurantData restaurantData) {
        this.restaurantData.add(restaurantData);
    }

    public void save() throws IOException {
        // save restaurant data in saveFile with NAME:SCORE format
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.saveFile));
        for (RestaurantData restaurantData : this.restaurantData) {
            writer.write(restaurantData.getName() + ":" + restaurantData.getScore());
            writer.newLine();
        }

        writer.close();
    }

    public void loadAll() throws IOException {
        // load restaurant data from saveFile
        BufferedReader reader = new BufferedReader(new FileReader(this.saveFile));
        String line = reader.readLine();
        while (line != null) {
            String[] data = line.split(":");
            this.restaurantData.add(new RestaurantData(data[0], Integer.parseInt(data[1])));
            line = reader.readLine();
        }

        reader.close();
    }

    public void sort() {
        // sort restaurant data by score
        this.restaurantData.sort((o1, o2) -> o2.getScore() - o1.getScore());
    }
}
