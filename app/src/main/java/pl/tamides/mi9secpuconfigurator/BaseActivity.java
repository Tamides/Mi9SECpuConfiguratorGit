package pl.tamides.mi9secpuconfigurator;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected Thread thread = null;

    @Override
    protected void onDestroy() {
        if (thread != null) {
            thread.interrupt();
        }

        super.onDestroy();
    }
}
