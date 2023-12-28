package com.example.unipaths.Activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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
 * Use the {@link UniversityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UniversityFragment extends Fragment {

    List<Item_universities> itemsIPTA = new ArrayList<>();
    List<Item_universities> itemsIPTS = new ArrayList<>();
    List<Item_universities> itemsMatric = new ArrayList<>();

    Know_Uni_CustomAdapter iptaAdapter;
    Know_Uni_CustomAdapter iptsAdapter;
    Know_Uni_CustomAdapter matricAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UniversityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UniversityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UniversityFragment newInstance(String param1, String param2) {
        UniversityFragment fragment = new UniversityFragment();
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
        View view = inflater.inflate(R.layout.fragment_university, container, false);

        // Inflate the layout for this fragment
        // Initialize your ListView and your adapters
        itemsIPTA.add(new Item_universities("Universiti Malaya", R.drawable.ipta_um_logo, "https://www.um.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Sains Malaysia", R.drawable.ipta_usm_logo, "https://www.usm.my/"));
        itemsIPTA.add(new Item_universities("Universiti Kebangsaan Malaysia", R.drawable.ipta_ukm_logo, "http://www.ukm.my/"));
        itemsIPTA.add(new Item_universities("Universiti Putra Malaysia", R.drawable.ipta_upm_logo, "http://www.upm.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Teknologi Malaysia", R.drawable.ipta_utm_logo, "http://www.utm.my/"));
        itemsIPTA.add(new Item_universities("Universiti Utara Malaysia", R.drawable.ipta_uum_logo, "http://www.uum.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Teknologi MARA", R.drawable.ipta_uitm_logo, "http://www.uitm.edu.my"));
        itemsIPTA.add(new Item_universities("Universiti Islam Antarabangsa Malaysia", R.drawable.ipta_iium_logo, "http://www.iium.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Pahang", R.drawable.ipta_ump_logo, "http://www.ump.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Pendidikan Sultan Idris", R.drawable.ipta_upsi_logo, "http://www.upsi.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Perlis", R.drawable.ipta_unimap_logo, "https://www.unimap.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Sabah", R.drawable.ipta_ums_logo, "https://ums.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Sarawak", R.drawable.ipta_unimas_logo, "https://www.unimas.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Terengganu", R.drawable.ipta_umt_logo, "http://www.umt.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Tun Hussein Onn Malaysia", R.drawable.ipta_uthm_logo, "https://www.uthm.edu.my/en/"));
        itemsIPTA.add(new Item_universities("Universiti Sains Islam Malaysia", R.drawable.ipta_usim_logo, "https://www.usim.edu.my/ms/"));
        itemsIPTA.add(new Item_universities("Universiti Teknikal Malaysia Melaka", R.drawable.ipta_utem_logo, "http://www.utem.edu.my/"));
        itemsIPTA.add(new Item_universities("Universiti Malaysia Kelantan", R.drawable.ipta_umk_logo, "https://www.umk.edu.my/ms/"));
        itemsIPTA.add(new Item_universities("Universiti Universiti Sultan Zainal Abidin", R.drawable.ipta_unisza_logo, "https://www.unisza.edu.my/ms/"));

        // Initialize your ListView and your adapters
        itemsIPTS.add(new Item_universities("Universiti Taylor's", R.drawable.ipts_taylor_logo, "http://www.taylors.edu.my/ "));
        itemsIPTS.add(new Item_universities("Universiti UCSI", R.drawable.ipts_ucsi_logo, "https://www.ucsiuniversity.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Teknologi PETRONAS", R.drawable.ipts_petronas_logo, "https://www.utp.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Antarabangsa INTI", R.drawable.ipts_inti_logo, "http://newinti.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Sunway", R.drawable.ipts_sunway_logo, "http://www.sunway.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Teknologi & Inovasi Asia Pasifik", R.drawable.ipts_apu_logo, "http://www.apu.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Pengurusan & Sains", R.drawable.ipts_msu_logo, "https://www.msu.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Tenaga Nasional", R.drawable.ipts_uniten_logo, "http://www.uniten.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Tunku Abdul Rahman", R.drawable.ipts_utar_logo, "http://www.utar.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Multimedia", R.drawable.ipts_mmu_logo, "https://www.mmu.edu.my/ "));
        itemsIPTS.add(new Item_universities("Universiti Kuala Lumpur", R.drawable.ipts_unikl_logo, "https://www.unikl.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti SEGi", R.drawable.ipts_segi_logo, "https://www.segi.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Help", R.drawable.ipts_help_logo, "https://help.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti AIMST", R.drawable.ipts_aimst_logo, "https://aimst.edu.my/"));
        itemsIPTS.add(new Item_universities("Universiti Teknologi Kreatif Limkokwing", R.drawable.ipts_lim_logo, "https://www.limkokwing.net/"));
        itemsIPTS.add(new Item_universities("Universiti Sains dan Teknologi Malaysia", R.drawable.ipts_must_logo, "https://must.edu.my/"));

        itemsMatric.add(new Item_universities("Kolej Matrikulasi Melaka", R.drawable.kmm_logo, "http://www.kmm.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Negeri Sembilan", R.drawable.kmns_logo, "http://www.kmns.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Pulau Pinang", R.drawable.kmpp_logo, "http://www.kmpp.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Perlis", R.drawable.kmp_logo, "http://www.kmp.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Labuan", R.drawable.kml_logo, "http://www.kml.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Johor", R.drawable.kmj_logo, "http://www.kmj.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Perak", R.drawable.kmpk_logo, "http://www.kmpk.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Kedah", R.drawable.kmk_logo, "http://www.kmk.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Pahang", R.drawable.kmph_logo, "http://www.kmph.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Selangor", R.drawable.kms_logo, "http://www.kms.matrik.edu.my"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Kelantan", R.drawable.kmkt_logo, "http://www.kmkt.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Sarawak", R.drawable.kmsw_logo, "http://www.kmsw.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Kejuruteraan Kedah", R.drawable.kmtk_logo, "http://www.kmtk.matrik.edu.my/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Kejuruteraan Pahang", R.drawable.kmkph_logo, "http://www.kmkph.matrik.edu.my/v3/"));
        itemsMatric.add(new Item_universities("Kolej Matrikulasi Kejuruteraan Johor", R.drawable.kmkj_logo, "https://www.kmkj.matrik.edu.my/"));

        RecyclerView recyclerViewUni =view.findViewById(R.id.recycler_view);
        recyclerViewUni.setLayoutManager(new LinearLayoutManager(getActivity()));
        iptaAdapter = new Know_Uni_CustomAdapter(getActivity(), itemsIPTA);
        iptsAdapter = new Know_Uni_CustomAdapter(getActivity(), itemsIPTS);
        matricAdapter = new Know_Uni_CustomAdapter(getActivity(), itemsMatric);

        // Set the initial adapter
        recyclerViewUni.setAdapter(iptaAdapter);

        // Initialize your three buttons
        final Button iptaButton = view.findViewById(R.id.ipta_btn);
        final Button iptsButton = view.findViewById(R.id.ipts_btn);
        final Button matricButton = view.findViewById(R.id.matrik_btn);

        // Set the initial text colors
        iptaButton.setTextColor(Color.BLACK);
        iptsButton.setTextColor(Color.GRAY);
        matricButton.setTextColor(Color.GRAY);

        // Set up the searchable functionality
        // Assuming you have a SearchView with the id 'search_view'

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);

        // Set click listeners on your buttons
        iptaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the ipta button is clicked, set its text color to black and the ipts button's text color to gray
                iptaButton.setTextColor(Color.BLACK);
                iptsButton.setTextColor(Color.GRAY);
                matricButton.setTextColor(Color.GRAY);

                searchView.setQuery("", false);

                // Switch the RecyclerView's adapter to the iptaAdapter
                recyclerViewUni.setAdapter(iptaAdapter);
            }
        });

        iptsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the ipts button is clicked, set its text color to black and the ipta button's text color to gray
                iptsButton.setTextColor(Color.BLACK);
                iptaButton.setTextColor(Color.GRAY);
                matricButton.setTextColor(Color.GRAY);

                searchView.setQuery("", false);

                // Switch the RecyclerView's adapter to the iptsAdapter
                recyclerViewUni.setAdapter(iptsAdapter);
            }
        });

        matricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the ipts button is clicked, set its text color to black and the ipta button's text color to gray
                matricButton.setTextColor(Color.BLACK);
                iptaButton.setTextColor(Color.GRAY);
                iptsButton.setTextColor(Color.GRAY);

                searchView.setQuery("", false);

                // Switch the RecyclerView's adapter to the iptsAdapter
                recyclerViewUni.setAdapter(matricAdapter);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }

            private void filter(String query) {
                ((Know_Uni_CustomAdapter) recyclerViewUni.getAdapter()).getFilter().filter(query);
            }
        });



        return view;
    }


}