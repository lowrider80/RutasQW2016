package pe.gob.qw.rutas.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by uadin12 on 10/05/2016.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {
    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);

    }

    // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
    @Override
    public boolean canScrollVertically() {
        return false;
    }
}