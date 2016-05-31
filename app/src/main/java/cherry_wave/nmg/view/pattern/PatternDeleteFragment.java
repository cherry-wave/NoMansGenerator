package cherry_wave.nmg.view.pattern;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.view.NMGDialogFragment;

public class PatternDeleteFragment extends NMGDialogFragment {

    private static final String TAG = PatternDeleteFragment.class.getCanonicalName();

    private static final String ARG_PATTERN = "patterns";

    private PatternsFragment patternsFragment;
    private Pattern pattern;

    public static PatternDeleteFragment newInstance(Pattern pattern) {
        PatternDeleteFragment patternDeleteDialog = new PatternDeleteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PATTERN, Parcels.wrap(pattern));
        patternDeleteDialog.setArguments(args);
        return patternDeleteDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        patternsFragment = (PatternsFragment) getTargetFragment();
        pattern = Parcels.unwrap(getArguments().getParcelable(ARG_PATTERN));

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(R.string.confirm_delete_pattern)
                .autoDismiss(false)
                .positiveText(R.string.delete)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SugarRecord.delete(pattern);
                        patternsFragment.updatePatterns();
                        dialog.dismiss();
                    }
                })
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        final MaterialDialog materialDialog = builder.build();

        return materialDialog;
    }

}
