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
        this.saveFile = new File("save.dat");
        if (!this.saveFile.exists()) {
            this.saveFile.getParentFile().mkdirs();
        }
    }

    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }

        return instance;
    }

    public void registerRestaurantData(RestaurantData restaurantData) {
        this.restaurantData.add(restaurantData);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() throws IOException {
        if (!this.saveFile.exists()) {
            this.saveFile.getParentFile().mkdirs();
        }

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
}
