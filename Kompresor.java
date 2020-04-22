import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class Kompresor {

    public Kompresor(String file, String gzipFile) {
        kompresuj(file, gzipFile);
    }

    //// Metoda kompresująca plik do pliku w formacie GZIP
    public static void kompresuj(String file, String gzipFile) {
        try {
            try (FileInputStream fis = new FileInputStream(file)) {
                try (FileOutputStream fos = new FileOutputStream(gzipFile)) {
                    GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) != -1) {
                        gzipOS.write(buffer, 0, len);
                    }
                    //zamknięcie
                    gzipOS.close();
                    fos.close();
                    fis.close();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Nie znaleziono pliku: " + file);
            }
        } catch (IOException e) {
            System.out.println("Błąd kompresji");
        }
    }
}
