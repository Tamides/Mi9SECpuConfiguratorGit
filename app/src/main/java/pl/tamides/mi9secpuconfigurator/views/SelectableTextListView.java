package pl.tamides.mi9secpuconfigurator.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.tamides.mi9secpuconfigurator.ColorsManager;
import pl.tamides.mi9secpuconfigurator.R;

public class SelectableTextListView extends LinearLayout {

    private Context context;
    private List<String> items = new ArrayList<>();
    private int layoutId;
    private int clickedViewPosition;

    public SelectableTextListView(Context context) {
        super(context);
        init(context);
    }

    public SelectableTextListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectableTextListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SelectableTextListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
    }

    public SelectableTextListView setItemLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public SelectableTextListView setItems(List<String> items) {
        this.items = items;

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            TextView view = (TextView) LayoutInflater.from(context).inflate(layoutId, this, false);
            view.setTag(i);
            view.setText(item);
            view.setOnClickListener(v -> {
                clickedViewPosition = (int) v.getTag();
                v.setBackgroundColor(ColorsManager.getInstance().getColor(R.color.gray));
                setNotSelectedItemsBackground();
            });

            addView(view);
        }

        return this;
    }

    private void setNotSelectedItemsBackground() {
        for (int i = 0; i < getChildCount(); i++) {
            TextView view = (TextView) getChildAt(i);
            int viewPosition = (int) view.getTag();

            if (viewPosition != clickedViewPosition) {
                view.setBackgroundColor(ColorsManager.getInstance().getColor(R.color.black));
            }
        }
    }

    public String getSelectedItemText() {
        return items.get(clickedViewPosition);
    }

    public void selectItem(String itemText) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView view = (TextView) getChildAt(i);

            if (TextUtils.equals(view.getText(), itemText)) {
                view.callOnClick();
            }
        }
    }
}
