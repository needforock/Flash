package ve.needforock.flash.views.main.finder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import ve.needforock.flash.R;

/**
 * Created by Soporte on 05-Sep-17.
 */

public class FinderDialogFragment extends DialogFragment implements FinderCallback {

    private EditText editText;
    private ImageButton imagebutton;
    private SpinKitView spinKitView;

    public static FinderDialogFragment newInstance() {
        return new FinderDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_finder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText= (EditText) view.findViewById(R.id.userInputEt);
        imagebutton = (ImageButton) view.findViewById(R.id.sendBtn);
        spinKitView = (SpinKitView) view.findViewById(R.id.loadingSkv);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCancelable(false);
                editText.setError(null);
                String email = editText.getText().toString();
                editText.setVisibility(View.GONE);
                imagebutton.setVisibility(View.GONE);
                spinKitView.setVisibility(View.VISIBLE);
                new UserValidation(FinderDialogFragment.this, getContext()).init(email);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void error(String error) {
        restoreViews();
        editText.setError(error);

    }

    @Override
    public void success() {
        dismiss();
    }

    @Override
    public void notFound() {
        restoreViews();
        Toast.makeText(getContext(), "Usuario no hallado", Toast.LENGTH_SHORT).show();
    }

    private void restoreViews(){
        spinKitView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        imagebutton.setVisibility(View.VISIBLE);

        setCancelable(true);
    }
}
