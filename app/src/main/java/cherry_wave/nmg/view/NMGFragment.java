package cherry_wave.nmg.view;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class NMGFragment extends Fragment {

    protected View inflate(LayoutInflater inflater, int resource, ViewGroup root, boolean attachToRoot) {
        View view = inflater.inflate(resource, root, attachToRoot);
        ButterKnife.bind(this, view);
        return view;
    }
}
