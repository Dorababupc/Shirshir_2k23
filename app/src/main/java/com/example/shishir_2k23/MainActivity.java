package com.example.shishir_2k23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.shishir_2k23.Event.EventFragment;
import com.example.shishir_2k23.Home.HomeFragment;
import com.example.shishir_2k23.Shop.ShopFragment;
import com.example.shishir_2k23.Sponsors.SponsorsFragment;
import com.example.shishir_2k23.Team.TeamFragment;
import com.example.shishir_2k23.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.events:
                    replaceFragment(new EventFragment());
                    break;
                case R.id.team:
                    replaceFragment(new TeamFragment());
                    break;
                case R.id.sponsors:
                    replaceFragment(new SponsorsFragment());
                    break;
                case R.id.shop:
                    replaceFragment(new ShopFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}