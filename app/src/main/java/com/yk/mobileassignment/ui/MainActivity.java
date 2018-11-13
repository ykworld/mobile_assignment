package com.yk.mobileassignment.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yk.mobileassignment.R;
import com.yk.mobileassignment.databinding.ActivityMainBinding;
import com.yk.mobileassignment.ui.list.CityListFragment;
import com.yk.mobileassignment.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private FragmentManager manager;
    private CityListFragment cityListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBinding(savedInstanceState);
        setUpFragment();
    }

    private void setUpBinding(Bundle savedInstanceState) {
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);
    }

    private void setUpFragment() {
        manager = getSupportFragmentManager();
        cityListFragment = new CityListFragment();

        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.main_container, cityListFragment);
        ft.commit();
    }

}
