package org.jurassicraft.server.genetics;

import java.util.Random;

public class GeneticsHelper {
    public static final int GENETICS_LENGTH = 10;

    public static String randomGenetics(Random random) {
    	StringBuilder genetics = new StringBuilder();

        for (int i = 0; i < GENETICS_LENGTH; i++) {
            int character = random.nextInt(4);

            switch (character) {
                case 0:
                	genetics.append("A");
                    break;
                case 1:
                	genetics.append("C");
                    break;
                case 2:
                	genetics.append("G");
                    break;
                case 3:
                	genetics.append("T");
                    break;
            }
        }

        return genetics.toString();
    }
}
