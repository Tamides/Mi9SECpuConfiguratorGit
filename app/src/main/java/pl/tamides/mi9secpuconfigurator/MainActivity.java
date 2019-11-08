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
        setListeners();
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

    private void findViews() {
        bigGovernorList = findViewById(R.id.bigGovernorList);
        bigMinList = findViewById(R.id.bigMinList);
        bigMaxList = findViewById(R.id.bigMaxList);
        littleGovernorList = findViewById(R.id.littleGovernorList);
        littleMinList = findViewById(R.id.littleMinList);
        littleMaxList = findViewById(R.id.littleMaxList);
        save = findViewById(R.id.save);
    }

    private void setListeners() {
        save.setOnClickListener(v -> saveData());
    }

    private void loadData() {
        thread = new Thread(() -> {
            List<String> governors = getGovernors();
            List<String> bigCoresFrequencies = getBigCoresFrequencies();
            List<String> littleCoresFrequencies = getLittleCoresFrequencies();
            String bigCoresCurrentGovernor = getBigCoresCurrentGovernor();
            String bigCoresCurrentMinFrequency = getBigCoresCurrentMinFrequency();
            String bigCoresCurrentMaxFrequency = getBigCoresCurrentMaxFrequency();
            String littleCoresCurrentGovernor = getLittleCoresCurrentGovernor();
            String littleCoresCurrentMinFrequency = getLittleCoresCurrentMinFrequency();
            String littleCoresCurrentMaxFrequency = getLittleCoresCurrentMaxFrequency();

            if (governors == null || bigCoresFrequencies == null || littleCoresFrequencies == null || Thread.currentThread().isInterrupted()) {
                return;
            }

            runOnUiThread(() -> {
                bigGovernorList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(governors)
                        .selectItem(bigCoresCurrentGovernor);
                bigMinList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(bigCoresFrequencies)
                        .selectItem(bigCoresCurrentMinFrequency);
                bigMaxList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(bigCoresFrequencies)
                        .selectItem(bigCoresCurrentMaxFrequency);
                littleGovernorList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(governors)
                        .selectItem(littleCoresCurrentGovernor);
                littleMinList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(littleCoresFrequencies)
                        .selectItem(littleCoresCurrentMinFrequency);
                littleMaxList
                        .setItemLayoutId(R.layout.text_item)
                        .setItems(littleCoresFrequencies)
                        .selectItem(littleCoresCurrentMaxFrequency);
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

    private String getBigCoresCurrentGovernor() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor");
    }

    private String getLittleCoresCurrentGovernor() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
    }

    private String getBigCoresCurrentMinFrequency() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu7/cpufreq/cpuinfo_min_freq");
    }

    private String getLittleCoresCurrentMinFrequency() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq");
    }

    private String getBigCoresCurrentMaxFrequency() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu7/cpufreq/cpuinfo_max_freq");
    }

    private String getLittleCoresCurrentMaxFrequency() {
        return RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
    }

    private void saveData() {
        String chosenBigCoresGovernor = bigGovernorList.getSelectedItemText();
        String chosenBigCoresMinFrequency = bigMinList.getSelectedItemText();
        String chosenBigCoresMaxFrequency = bigMaxList.getSelectedItemText();
        String chosenLittleCoresGovernor = littleGovernorList.getSelectedItemText();
        String chosenLittleCoresMinFrequency = littleMinList.getSelectedItemText();
        String chosenlLittleCoresMaxFrequency = littleMaxList.getSelectedItemText();

        thread = new Thread(() -> {
            String commandResult = RootTerminal.getInstance().execCommand(
                    "echo " + chosenBigCoresGovernor + " > /sys/devices/system/cpu/cpu7/cpufreq/scaling_governor && " +
                            "echo " + chosenBigCoresMinFrequency + " > /sys/devices/system/cpu/cpu7/cpufreq/cpuinfo_min_freq && " +
                            "echo " + chosenBigCoresMaxFrequency + " > /sys/devices/system/cpu/cpu7/cpufreq/cpuinfo_max_freq && " +
                            "echo " + chosenLittleCoresGovernor + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor && " +
                            "echo " + chosenLittleCoresMinFrequency + " > /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq && " +
                            "echo " + chosenlLittleCoresMaxFrequency + " > /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            );

            if (commandResult != null && !Thread.currentThread().isInterrupted()) {
                runOnUiThread(this::finish);
            }
        });
        thread.start();
    }
}
