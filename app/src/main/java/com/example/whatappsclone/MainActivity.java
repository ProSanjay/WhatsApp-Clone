package com.example.whatappsclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatappsclone.databinding.ActivityMainBinding;
import com.example.whatappsclone.fragements.FragementAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        FragmentManager fm=getSupportFragmentManager();
        FragementAdapter adapter=new FragementAdapter(fm,getLifecycle());
        binding.viewPager.setAdapter(adapter);
        binding.tab.addTab(binding.tab.newTab().setText("CHAT"));
        binding.tab.addTab(binding.tab.newTab().setText("STATUS"));
        binding.tab.addTab(binding.tab.newTab().setText("CALLS"));

        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tab.selectTab(binding.tab.getTabAt(position));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
            {
                Toast.makeText(this, "you clicked on setting", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.logout:
            {
                auth.signOut();
                startActivity(new Intent(MainActivity.this,SignIn.class));
                break;
            }
            case R.id.groupChat:
            {
                startActivity(new Intent(MainActivity.this,GroupChat.class));
                break;
            }
        }
        return true;
    }
}