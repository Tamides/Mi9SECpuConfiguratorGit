package pl.tamides.mi9secpuconfigurator;

import java.io.File;

public class RootTerminal {

    private static final String TMP_FILE_NAME = "tmp";

    private static volatile RootTerminal instance = null;

    private File tmpFile;

    private RootTerminal() {
        tmpFile = new File(App.getAppContext().getFilesDir() + File.separator + TMP_FILE_NAME);
    }

    public static RootTerminal getInstance() {
        if (instance == null) {
            synchronized (RootTerminal.class) {
                if (instance == null) {
                    instance = new RootTerminal();
                }
            }
        }

        return instance;
    }

    public String execCommand(String command) {
        try {
            new ProcessBuilder().command("su", "-c", command + " > " + tmpFile.getAbsolutePath()).start().waitFor();
            String result = new TextFile(tmpFile).getFileText();

            if (!tmpFile.delete()) {
                throw new Exception("Can't delete " + tmpFile.getAbsolutePath() + " file");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            ShortMessage.getInstance().show(e.getMessage());
            return null;
        }
    }
}
