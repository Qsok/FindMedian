import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Kru on 28.09.2016.
 */
public class ArrayContainer {

    static private ArrayList<Long> firstArray;
    static private ArrayList<Long> secondArray;

    public static boolean readFromFile(String fileName){
        //TODO считывать из файла потоком Long
        splitInputLinesToArray();
    }

    static private void splitInputLinesToArray(List<Long> unsplittedLinesFromFile) {

    }

    static boolean readFromConsole(Scanner sc) {

    }


}
