package pe.gob.qw.rutas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExpandibleListView extends ListView {

    boolean expanded = false;
    int cargas = 1;
    public ExpandibleListView(Context context) {
        super(context);
    }

    public ExpandibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandibleListView(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded()) {
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
