package cherry_wave.nmg.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.R;

public class TutorialActivity extends NMGActivity {
    
    @BindView(R.id.tutorial_text)
    TextView tutorial;

    public static final String EXTRA_TEXT = "EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent != null) {
            // Shouldn't be called without the extra
            tutorial.setText(intent.getIntExtra(EXTRA_TEXT, -1));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
