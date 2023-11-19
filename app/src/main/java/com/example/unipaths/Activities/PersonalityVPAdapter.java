package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class PersonalityVPAdapter extends FragmentStateAdapter {
    public PersonalityVPAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    //Set up fragments in the viewPager2
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PersonalityGuidanceFragment();
            case 1:
                return new PersonalityMyselfFragment();
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
