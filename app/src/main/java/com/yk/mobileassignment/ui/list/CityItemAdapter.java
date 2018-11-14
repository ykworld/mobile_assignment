package com.yk.mobileassignment.ui.list;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yk.mobileassignment.BR;
import com.yk.mobileassignment.model.City;
import com.yk.mobileassignment.model.Coord;
import com.yk.mobileassignment.viewmodel.MainViewModel;

import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.ItemViewHolder> {

    private List<City> itemList;
    private int layoutId;
    private MainViewModel viewModel;

    public CityItemAdapter(@LayoutRes int layoutId, MainViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    public void setItemList(List<City> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        City city = itemList.get(position);
        Coord coord = city.getCoord();
        String cityName = itemList.get(position).getDisplayName();
        holder.bind(viewModel, coord, cityName);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MainViewModel viewModel, Coord coord, String cityName) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.coord, coord);
            binding.setVariable(BR.city, cityName);
            binding.executePendingBindings();
        }

    }
}
