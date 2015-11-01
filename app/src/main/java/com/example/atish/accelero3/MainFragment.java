package com.example.atish.accelero3;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MainFragment extends Fragment {

    private static EditText topTextInpuText;
    private static EditText bottomTextInput;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main,container,false);

            topTextInpuText = (EditText) view.findViewById(R.id.topTextInput);
            bottomTextInput = (EditText) view.findViewById(R.id.bottomTextInput);
            final Button fragment_button = (Button) view.findViewById(R.id.fragment_button);

        fragment_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        Fragment_buttonClicked(v);

                    }

                }
        );

            return view;
          }

    public void Fragment_buttonClicked (View view){




    }
}
