
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileLoader {

    public static String getFileName(String fileName) {
        return "./" + fileName;
    }

    public static List<String> getFilesNames(String path) {
        try {
            return Files.walk(Paths.get(getFileName(path)))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }

    public static List<List<String>> getFilesNamesGroup(String path) {
        List<String> filesNames = Objects.requireNonNull(getFilesNames(path)).stream()
                .toList();
        return makeListOfList(filesNames, AppProperties.NUMBER_PHOTO_IN_GROUP, true);
    }


    public static String getSingleName(String fullName) {
        if (fullName == null) return "";
        return fullName.substring(fullName.lastIndexOf("\\") + 1, fullName.lastIndexOf("."))
                .substring(fullName.lastIndexOf("/") + 1);
    }

    public static List<String> getAllSingleNames(List<String> fullNames) {
        return fullNames.stream()
                .map(name -> name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf("."))
                        .substring(name.lastIndexOf("/") + 1))
                .toList();
    }

    public static List<List<String>> makeListOfList(List<String> buttonsName, int numberInRow, boolean remFirst) {
        List<List<String>> buttonsNameRows = new ArrayList<>();
        int rem = buttonsName.size() % numberInRow;
        int i = 0;
        while (i < buttonsName.size()) {
            List<String> row = new ArrayList<>();
            for (int j = 0; (j < numberInRow && i < buttonsName.size()); j++) {
                row.add(buttonsName.get(i));
                i++;
                if (remFirst && rem != 0 && i == rem) break;
            }
            buttonsNameRows.add(row);
        }
        return buttonsNameRows;
    }

}
