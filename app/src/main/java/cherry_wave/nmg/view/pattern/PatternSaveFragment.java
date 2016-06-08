package cherry_wave.nmg.view.pattern;

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
import cherry_wave.nmg.controller.PatternUtils;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.NMGDialogFragment;

public class PatternSaveFragment extends NMGDialogFragment {

    private static final String TAG = PatternSaveFragment.class.getCanonicalName();

    private static final String ARG_PATTERN = "patterns";

    private PatternsFragment patternsFragment;
    private Pattern pattern;

    public static PatternSaveFragment newInstance(Pattern pattern) {
        PatternSaveFragment patternSaveDialog = new PatternSaveFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PATTERN, Parcels.wrap(pattern));
        patternSaveDialog.setArguments(args);
        return patternSaveDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        patternsFragment = (PatternsFragment) getTargetFragment();
        pattern = Parcels.unwrap(getArguments().getParcelable(ARG_PATTERN));
        if (pattern == null) {
            pattern = new Pattern("");
        }

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_save_pattern)
                .autoDismiss(false)
                .positiveText(R.string.save)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText editText = dialog.getInputEditText();
                        pattern.setCharacters(editText.getText().toString());
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
                .input(null, pattern.getCharacters(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                    }
                });
        final MaterialDialog materialDialog = builder.build();

        return materialDialog;
    }

    private boolean isValid(EditText editText) {
        editText.setError(null);
        String validateResponse = PatternUtils.validate(getContext(), pattern);
        if (!validateResponse.equals(getString(R.string.ok))) {
            editText.setError(validateResponse);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    private void save() {
        SugarRecord.save(pattern);
        patternsFragment.updatePatterns();
    }

}
