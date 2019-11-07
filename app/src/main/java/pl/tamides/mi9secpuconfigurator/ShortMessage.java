package pl.tamides.mi9secpuconfigurator;

import android.widget.Toast;

public class ShortMessage {

    private static volatile ShortMessage instance = null;

    public static ShortMessage getInstance() {
        if (instance == null) {
            synchronized (ShortMessage.class) {
                if (instance == null) {
                    instance = new ShortMessage();
                }
            }
        }

        return instance;
    }

    public void show(String message) {
        Toast.makeText(App.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }
}
