package com.example.unipaths.Activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequireFragment extends Fragment {

    List<Item_requirement> itemsUni = new ArrayList<>();
    List<Item_requirement> itemsPreu = new ArrayList<>();
    Know_Require_CuntomAdapter uniAdapter;
    Know_Require_CuntomAdapter preuAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequireFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequireFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequireFragment newInstance(String param1, String param2) {
        RequireFragment fragment = new RequireFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_require, container, false);

        //Requirement
        //------------------------------------------------------------------------------------------------

        // add items to the list
        itemsUni.add(new Item_requirement("Result of Malaysia University English Test (MUET)",
                "- at least band 1 (might vary for different courses and universities)"));
        itemsUni.add(new Item_requirement("Result of Sijil Pelajaran Malaysia (SPM)",
                "- C grade in Bahasa Malaysia\n- passing grade in History"));
        itemsUni.add(new Item_requirement("Pre-University Results:",
                "For the Malaysian Higher School Certificate (STPM):" +
                        "\n- a minimum PNGK of 2.00 with a Grade C in 3 subjects, including General Studies." +
                        "\n\nFor the Malaysian Higher Religious Certificate (STAM):\n- at least a Pangkat Jayyid qualification." +
                        "\n\nFor Matriculation/Pre-University:\n- a minimum PNGK of 2.00."));

        itemsPreu.add(new Item_requirement("Result of Sijil Pelajaran Malaysia (SPM)",
                "Diploma:\n- at least 3 credits in SPM and a pass in BM & Sejarah" +
                        "\nFoundation:\n- at least 5 credits in SPM" +
                        "\nMatriculation:\n- C in Bahasa Melayu and English"));
        itemsPreu.add(new Item_requirement("Others",
                "Matriculation:" +
                        "\n- Malaysian" +
                        "\n- SPM candidate for the current year" +
                        "\n- Age not exceeding 20 years old at registration"));


        RecyclerView recyclerViewReq = view.findViewById(R.id.list_requirementUni);
        recyclerViewReq.setLayoutManager(new LinearLayoutManager(getActivity()));
        uniAdapter = new Know_Require_CuntomAdapter(itemsUni);
        preuAdapter = new Know_Require_CuntomAdapter(itemsPreu);
        recyclerViewReq.setAdapter(uniAdapter);

        // Initialize your two buttons
        final Button uniiButton = view.findViewById(R.id.unirequire_btn);
        final Button preuButton = view.findViewById(R.id.preurequire_btn);

        // Set the initial text colors
        uniiButton.setTextColor(Color.BLACK);
        preuButton.setTextColor(Color.GRAY);

        // Set click listeners on your buttons
        uniiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the ipta button is clicked, set its text color to black and the ipts button's text color to gray
                uniiButton.setTextColor(Color.BLACK);
                preuButton.setTextColor(Color.GRAY);

                // Switch the RecyclerView's adapter to the iptaAdapter
                recyclerViewReq.setAdapter(uniAdapter);
            }
        });

        preuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the ipts button is clicked, set its text color to black and the ipta button's text color to gray
                preuButton.setTextColor(Color.BLACK);
                uniiButton.setTextColor(Color.GRAY);

                // Switch the RecyclerView's adapter to the iptsAdapter
                recyclerViewReq.setAdapter(preuAdapter);
            }
        });

        return view;
    }
}