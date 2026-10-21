package com.example.atish.accelero3;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainFragment extends Fragment {

    private EditText topTextInput;
    private EditText bottomTextInput;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main,container,false);

            topTextInput = (EditText) view.findViewById(R.id.topTextInput);
            bottomTextInput = (EditText) view.findViewById(R.id.bottomTextInput);
            final Button fragmentButton = (Button) view.findViewById(R.id.fragment_button);
            if (fragmentButton != null) {
                fragmentButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Fragment_buttonClicked(v);
                    }
                });
            }

            return view;
          }

    public void Fragment_buttonClicked(View view) {
        if (getActivity() == null || topTextInput == null || bottomTextInput == null) {
            return;
        }
        String top = topTextInput.getText().toString().trim();
        String bottom = bottomTextInput.getText().toString().trim();
        String message = top.length() == 0 && bottom.length() == 0
                ? "ButtonPressed"
                : top + "\n" + bottom;
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        topTextInput = null;
        bottomTextInput = null;
    }
}
