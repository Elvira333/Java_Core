package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Tree {

    /**
     * TODO: Доработать метод print, необходимо распечатывать директории и файлы
     *
     * @param file
     * @param indent
     * @param isLast
     */
    public static void print(File file, String indent, boolean isLast) {
        System.out.print(indent); // рисуем отступ

        if (isLast && file.listFiles() != null && file.listFiles().length == 0) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        int subDirTotal = 0;
        int fileTotal = 0;

        for (File f : files) {
            if (f.isDirectory()) {
                subDirTotal++;
            } else {
                fileTotal++;
            }
        }

        int subDirCounter = 0;
        int fileCounter = 0;

        for (File f : files) {
            if (f.isDirectory()) {
                print(f, indent + (subDirCounter == subDirTotal - 1 ? "  " : "│ "), subDirCounter == subDirTotal - 1);
                subDirCounter++;
            } else {
                System.out.println(indent + (fileCounter == fileTotal - 1 ? "└─ " : "├─ ") + f.getName());
                fileCounter++;
            }
        }
    }


    public static void copyFilesToDirectory(String sourceDirectoryPath, String targetDirectoryPath) {
        File sourceDirectory = new File(sourceDirectoryPath);
        File targetDirectory = new File(targetDirectoryPath);

        // Проверяем, существует ли исходная директория
        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            System.out.println("Исходная директория не существует или не является директорией.");
            return;
        }

        // Проверяем, существует ли целевая директория
        if (!targetDirectory.exists() || !targetDirectory.isDirectory()) {
            System.out.println("Целевая директория не существует или не является директорией.");
            return;
        }

        // Получаем список файлов в исходной директории
        File[] files = sourceDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        // Создаем путь для целевого файла в новой директории
                        Path targetFilePath = targetDirectory.toPath().resolve(file.getName());

                        // Копируем файл в целевую директорию
                        Files.copy(file.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

                        System.out.println("Файл " + file.getName() + " успешно скопирован в новую директорию.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Ошибка при копировании файла " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}


