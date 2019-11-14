package com.example.gohome.Organizer.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gohome.Organizer.Fragment.OrganizerAdoptFragment;
import com.example.gohome.Organizer.Fragment.OrganizerCheckFragment;
import com.example.gohome.Organizer.Fragment.OrganizerMemberFragment;
import com.example.gohome.Organizer.Fragment.OrganizerMineFragment;
import com.example.gohome.Organizer.Fragment.OrganizerOrganizationFragment;

import java.util.ArrayList;
import java.util.List;

public class OrganizerMainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    private final static int PAGER_COUNT= 5;

    public OrganizerMainViewPagerAdapter(FragmentManager fm){
        super(fm);

        fragmentList = new ArrayList<>();
        fragmentList.add(0, new OrganizerCheckFragment());
        fragmentList.add(1, new OrganizerOrganizationFragment());
        fragmentList.add(2, new OrganizerAdoptFragment());
        fragmentList.add(3, new OrganizerMemberFragment());
        fragmentList.add(4, new OrganizerMineFragment());
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("fragment position: " + position);
        try {
            return fragmentList.get(position);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}