package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в следующем формате:");
        System.out.println("Фамилия Имя Отчество дата_рождения номер_телефона пол");
        System.out.println("Дата рождения в формате dd.mm.yyyy, пол - символ 'f' или 'm'.");

        try {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts.length != 6) {
                throw new InputMismatchException("Неверное количество данных. Должно быть 6 значений.");
            }

            if (containsDigit(parts[0]) || containsDigit(parts[1]) || containsDigit(parts[2])) {
                throw new InputMismatchException("Фамилия, имя или отчество не должны содержать цифры.");
            }

            String surname = parts[0];
            String name = parts[1];
            String patronymic = parts[2];
            LocalDate birthDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            long phoneNumber = Long.parseLong(parts[4]);
            char gender = parts[5].charAt(0);

            if (gender != 'f' && gender != 'm') {
                throw new InputMismatchException("Неверный формат пола. Используйте 'f' или 'm'.");
            }

            String filename = "";
            if (gender == 'f' && parts[0].endsWith("а")) {
                filename = surname.substring(0, surname.length() - 1) + ".txt";
            } else {
                filename = surname + ".txt";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.write(surname + " " + name + " " + patronymic + " " +
                        birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                        phoneNumber + " " + gender + "\n");
                System.out.println("Данные успешно записаны в файл " + filename);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }

        } catch (InputMismatchException | NumberFormatException | ArrayIndexOutOfBoundsException |
                 DateTimeParseException e) {
            System.err.println("Ошибка ввода данных: " + e.getMessage());
        }
    }

    private static boolean containsDigit(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}