package pl.tamides.mi9secpuconfigurator;

import android.os.Bundle;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import pl.tamides.mi9secpuconfigurator.views.SelectableTextListView;

public class MainActivity extends BaseActivity {

    private SelectableTextListView bigGovernorList;
    private SelectableTextListView bigMinList;
    private SelectableTextListView bigMaxList;
    private SelectableTextListView littleGovernorList;
    private SelectableTextListView littleMinList;
    private SelectableTextListView littleMaxList;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        loadData();
//
//        List<String> aaa = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
//            List<String> frequencies = Arrays.asList(commandResult.split(" "));
//
//
//            for (String frequency : frequencies) {
//                aaa.add(frequency.replace("\n", ""));
//            }
//        }
//
//        bigGovernorList
//                .setItemLayoutId(R.layout.text_item)
//                .setItems(aaa)
//                .selectItem("1708800");
    }

    public void findViews() {
        bigGovernorList = findViewById(R.id.bigGovernorList);
        bigMinList = findViewById(R.id.bigMinList);
        bigMaxList = findViewById(R.id.bigMaxList);
        littleGovernorList = findViewById(R.id.littleGovernorList);
        littleMinList = findViewById(R.id.littleMinList);
        littleMaxList = findViewById(R.id.littleMaxList);
        save = findViewById(R.id.save);
    }

    private void loadData() {
        thread = new Thread(() -> {
            List<String> governors = getGovernors();
            List<String> bigCoresFrequencies = getBigCoresFrequencies();
            List<String> littleCoresFrequencies = getLittleCoresFrequencies();

            if (governors == null || bigCoresFrequencies == null || littleCoresFrequencies == null || Thread.currentThread().isInterrupted()) {
                return;
            }

            runOnUiThread(() -> {
                bigGovernorList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(governors);
                bigMinList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(bigCoresFrequencies);
                bigMaxList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(bigCoresFrequencies);
                littleGovernorList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(governors);
                littleMinList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(littleCoresFrequencies);
                littleMaxList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(littleCoresFrequencies);
            });
        });
        thread.start();
    }

    private List<String> getGovernors() {
        String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors");

        if (commandResult == null) {
            return null;
        }

        return Arrays.asList(commandResult.split(" "));
    }

    private List<String> getBigCoresFrequencies() {
        String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu7/cpufreq/scaling_available_frequencies");

        if (commandResult == null) {
            return null;
        }

        return Arrays.asList(commandResult.split(" "));
    }

    private List<String> getLittleCoresFrequencies() {
        String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies");

        if (commandResult == null) {
            return null;
        }

        return Arrays.asList(commandResult.split(" "));
    }
}
