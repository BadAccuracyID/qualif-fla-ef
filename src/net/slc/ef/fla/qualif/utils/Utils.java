package net.slc.ef.fla.qualif.utils;

import java.util.List;

public class Utils {

    public static final List<String> customerFirstNames = List.of(
            "Aldo",
            "Benoit",
            "Clement",
            "Dorian",
            "Ethan",
            "Florian",
            "Gael",
            "Hugo",
            "Ilan",
            "Julien",
            "Kevin",
            "Liam",
            "Mathis",
            "Nathan",
            "Oscar",
            "Paul",
            "Quentin",
            "Raphael",
            "Sacha",
            "Theo",
            "Ugo",
            "Valentin",
            "William",
            "Xavier",
            "Yanis",
            "Zachary"
    );

    public static final List<String> customerLastNames = List.of(
            "Aubert",
            "Bourgeois",
            "Clement",
            "Dumont",
            "Evrard",
            "Fournier",
            "Girard",
            "Huet",
            "Imbert",
            "Jacquet",
            "Klein",
            "Lefevre",
            "Martin",
            "Noel",
            "Olivier",
            "Petit",
            "Quentin",
            "Renaud",
            "Simon",
            "Tanguy",
            "Urbain",
            "Vidal",
            "Wagner",
            "Xavier",
            "Yves",
            "Zimmermann"
    );

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}
