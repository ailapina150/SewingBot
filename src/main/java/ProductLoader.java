import java.util.List;

public final class ProductLoader {
    private static List<List<String>> filesNameGroup;

    private ProductLoader() {
        filesNameGroup = FileLoader.getFilesNamesGroup(AppProperties.BAG);
    }

    public static synchronized List<List<String>> get() {
        if (filesNameGroup == null) {
            new ProductLoader();
        }
        return filesNameGroup;
    }
}