import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j
public final class ProductLoader {
    private static List<List<String>> filesNameGroup;

    private ProductLoader() {
        filesNameGroup = FileLoader.getFilesNamesGroup(AppProperties.BAG);
        log.info("filesNameGroup создан с количеством строк: " + filesNameGroup.size());
    }

    public static synchronized List<List<String>> get() {
        if (filesNameGroup == null) {
            new ProductLoader();
        }
        log.info("filesNameGroup получен: " + filesNameGroup.size());
        return filesNameGroup;
    }
}