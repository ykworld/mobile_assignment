package com.yk.mobileassignment.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yk.mobileassignment.R;
import com.yk.mobileassignment.databinding.FragmentCityListBinding;
import com.yk.mobileassignment.model.City;
import com.yk.mobileassignment.viewmodel.MainViewModel;

import java.util.List;

public class CityListFragment extends Fragment {
    private FragmentCityListBinding binding;
    private CityItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MainViewModel viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        adapter = new CityItemAdapter(R.layout.city_item_list, viewModel);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if (cities != null) {
                    adapter.setItemList(cities);
                }
            }
        });
    }
}
