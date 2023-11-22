package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class Knowledge_Universities extends AppCompatActivity {

    List<Item_requirement> itemsUni = new ArrayList<>();
    List<Item_requirement> itemsPreu = new ArrayList<>();
    List<Item_universities> itemsIPTA = new ArrayList<>();
    List<Item_universities> itemsIPTS = new ArrayList<>();
    List<Item_universities> itemsMatric = new ArrayList<>();
    List<Item_requirement> itemsCourses = new ArrayList<>();
    List<Item_courses> itemsCou = new ArrayList<>();
    // Define your adapters
    Know_Require_CuntomAdapter uniAdapter;
    Know_Require_CuntomAdapter preuAdapter;
    Know_Uni_CustomAdapter iptaAdapter;
    Know_Uni_CustomAdapter iptsAdapter;
    Know_Uni_CustomAdapter matricAdapter;
    Know_Require_CuntomAdapter courseAdapter;
    Know_Cou_CustomAdapter couAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_universities);

        //Switch between requirement, universities and courses
        Button requireButton = findViewById(R.id.requirement);
        Button uniButton = findViewById(R.id.universities);
        Button courseButton = findViewById(R.id.courses);

        ConstraintLayout layoutRequirement = findViewById(R.id.require);
        ConstraintLayout layoutUniversities = findViewById(R.id.uni);
        ConstraintLayout layoutCourses = findViewById(R.id.cour);

        layoutUniversities.setVisibility(View.GONE);
        layoutCourses.setVisibility(View.GONE);
        layoutRequirement.setVisibility(View.VISIBLE);
        requireButton.setTextColor(Color.BLACK);
        uniButton.setTextColor(Color.GRAY);
        courseButton.setTextColor(Color.GRAY);

        requireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireButton.setTextColor(Color.BLACK);
                uniButton.setTextColor(Color.GRAY);
                courseButton.setTextColor(Color.GRAY);
                layoutUniversities.setVisibility(View.GONE);
                layoutCourses.setVisibility(View.GONE);
                layoutRequirement.setVisibility(View.VISIBLE);
            }
        });

        uniButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniButton.setTextColor(Color.BLACK);
                requireButton.setTextColor(Color.GRAY);
                courseButton.setTextColor(Color.GRAY);
                layoutRequirement.setVisibility(View.GONE);
                layoutCourses.setVisibility(View.GONE);
                layoutUniversities.setVisibility(View.VISIBLE);
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseButton.setTextColor(Color.BLACK);
                requireButton.setTextColor(Color.GRAY);
                uniButton.setTextColor(Color.GRAY);
                layoutRequirement.setVisibility(View.GONE);
                layoutUniversities.setVisibility(View.GONE);
                layoutCourses.setVisibility(View.VISIBLE);
            }
        });


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


        RecyclerView recyclerViewReq = findViewById(R.id.list_requirementUni);
        recyclerViewReq.setLayoutManager(new LinearLayoutManager(this));
        uniAdapter = new Know_Require_CuntomAdapter(itemsUni);
        preuAdapter = new Know_Require_CuntomAdapter(itemsPreu);
        recyclerViewReq.setAdapter(uniAdapter);

        // Initialize your two buttons
        final Button uniiButton = findViewById(R.id.unirequire_btn);
        final Button preuButton = findViewById(R.id.preurequire_btn);

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

        //------------------------------------------------------------------------------------------------

        //Universities
        //------------------------------------------------------------------------------------------------

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

        RecyclerView recyclerViewUni = findViewById(R.id.recycler_view);
        recyclerViewUni.setLayoutManager(new LinearLayoutManager(this));
        iptaAdapter = new Know_Uni_CustomAdapter(this, itemsIPTA);
        iptsAdapter = new Know_Uni_CustomAdapter(this, itemsIPTS);
        matricAdapter = new Know_Uni_CustomAdapter(this, itemsMatric);

        // Set the initial adapter
        recyclerViewUni.setAdapter(iptaAdapter);

        // Initialize your three buttons
        final Button iptaButton = findViewById(R.id.ipta_btn);
        final Button iptsButton = findViewById(R.id.ipts_btn);
        final Button matricButton = findViewById(R.id.matrik_btn);

        // Set the initial text colors
        iptaButton.setTextColor(Color.BLACK);
        iptsButton.setTextColor(Color.GRAY);
        matricButton.setTextColor(Color.GRAY);

        // Set up the searchable functionality
        // Assuming you have a SearchView with the id 'search_view'

        SearchView searchView = findViewById(R.id.search_view);
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

        // Get the root view of your layout
        View rootView = findViewById(android.R.id.content);


        //------------------------------------------------------------------------------------------------

        //Courses
        //------------------------------------------------------------------------------------------------

        itemsCourses.add(new Item_requirement("Actuarial Science",
                "https://youtu.be/hNpeMG-S-d8?si=MCtVDwS3Orbc4uBc" +
                        "\nhttps://youtu.be/KI3QoG5pkeU?si=usszsp3QVOWQ0ibG" +
                        "\nhttps://youtu.be/dKQySRFkEmY?si=ebQga1Z4HsILChuN"));
        itemsCourses.add(new Item_requirement("Agriculture",
                "https://youtu.be/vKYVV1jrjMM?si=pPUzyaRUDEAyR9Ss" +
                        "\nhttps://youtu.be/qS-UWCbM6Ic?si=mE74Klp8p2jbzMDX" +
                        "\nhttps://youtu.be/QdgIjjqdgHA?si=H1NU1fs3xZCiNepZ"));
        itemsCourses.add(new Item_requirement("Art",
                "https://youtu.be/Wg-w7s-vJPA?si=oeIMdTEc4n8Kjsjl" +
                        "\nhttps://youtu.be/8lTxWltIjmc?si=1GXzxFckp_LaYSOF" +
                        "\nhttps://youtu.be/ix8I9zbMJqg?si=H7reEfKczWWeEbFE"));
        itemsCourses.add(new Item_requirement("Biomedical Science",
                "https://youtu.be/Lnh5cJa4DPA?si=LYRJ_gG9z-6oJndJ" +
                        "\nhttps://youtu.be/kziZ3Z2Yqao?si=bwstrjmslCXel401" +
                        "\nhttps://youtu.be/TfoOunFyxmA?si=itTyaLhUT7f6JZST"));
        itemsCourses.add(new Item_requirement("Business",
                "https://youtu.be/MiBWVNhFJLI?si=vkLvZiyEgATTzXE1" +
                        "\nhttps://youtu.be/0yWAWmeY6hk?si=5Qqm6PaKVJXXvITR" +
                        "\nhttps://youtu.be/OqWpF0Kgyic?si=ucAyX5kLypajUlgp"));


        RecyclerView recyclerViewCourse = findViewById(R.id.recycler_view_Cou);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new Know_Require_CuntomAdapter(itemsCourses);
        recyclerViewCourse.setAdapter(courseAdapter);

        //------------------------------------------
        itemsCou.add(new Item_courses("Accounting", "https://youtu.be/hM085OTD2U8?si=3htPSt5hdNWbZoRg"));
        itemsCou.add(new Item_courses("Actuarial Science", "https://youtu.be/hNpeMG-S-d8?si=MCtVDwS3Orbc4uBc"));
        itemsCou.add(new Item_courses("Agriculture", "https://youtu.be/vKYVV1jrjMM?si=pPUzyaRUDEAyR9Ss"));
        itemsCou.add(new Item_courses("Architecture", "https://youtu.be/vWX5ZXoFktM?si=zaXBD_9naZMiAlWQ"));
        itemsCou.add(new Item_courses("Art", "https://youtu.be/Wg-w7s-vJPA?si=oeIMdTEc4n8Kjsjl"));
        itemsCou.add(new Item_courses("Biology", "https://youtu.be/Ez9qK_Htw_0?si=lncwLryyRvhKcvBD"));
        itemsCou.add(new Item_courses("Biomedical Engineering", "https://youtu.be/7ZnTD5ucZsA?si=VhgQfhgWQKulsP5t"));
        itemsCou.add(new Item_courses("Biomedical Science", "https://youtu.be/Lnh5cJa4DPA?si=LYRJ_gG9z-6oJndJ"));
        itemsCou.add(new Item_courses("Business", "https://youtu.be/MiBWVNhFJLI?si=vkLvZiyEgATTzXE1"));
        itemsCou.add(new Item_courses("Chemical Engineering", "https://youtu.be/dcaZ0ktW028?si=v0wgrzBQ_k1wyPNq"));
        itemsCou.add(new Item_courses("Civil Engineering", "https://youtu.be/Rt8H_FoIkB0?si=Vu3DCArt72wpCKcI"));
        itemsCou.add(new Item_courses("Communication", "https://youtu.be/t2nkQSEsP8w?si=r9PGKYxNUgpk2_bG"));
        itemsCou.add(new Item_courses("Computer Science", "https://youtu.be/k0GlqFrRQI0?si=-A5mqIoVN60DLyH7"));
        itemsCou.add(new Item_courses("Dentistry", "https://youtu.be/XfFtk5Fp41g?si=ZHhueFyEO-zXFir-"));
        itemsCou.add(new Item_courses("Economics", "https://youtu.be/zFKnZ3L4R5Y?si=iK-yAfdw2kAUa0oG"));
        itemsCou.add(new Item_courses("Education", "https://youtu.be/5EgL3jP5FSs?si=eqcMWzwuNwbiOeBD"));
        itemsCou.add(new Item_courses("Electrical Engineering", "https://youtu.be/YXPnvV8Ia0M?si=LPNAYYRvT4lRRUOI"));
        itemsCou.add(new Item_courses("Engineering", "https://youtu.be/9Z4wUKBkD94?si=ErJElNr6-pOn1NMW"));
        itemsCou.add(new Item_courses("Environmental Science", "https://youtu.be/qgslftDSSXo?si=iIgqbtmXsmiwJ3Sj"));
        itemsCou.add(new Item_courses("Event Management", "https://youtu.be/jX5j4guxnSE?si=YLDA3Lwyh_OKy7S-"));
        itemsCou.add(new Item_courses("Finance", "https://youtu.be/qXCGakevXIo?si=dGUUfzZR3Z21J-K2"));
        itemsCou.add(new Item_courses("Food Science", "https://youtu.be/80WyjDJgJ_M?si=Em9B090rD1QT9U2y"));
        itemsCou.add(new Item_courses("Game Development", "https://youtu.be/cUvjDOWYNY8?si=7cZTTP8sCdAlfRaq"));
        itemsCou.add(new Item_courses("Geology", "https://youtu.be/SPXNMBGE6tc?si=5N2kuNj_AJ32SpvO"));
        itemsCou.add(new Item_courses("Graphic Design", "https://youtu.be/RWxRyjqsBoE?si=qRdzng7cucG6xJKS"));
        itemsCou.add(new Item_courses("History", "https://youtu.be/fYteaPgd5ek?si=jvqZeWo5HeYqLyZ7"));
        itemsCou.add(new Item_courses("Interior Design", "https://youtu.be/XHA7qROUKhw?si=XY_q5_P8VBqpDUO3"));
        itemsCou.add(new Item_courses("Law", "https://youtu.be/fSSHub4Tew8?si=448xSPboQMUeY0UA"));
        itemsCou.add(new Item_courses("Language and Linguistics", "https://youtu.be/XswmHGuJATg?si=ysRNHyfrkDcGZiM7"));
        itemsCou.add(new Item_courses("Marketing", "https://youtu.be/_4x_57Ts7DQ?si=2xXtmjzvgFz1ZPcJ"));
        itemsCou.add(new Item_courses("Mathematics", "https://youtu.be/wk28BSaLszo?si=PN3pP9rHwnnaSaGo"));
        itemsCou.add(new Item_courses("Mechanical Engineering", "https://youtu.be/oRAesJVaQ2Y?si=SLPSRANCs816sXXg"));
        itemsCou.add(new Item_courses("Medicine", "https://youtu.be/YdzFpjwxGZg?si=Og5wioq5lgzLDMtw"));
        itemsCou.add(new Item_courses("Music", "https://youtu.be/Qx5ROw4zHvI?si=fbnh0QwPtcTLlrEu"));
        itemsCou.add(new Item_courses("Nursing", "https://youtu.be/Z0T732VH1dk?si=Rgw4N8AJFADpvMzK"));
        itemsCou.add(new Item_courses("Nutrition", "https://youtu.be/CKZ8Eu85Qys?si=7C-StjASd-9uCl7f"));
        itemsCou.add(new Item_courses("Pharmacy", "https://youtu.be/-9_eP9y_J10?si=MWlMQtyJV06gXWuQ"));
        itemsCou.add(new Item_courses("Physics", "https://youtu.be/WttrWqdfOPM?si=MPIWXehZ4c_A5HCb"));
        itemsCou.add(new Item_courses("Piloting", "https://youtu.be/z1x_I_RSOe0?si=XeKxhc1G6x7rKxQw"));
        itemsCou.add(new Item_courses("Political Science", "https://youtu.be/KrBGBPwGTvE?si=kmrPdFKEZ-sRYx8H"));
        itemsCou.add(new Item_courses("Psychology", "https://youtu.be/_kXAsFHpUIA?si=ZDr3pUN3yz_tcsKd"));
        itemsCou.add(new Item_courses("Quantity Surveying", "https://youtu.be/CH7_eBYtItE?si=E4Z1VA3jNEcTemIH"));
        itemsCou.add(new Item_courses("Sociology", "https://youtu.be/b116qtBfum4?si=5vli1dEH22n8ie-Z"));
        itemsCou.add(new Item_courses("Sport Science", "https://youtu.be/t0zVJ8P1_Zg?si=xjATI2Qa_VZPSy-5"));
        itemsCou.add(new Item_courses("Tourism Management", "https://youtu.be/LlatZu3JxUs?si=0je8tXM1tW3a5A-d"));
        itemsCou.add(new Item_courses("Veterinary Medicine", "https://youtu.be/fEs1zTqV5E0?si=dkh4n0tQqu2pTnQ0"));

        RecyclerView recyclerViewCou = findViewById(R.id.recycler_view_Cou);
        recyclerViewCou.setLayoutManager(new LinearLayoutManager(this));
        couAdapter = new Know_Cou_CustomAdapter(this, itemsCou);
        recyclerViewCou.setAdapter(couAdapter);

        SearchView searchViewCou = findViewById(R.id.search_viewCou);
        searchViewCou.setIconifiedByDefault(false);

        searchViewCou.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                ((Know_Cou_CustomAdapter) recyclerViewCou.getAdapter()).getFilter().filter(query);
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Hide the keyboard when the root view is touched
                hideKeyboard(v);
                return false;
            }
        });
    }
// ...

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
