package com.logotet.totochecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.logotet.totochecker.adapters.RecyclerViewInitializer;
import com.logotet.totochecker.databinding.ActivityMainBinding;
import com.logotet.totochecker.models.MainActivityViewModel;
import com.logotet.totochecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private List<String> winningNumbers49;
    private List<String> winningNumbersFirst35;
    private List<String> winningNumbersSecond35;
    private List<String> winningNumbers42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        setUpNavDrawer();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        winningNumbers49 = viewModel.getWinningNumbers49();
        RecyclerViewInitializer.initBallRecView(binding.recView49, winningNumbers49);
        winningNumbersFirst35 = viewModel.getWinningNumbersFirst35();
        RecyclerViewInitializer.initBallRecView(binding.recViewFirst35, winningNumbersFirst35);
        winningNumbersSecond35 = viewModel.getWinningNumbersSecond35();
        RecyclerViewInitializer.initBallRecView(binding.recViewSecond35, winningNumbersSecond35);
        winningNumbers42 = viewModel.getWinningNumbers42();
        RecyclerViewInitializer.initBallRecView(binding.recView42, winningNumbers42);


        goToFragWithRecView(binding.recView49);
        goToFragWithRecView(binding.recViewFirst35);
        goToFragWithRecView(binding.recViewSecond35);
        goToFragWithRecView(binding.recView42);
    }

    private void goToFragWithRecView(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toggleToFragment(view.getId());
                return false;
            }
        });
    }

    private void setUpNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navDrawer.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            toggleToFragment(id);
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void toggleToFragment(int id) {
        switch (id) {
            case R.id.nav_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_49:
            case R.id.rec_view_49:
                openFragment(NumbersCategoryFragment
                        .newInstance((ArrayList<String>) winningNumbers49, null, Constants.SIX49));
                break;
            case R.id.nav_35:
            case R.id.rec_view_first_35:
            case R.id.rec_view_second_35:
                openFragment(NumbersCategoryFragment
                        .newInstance((ArrayList<String>) winningNumbersFirst35, (ArrayList<String>) winningNumbersSecond35, Constants.FIVE35));
                break;
            case R.id.nav_42:
            case R.id.rec_view_42:
                openFragment(NumbersCategoryFragment
                        .newInstance((ArrayList<String>) winningNumbers42, null, Constants.SIX42));
                break;
            case R.id.nav_my_numbers:
                openFragment(new MyNumbersFragment());
                break;
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        binding.fragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}