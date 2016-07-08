package cherry_wave.nmg.view.settings;

import android.os.Bundle;

import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.R;

public class UsedLibrariesActivity extends NMGActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, new UsedLibrariesFragment())
                .commit();
    }
}