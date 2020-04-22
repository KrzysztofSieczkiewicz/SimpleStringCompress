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

            // Pętla menu głównego
            String wybor = "start";
            while (!wybor.equals("z")) {

                System.out.println("Podaj działanie: kompresja(k), dekompresja(d), zamknij program(z)");
                wybor = odczyt.nextLine();

            switch (wybor) {
                case "k": {
                    // Pobieranie plików do kompresowania dopóki użytkownik nie poinformuje, że to ostatni
                    List<String> nazwyPlikow = new ArrayList<>();
                    boolean wystarczy = false;
                    while (!wystarczy) {
                        System.out.println("Podaj nazwę pliku (z rozszerzeniem) do kompresowania lub 'koniec' żeby zacząć kompresję");
                        String odczytanaLinia = odczyt.nextLine();

                        if (odczytanaLinia.equals("koniec")) {
                            System.out.println("Zakończono wczytywanie plików \n");
                            wystarczy = true;
                        } else if (!odczytanaLinia.matches("(.*).txt")) {
                            System.out.println("Nieprawidłowa nazwa pliku (brak rozszerzenia .txt)");
                        } else {
                            nazwyPlikow.add(odczytanaLinia);
                            System.out.println("Dodano do kolejki plik: " + odczytanaLinia);
                        }
                    }

                    System.out.println("Pliki wybrane do kompresowania");
                    for (String nazwa : nazwyPlikow) {
                        System.out.println(nazwa);
                    }
                    System.out.println();

                    // Kompresowanie podanych plików
                    for (String nazwa : nazwyPlikow) {
                        StringBuilder nazwaPoKompresji = new StringBuilder();
                        nazwaPoKompresji.append(nazwa);
                        nazwaPoKompresji.delete(nazwaPoKompresji.length() - 4, nazwaPoKompresji.length());
                        nazwaPoKompresji.append(".gzip");
                        Kompresor.kompresuj(nazwa, nazwaPoKompresji.toString());
                    }
                    System.out.println("Zakończono kompresowanie\n");
                    break;
                }

                case "d": {
                    // Pobieranie plików do dekompresowania dopóki użytkownik nie poinformuje, że to ostatni
                    List<String> nazwySkompresowanychPlikow = new ArrayList<>();
                    boolean wystarczy = false;
                    while (!wystarczy) {
                        System.out.println("Podaj nazwę pliku (z rozszerzeniem) do dekompresowania lub 'koniec' żeby zacząć dekompresję");

                        String odczytanaLinia = odczyt.nextLine();
                        if (odczytanaLinia.equals("koniec") || odczytanaLinia.equals("Koniec")) {
                            System.out.println("Zakończono wczytywanie plików \n");
                            wystarczy = true;
                        } else if (!odczytanaLinia.matches("(.*).gzip")) {
                            System.out.println("Nieprawidłowa nazwa pliku (brak rozszerzenia .gzip)");
                        } else {
                            nazwySkompresowanychPlikow.add(odczytanaLinia);
                            System.out.println("Dodano do kolejki plik: " + odczytanaLinia);
                        }
                    }

                    System.out.println("Pliki wybrane do dekompresowania");
                    for (String nazwa : nazwySkompresowanychPlikow) {
                        System.out.println(nazwa);
                    }
                    System.out.println();

                    // Dekompresowanie podanych plików
                    for (String nazwa : nazwySkompresowanychPlikow) {
                        StringBuilder nazwaPoDekompresji = new StringBuilder();
                        nazwaPoDekompresji.append(nazwa);
                        nazwaPoDekompresji.delete(nazwaPoDekompresji.length() - 5, nazwaPoDekompresji.length());
                        nazwaPoDekompresji.append("Dekompresowany.txt");
                        Dekompresor.dekompresuj(nazwa, nazwaPoDekompresji.toString());
                    }
                    System.out.println("Zakończono dekompresowanie\n");
                    break;
                }

                case "z": {
                    System.out.println("\nZamykanie programu\n");
                    break;
                }
            }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Błąd komunikacji z użytkownikiem");
        }
    }







}

