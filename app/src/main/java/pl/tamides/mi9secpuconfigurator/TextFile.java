package pl.tamides.mi9secpuconfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextFile {

    private File file;

    public TextFile(File file) {
        this.file = file;
    }

    public String getFileText() {
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();

                if (line != null) {
                    stringBuilder.append("\n");
                }
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            ShortMessage.getInstance().show(e.getMessage());
            return null;
        }
    }
}
