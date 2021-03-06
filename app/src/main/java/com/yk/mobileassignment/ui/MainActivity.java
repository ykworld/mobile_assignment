package com.yk.mobileassignment.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yk.mobileassignment.R;
import com.yk.mobileassignment.constants.Constants;
import com.yk.mobileassignment.databinding.ActivityMainBinding;
import com.yk.mobileassignment.model.Coord;
import com.yk.mobileassignment.ui.list.CityListFragment;
import com.yk.mobileassignment.ui.map.MapFragment;
import com.yk.mobileassignment.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private MainViewModel viewModel;
    private FragmentManager manager;
    private CityListFragment cityListFragment;
    private MapFragment mapFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFragment();
        setUpBinding(savedInstanceState);
    }

    private void setUpBinding(Bundle savedInstanceState) {
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);

        setUpUpdateMap();
    }

    private void setUpUpdateMap() {
        viewModel.getCoord().observe(this, new Observer<Coord>() {
            @Override
            public void onChanged(@Nullable Coord coord) {
                if (coord != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_COORD, coord);
                    mapFragment.setArguments(bundle);

                    FragmentTransaction ft = manager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.main_container, mapFragment);
                    ft.commit();
                }
            }
        });
    }

    private void setUpFragment() {
        manager = getSupportFragmentManager();
        cityListFragment = new CityListFragment();
        mapFragment = new MapFragment();

        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.main_container, cityListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_main_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        viewModel.onTextChanged(s);
        return false;
    }
}
