package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class PersonalityVPAdapter extends FragmentStateAdapter {
    private boolean hasTakenTest;
    public PersonalityVPAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    public PersonalityVPAdapter(@NonNull FragmentActivity fragmentActivity, boolean hasTakenTest) {
        super(fragmentActivity);
        this.hasTakenTest = hasTakenTest;
    }

    //Set up fragments in the viewPager2
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PersonalityGuidanceFragment();
            case 1:
                if(hasTakenTest) {
                    return new PersonalityMyselfFragment();
                }
                else return new PersonalityMyselfEmptyFragment();
            default:
                return null;
        }
    }
    //The amount of pages of viewPager2
    @Override
    public int getItemCount() {
        return 2;
    }

}
