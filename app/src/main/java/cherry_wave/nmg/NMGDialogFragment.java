package cherry_wave.nmg;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

public class NMGDialogFragment extends DialogFragment {

    protected View inflate(int resource) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(resource, null);
        ButterKnife.bind(this, view);
        return view;
    }
}
