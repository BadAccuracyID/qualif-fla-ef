package net.slc.ef.fla.qualif.model.data;

public class RestaurantData {

    private final String name;
    private final int score;

    public RestaurantData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static RestaurantDataBuilder builder() {
        return new RestaurantDataBuilder();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public static class RestaurantDataBuilder {

        private String name;
        private int score;

        public RestaurantDataBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantDataBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public RestaurantData build() {
            return new RestaurantData(name, score);
        }
    }
}
