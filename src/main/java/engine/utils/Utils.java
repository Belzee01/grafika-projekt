package engine.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static String loadFile(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load the file : " + file);
        }
    }
}
