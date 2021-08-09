package com.example.whatappsclone.fragements;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragementAdapter extends FragmentStateAdapter {
    public FragementAdapter(@NonNull FragmentManager fragmentManager, @NonNull  Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:return new statusFragement();
            case 2:return new CallsFragements();

        }
return new ChatFragement();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
