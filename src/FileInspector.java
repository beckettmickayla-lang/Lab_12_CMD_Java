import javax.swing.*;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile = null;
        String rec = "";
        boolean fileReady = false;

        // Check if a command line argument is provided
        if (args.length > 0) {
            selectedFile = new File(args[0]);
            if (selectedFile.exists() && selectedFile.isFile()) {
                fileReady = true;
            } else {
                System.out.println("Provided file does not exist or is invalid: " + args[0]);
                System.out.println("Exiting...");
                return;
            }
        } else {
            // No argument provided, launch JFileChooser
            try {
                File workingDirectory = new File(System.getProperty("user.dir") + File.separator + "src");
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    fileReady = true;
                } else {
                    System.out.println("No file selected! ... exiting");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Process the file if it was successfully resolved
        if (fileReady && selectedFile != null) {
            try {
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int lineCount = 0;
                int wordCount = 0;
                int charCount = 0;

                System.out.println(" FILE CONTENTS ");
                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;
                    System.out.println(rec);
                    charCount = charCount + rec.length();

                    if (!rec.trim().isEmpty()) {
                        String[] words = rec.trim().split("\\s+");
                        wordCount = wordCount + words.length;
                    }
                }
                reader.close();

                System.out.println("\n SUMMARY REPORT ");
                System.out.println("File Name: " + selectedFile.getName());
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
