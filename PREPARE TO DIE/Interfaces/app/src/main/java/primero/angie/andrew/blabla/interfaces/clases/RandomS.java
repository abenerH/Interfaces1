package primero.angie.andrew.blabla.interfaces.clases;

import java.util.UUID;

/**
 * Created by Dell on 26/11/2017.
 */

public class RandomS {
    public static void main(String[] args) {
        System.out.println(generateString());
    }

    /**
     * Return a random string
     *
     * @return
     */
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
