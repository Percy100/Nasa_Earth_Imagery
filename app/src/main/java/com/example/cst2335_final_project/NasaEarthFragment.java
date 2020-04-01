package com.example.cst2335_final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NasaEarthFragment extends Fragment {
    private View view;
    private TextView txtLat;
    private TextView txtLong;
    Button fragHide;

    public NasaEarthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nasa_earth, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtLat = view.findViewById(R.id.LatText);
        txtLong = view.findViewById(R.id.LongText);
        fragHide = view.findViewById(R.id.deleteButton);

        txtLat.setText(getArguments().getString("Latitude"));
        txtLong.setText(getArguments().getString("Longitude"));

        fragHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getFragmentManager().beginTransaction().remove(NasaEarthFragment.this).commit();

            }
        });
    }

}
