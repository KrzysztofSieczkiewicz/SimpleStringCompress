import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Dekompresor {

    public Dekompresor(String file, String gzipFile) {
        dekompresuj(file, gzipFile);
    }

    public static void dekompresuj(String gzipFile, String newFile) {
        try {
            try (FileInputStream fis = new FileInputStream(gzipFile)) {
                GZIPInputStream gis = new GZIPInputStream(fis);
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                //close resources
                fos.close();
                gis.close();
            } catch (FileNotFoundException e) {
                System.out.println("Nie znalezniono pliku: " + gzipFile);
            }
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać pliku do dekompresji");
        }
    }
}
