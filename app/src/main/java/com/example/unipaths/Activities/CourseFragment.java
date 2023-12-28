package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    List<Item_requirement> itemsCourses = new ArrayList<>();
    List<Item_courses> itemsCou = new ArrayList<>();
    Know_Require_CuntomAdapter courseAdapter;
    Know_Cou_CustomAdapter couAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
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
        View view = inflater.inflate(R.layout.fragment_course, container, false);
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


        RecyclerView recyclerViewCourse = view.findViewById(R.id.recycler_view_Cou);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        RecyclerView recyclerViewCou = view.findViewById(R.id.recycler_view_Cou);
        recyclerViewCou.setLayoutManager(new LinearLayoutManager(getActivity()));
        couAdapter = new Know_Cou_CustomAdapter(getActivity(), itemsCou);
        recyclerViewCou.setAdapter(couAdapter);

        SearchView searchViewCou = view.findViewById(R.id.search_viewCou);
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
        return view;
    }
}