package cherry_wave.nmg.view.syllables;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.controller.SyllableUtils;
import cherry_wave.nmg.model.Syllable;
import cherry_wave.nmg.NMGDialogFragment;

public class SyllableSaveFragment extends NMGDialogFragment {

    private static final String TAG = SyllableSaveFragment.class.getCanonicalName();

    private static final String ARG_SYLLABLE = "syllable";

    private SyllablesFragment syllablesFragment;
    private Syllable syllable;

    public static SyllableSaveFragment newInstance(Syllable syllable) {
        SyllableSaveFragment syllableDialog = new SyllableSaveFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SYLLABLE, Parcels.wrap(syllable));
        syllableDialog.setArguments(args);
        return syllableDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        syllablesFragment = (SyllablesFragment) getTargetFragment();
        syllable = Parcels.unwrap(getArguments().getParcelable(ARG_SYLLABLE));
        if (syllable == null) {
            syllable = new Syllable("");
        }

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_save_syllable)
                .autoDismiss(false)
                .positiveText(R.string.save)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText editText = dialog.getInputEditText();
                        syllable.setCharacters(editText.getText().toString());
                        if (isValid(editText)) {
                            save();
                            dialog.dismiss();
                        }
                    }
                })
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, syllable.getCharacters(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                });
        return builder.build();
    }

    private boolean isValid(EditText editText) {
        editText.setError(null);
        String validateResponse = SyllableUtils.validate(getContext(), syllable);
        if (!validateResponse.equals(getString(R.string.ok))) {
            editText.setError(validateResponse);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    private void save() {
        SugarRecord.save(syllable);
        syllablesFragment.updateSyllables();
    }

}
