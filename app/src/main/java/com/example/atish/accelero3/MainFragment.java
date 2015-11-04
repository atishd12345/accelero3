package com.example.atish.accelero3;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainFragment extends Fragment {

    private static EditText test_EditText;
    private static TextView test_TextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main,container,false);
        test_TextView = (TextView) view.findViewById(R.id.test_TextView);
        test_EditText = (EditText) view.findViewById(R.id.test_EditText);
        final Button bt_connect = (Button) view.findViewById(R.id.bt_connect);
        final Button bt_disconnect = (Button) view.findViewById(R.id.bt_disconnect);
        final Button fragment_button = (Button) view.findViewById(R.id.test_fragment_button);

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

        Toast.makeText(getActivity(),"ButtonPressed",Toast.LENGTH_LONG).show();





    }
}
