import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class DataSaver {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
        ArrayList<String> recs = new ArrayList<>();
        boolean continueInput = true;
        System.out.println(" DATA ENTRY ");

        while (continueInput) {
            String firstName = SafeInput.getNonZeroLenString(pipe, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(pipe, "Enter Last Name");
            int idNum = SafeInput.getRangedInt(pipe, "Enter ID Number", 1, 999999);
            String idString = String.format("%06d", idNum);

            String email = "";
            boolean isValidEmail = false;
            while (!isValidEmail) {
                email = SafeInput.getNonZeroLenString(pipe, "Enter Email");
                if (EMAIL_REGEX.matcher(email).matches()) {
                    isValidEmail = true;
                } else {
                    System.out.println("Invalid email format! Please try again (e.g., name@example.com).");
                }
            }

            int yob = SafeInput.getRangedInt(pipe, "Enter your Year of Birth", 1900, 2026);
            String record = firstName + ", " + lastName + ", " + idString + ", " + email + ", " + yob;
            recs.add(record);
            continueInput = SafeInput.getYNConfirm(pipe, "Do you want to enter another record?");
        }

        String fileName = SafeInput.getNonZeroLenString(pipe, "Enter output filename (without extension)");
        if (!fileName.endsWith(".csv")) {
            fileName = fileName + ".csv";
        }

        Path file = Paths.get(System.getProperty("user.dir"), "src", fileName);

        try {
            Files.write(file, recs, CREATE, TRUNCATE_EXISTING);
            System.out.println("\nData file written successfully to: " + file.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
