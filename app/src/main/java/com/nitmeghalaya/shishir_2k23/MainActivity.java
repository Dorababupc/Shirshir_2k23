package com.nitmeghalaya.shishir_2k23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nitmeghalaya.shishir_2k23.Event.EventFragment;
import com.nitmeghalaya.shishir_2k23.Home.HomeFragment;
import com.nitmeghalaya.shishir_2k23.Shop.ShopFragment;
import com.nitmeghalaya.shishir_2k23.Sponsors.SponsorsFragment;
import com.nitmeghalaya.shishir_2k23.Team.TeamFragment;
import com.nitmeghalaya.shishir_2k23.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    //private FirebaseMessaging fFirebaseMessaging;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bottom_nav_color_state_list));


        replaceFragment(new HomeFragment());

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String someValue = extras.getString("someKey"); // extract any necessary data
//
//            // create a new fragment instance with any necessary data
//            EventFragment eventFragment = EventFragment.newInstance(someValue);
//
//            // start a new fragment transaction and display the fragment
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frame_layout, eventFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }

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
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                    }
                });



    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.myApp:
                // Handle menu item one click
                Intent intent = new Intent(MainActivity.this,MyProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}