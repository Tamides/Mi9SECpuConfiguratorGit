package pl.tamides.mi9secpuconfigurator;

import android.os.Bundle;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        List<String> aaa = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String commandResult = RootTerminal.getInstance().execCommand("cat /sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
            List<String> frequencies = Arrays.asList(commandResult.split(" "));


            for (String frequency : frequencies) {
                aaa.add(frequency.replace("\n", ""));
            }
        }

        bigGovernorList
                .setItemLayoutId(R.layout.text_item)
                .setItems(aaa)
                .selectItem("1708800");
    }

    public void findViews() {
        bigGovernorList = findViewById(R.id.bigGovernorList);
        bigMinList = findViewById(R.id.bigMinList);
        bigMaxList = findViewById(R.id.bigMaxList);
        littleGovernorList = findViewById(R.id.littleGovernorList);
        littleMinList = findViewById(R.id.littleMinList);
        littleMaxList = findViewById(R.id.littleMaxList);
    }
}
