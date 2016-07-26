package cherry_wave.nmg.view.generator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import cherry_wave.nmg.R;
import cherry_wave.nmg.NMGDialogFragment;

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
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_generator_info)
                .neutralText(R.string.ok)
                .neutralColorRes(R.color.colorPrimary)
                .content(getArguments().getInt(ARG_CONTENT_RES));
        return builder.build();
    }

}
