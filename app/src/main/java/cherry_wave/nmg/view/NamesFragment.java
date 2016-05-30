package cherry_wave.nmg.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cherry_wave.nmg.R;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NamesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_names, container, false);
        return rootView;
    }
}