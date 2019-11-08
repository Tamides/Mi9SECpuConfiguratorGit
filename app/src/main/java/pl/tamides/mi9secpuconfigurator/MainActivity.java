package pl.tamides.mi9secpuconfigurator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 8; i++) {
            String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
            List<String> frequencies = Arrays.asList(commandResult.split(" "));

            LinearLayout governorContainer = findViewById(R.id.governorContainer);

            for (String frequency : frequencies) {
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.text_item, governorContainer, false);
                textView.setText(frequency);
                governorContainer.addView(textView);
            }
        }
    }
}
