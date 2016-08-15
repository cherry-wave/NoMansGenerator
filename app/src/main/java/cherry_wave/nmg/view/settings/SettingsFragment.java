package cherry_wave.nmg.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import cherry_wave.nmg.R;
import cherry_wave.nmg.view.TutorialActivity;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        findPreference(getString(R.string.preference_patterns_tutorial)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                intent.putExtra(TutorialActivity.EXTRA_TEXT, R.string.tutorial_main);
                startActivity(intent);
                return true;
            }
        });
    }
}