package edu.ucsb.cs.cs185.contenant.contenant;

/**
 * Created by Allison on 5/31/2016.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DiagFragment extends DialogFragment {

    Button mButton;
    TextView mTextView;
    TextView title_view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.fragment_diag);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        title_view=(TextView)dialog.findViewById(R.id.help_title);
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face);
        mButton = (Button) dialog.findViewById(R.id.ok);
        mTextView = (TextView) dialog.findViewById(R.id.details1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

}
