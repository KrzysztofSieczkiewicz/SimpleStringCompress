import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class AppGZIP {
    public static void main(String[] args) {

        // Otwarcie komunikacji między użytkownikiem i programem
        try (Scanner odczyt = new Scanner(System.in)) {


            // Pobieranie plików do kompresowania dopóki użytkownik nie poinformuje, że to ostatni
            List<String> nazwyPlikow = new ArrayList<>();
            boolean wystarczy = false;
            while (wystarczy != true) {
                System.out.println("Podaj nazwę pliku (z rozszerzeniem) do kompresowania lub 'koniec' żeby zacząć kompresję");

                String odczytanaLinia = odczyt.nextLine();
                if (odczytanaLinia.equals("koniec") || odczytanaLinia.equals("Koniec")) {
                    System.out.println("Zakończono wczytywanie plików \n");
                    break;
                } else {
                    nazwyPlikow.add(odczytanaLinia);
                    System.out.println("Dodano do kolejki plik: " + odczytanaLinia);
                }
            }

            System.out.println("Pliki wybrane do kompresowania");
            for (String nazwa : nazwyPlikow) {
                System.out.println(nazwa);
            }

            // Kompresowanie podanych plików
            for (String nazwa : nazwyPlikow) {
                StringBuilder nazwaPoKompresji = new StringBuilder();
                nazwaPoKompresji.append(nazwa);
                nazwaPoKompresji.delete(nazwaPoKompresji.length() - 4, nazwaPoKompresji.length());
                nazwaPoKompresji.append(".GZIP");
                kompresuj(nazwa, nazwaPoKompresji.toString());
            }
            System.out.println("Zakończono kompresowanie");


            // Pobieranie plików do dekompresowania dopóki użytkownik nie poinformuje, że to ostatni
            List<String> nazwySkompresowanychPlikow = new ArrayList<>();
            wystarczy = false;
            while (wystarczy != true) {
                System.out.println("Podaj nazwę pliku (z rozszerzeniem) do kompresowania lub 'koniec' żeby zacząć kompresję");

                String odczytanaLinia = odczyt.nextLine();
                if (odczytanaLinia.equals("koniec") || odczytanaLinia.equals("Koniec")) {
                    System.out.println("Zakończono wczytywanie plików \n");
                    break;
                } else {
                    nazwySkompresowanychPlikow.add(odczytanaLinia);
                    System.out.println("Dodano do kolejki plik: " + odczytanaLinia);
                }
            }

            System.out.println("Pliki wybrane do dekompresowania");
            for (String nazwa : nazwySkompresowanychPlikow) {
                System.out.println(nazwa);
            }

            // Dekompresowanie podanych plików
            for (String nazwa : nazwySkompresowanychPlikow) {
                StringBuilder nazwaPoDekompresji = new StringBuilder();
                nazwaPoDekompresji.append(nazwa);
                nazwaPoDekompresji.delete(nazwaPoDekompresji.length() - 5, nazwaPoDekompresji.length());
                nazwaPoDekompresji.append("Dekompresowany.txt");
                dekompresuj(nazwa, nazwaPoDekompresji.toString());
            }
            System.out.println("Zakończono dekompresowanie");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Błąd komunikacji z użytkownikiem");
        }
    }


    //// Metoda kompresująca plik do pliku w formacie GZIP
    private static void kompresuj(String file, String gzipFile) {
        try {
            try(FileInputStream fis = new FileInputStream(file)) {
                try(FileOutputStream fos = new FileOutputStream(gzipFile)) {
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

    private static void dekompresuj(String gzipFile, String newFile) {
        try {
            try(FileInputStream fis = new FileInputStream(gzipFile)) {
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

