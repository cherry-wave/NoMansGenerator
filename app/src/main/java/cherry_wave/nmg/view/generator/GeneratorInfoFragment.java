package cherry_wave.nmg.view.generator;

import android.app.Dialog;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;

import cherry_wave.nmg.R;
import cherry_wave.nmg.view.NMGDialogFragment;

public class GeneratorInfoFragment extends NMGDialogFragment {

    private static final String ARG_CONTENT_RES = "contentRes";

    public static GeneratorInfoFragment newInstance(int contentRes) {
        GeneratorInfoFragment patternSaveDialog = new GeneratorInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_RES, contentRes);
        patternSaveDialog.setArguments(args);
        return patternSaveDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_generator_info)
                .neutralText(R.string.ok)
                .neutralColorRes(R.color.colorPrimary)
                .content(getArguments().getInt(ARG_CONTENT_RES));
        final MaterialDialog materialDialog = builder.build();

        return materialDialog;
    }

}
