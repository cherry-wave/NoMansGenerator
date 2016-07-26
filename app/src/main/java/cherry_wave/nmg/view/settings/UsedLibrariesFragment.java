package cherry_wave.nmg.view.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cherry_wave.nmg.R;

public class UsedLibrariesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.used_libraries);
    }
}