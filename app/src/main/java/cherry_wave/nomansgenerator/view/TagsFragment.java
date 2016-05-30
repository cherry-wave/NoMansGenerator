package cherry_wave.nomansgenerator.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cherry_wave.nomansgenerator.R;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TagsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tags, container, false);
        return rootView;
    }
}