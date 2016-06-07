package cherry_wave.nmg.view.syllables;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;
import cherry_wave.nmg.NMGDialogFragment;

public class SyllableDeleteFragment extends NMGDialogFragment {

    private static final String TAG = SyllableDeleteFragment.class.getCanonicalName();

    private static final String ARG_SYLLABLE = "syllable";

    private SyllablesFragment syllablesFragment;
    private Syllable syllable;

    public static SyllableDeleteFragment newInstance(Syllable syllable) {
        SyllableDeleteFragment syllableDialog = new SyllableDeleteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SYLLABLE, Parcels.wrap(syllable));
        syllableDialog.setArguments(args);
        return syllableDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        syllablesFragment = (SyllablesFragment) getTargetFragment();
        syllable = Parcels.unwrap(getArguments().getParcelable(ARG_SYLLABLE));

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(R.string.confirm_delete_syllable)
                .autoDismiss(false)
                .positiveText(R.string.delete)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SugarRecord.delete(syllable);
                        syllablesFragment.updateSyllables();
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
