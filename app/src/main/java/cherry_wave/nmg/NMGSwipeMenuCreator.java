package cherry_wave.nmg;

import android.content.Context;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;

import cherry_wave.nmg.R;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NMGSwipeMenuCreator implements SwipeMenuCreator {

    private Context context;

    @Override
    public void create(SwipeMenu menu) {
        SwipeMenuItem edit = new SwipeMenuItem(context);
        edit.setIcon(R.drawable.ic_edit);
        edit.setWidth(edit.getIcon().getMinimumWidth() * 2);
        menu.addMenuItem(edit);

        SwipeMenuItem delete = new SwipeMenuItem(context);
        delete.setIcon(R.drawable.ic_delete);
        delete.setWidth(delete.getIcon().getMinimumWidth() * 2);
        menu.addMenuItem(delete);
    }
}
