package pl.tamides.mi9secpuconfigurator;

import androidx.core.content.ContextCompat;

public class ColorsManager {

    private static volatile ColorsManager instance = null;

    public static ColorsManager getInstance() {
        if (instance == null) {
            synchronized (ColorsManager.class) {
                if (instance == null) {
                    instance = new ColorsManager();
                }
            }
        }

        return instance;
    }

    public int getColor(int colorRes) {
        return ContextCompat.getColor(App.getAppContext(), colorRes);
    }
}
