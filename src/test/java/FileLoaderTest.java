import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void getFilesNames_validPathWithFiles_returnsListOfFileNames() throws IOException {
        System.out.println(tempDir);
        Path file1 = Files.createFile(tempDir.resolve("file1.txt"));
        Path file2 = Files.createFile(tempDir.resolve("file2.txt"));
        List<String> fileNames = FileLoader.getFilesNames(tempDir.toString());
        assertNotNull(fileNames);
        assertEquals(2, fileNames.size());
        assertTrue(fileNames.contains(file1.toString()));
        assertTrue(fileNames.contains(file2.toString()));
    }
    @Test
    void getFilesNames_validPathWithNoFiles_returnsEmptyList() {
        List<String> fileNames =  FileLoader.getFilesNames(tempDir.toString());
        assertNotNull(fileNames);
        assertTrue(fileNames.isEmpty());
    }

    @Test
    void getFilesNames_invalidPath_returnsNull() {
        List<String> fileNames =  FileLoader.getFilesNames("invalid/path");
        assertNull(fileNames);
    }

    @Test
    void testGetFilesNames_invalidPath() {
        List<String> result = FileLoader.getFilesNames("invalid/path");
        assertNull(result);
    }

    @Test
    void testGetFilesNamesGroup_emptyDirectory() throws IOException {
        Path tempDir = Files.createTempDirectory("test");
        List<List<String>> result = FileLoader.getFilesNamesGroup(tempDir.toString());
        assertTrue(result.isEmpty());
        Files.deleteIfExists(tempDir);
    }


    @Test
    void testGetSingleName() {
        assertEquals("image", FileLoader.getSingleName("path/to/image.jpg"));
        assertEquals("image", FileLoader.getSingleName("path\\to\\image.jpg"));
        assertEquals("", FileLoader.getSingleName(null));
        assertEquals("image", FileLoader.getSingleName("image.jpg")); // Тест без пути
        assertEquals("image", FileLoader.getSingleName("image")); // Тест без расширения
    }

    @Test
    void testGetAllSingleNames() {
        List<String> fullNames = List.of("path/to/image1.jpg", "path\\to\\image2.png", "image3.gif");
        List<String> singleNames = FileLoader.getAllSingleNames(fullNames);
        assertEquals(List.of("image1", "image2", "image3"), singleNames);
    }


    @Test
    void testMakeListOfList_empty() {
        List<List<String>> result = FileLoader.makeListOfList(new ArrayList<>(), 3, true);
        assertTrue(result.isEmpty());
    }

    @Test
    void testMakeListOfList_severalItems() {
        List<String> items = List.of("a", "b", "c", "d", "e", "f", "g");
        List<List<String>> result = FileLoader.makeListOfList(items, 3, false);
        assertEquals(3, result.size());
        assertEquals(List.of("a", "b", "c"), result.get(0));
        assertEquals(List.of("d", "e", "f"), result.get(1));
        assertEquals(List.of("g"), result.get(2));
    }

    @Test
    void testMakeListOfList_severalItems_remFist_true() {
        List<String> items = List.of("a", "b", "c", "d", "e", "f", "g");
        List<List<String>> result = FileLoader.makeListOfList(items, 3, true);
        assertEquals(3, result.size());
        assertEquals(List.of("a"), result.get(0));
        assertEquals(List.of("b", "c", "d"), result.get(1));
        assertEquals(List.of("e", "f", "g"), result.get(2));
    }

}