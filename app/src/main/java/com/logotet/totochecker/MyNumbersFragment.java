package com.logotet.totochecker;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.totochecker.adapters.CategoryAdapter;
import com.logotet.totochecker.databinding.FragmentMyNumbersBinding;
import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.models.MyNumbersFragmentViewModel;
import com.logotet.totochecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class MyNumbersFragment extends Fragment implements CategoryAdapter.CategoryHolder.OnItemsClickListener,
        AddNumbersFragment.OnDataSavedListener{

    private FragmentMyNumbersBinding binding;
    private MyNumbersFragmentViewModel viewModel;
    private CategoryAdapter adapter49;
    private CategoryAdapter adapter35;
    private CategoryAdapter adapter42;
    private List<NumbersEntity> entities49 = new ArrayList<>();
    private List<NumbersEntity> entities35 = new ArrayList<>();
    private List<NumbersEntity> entities42 = new ArrayList<>();
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_numbers, container, false);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MyNumbersFragmentViewModel.class);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constants.TITLE_MY_NUMBERS);

        binding.btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWinningNumbers();
            }
        });

        //TODO refactor the following lines into one method
        viewModel.getAllDataFromCategory(Constants.SIX49,
                values -> {
                    entities49 = values;
                    adapter49 = new CategoryAdapter(values, this);
                    binding.recViewCategoryMy49.setAdapter(adapter49);
                    binding.recViewCategoryMy49.setLayoutManager(new LinearLayoutManager(binding.recViewCategoryMy49.getContext()));
                    MyNumbersFragment.this.setViewsVisibility(entities49, binding.recViewCategoryMy49, binding.txtCategory49);
                });

        viewModel.getAllDataFromCategory(Constants.FIVE35,
                values -> {
                    entities35 = values;
                    adapter35 = new CategoryAdapter(values, this);
                    binding.recViewCategoryMy35.setAdapter(adapter35);
                    binding.recViewCategoryMy35.setLayoutManager(new LinearLayoutManager(binding.recViewCategoryMy35.getContext()));
                    setViewsVisibility(values, binding.recViewCategoryMy35, binding.txtCategory35);
                });

        viewModel.getAllDataFromCategory(Constants.SIX42,
                values -> {
                    entities42 = values;
                    adapter42 = new CategoryAdapter(values, this);
                    binding.recViewCategoryMy42.setAdapter(adapter42);
                    binding.recViewCategoryMy42.setLayoutManager(new LinearLayoutManager(binding.recViewCategoryMy42.getContext()));
                    setViewsVisibility(values, binding.recViewCategoryMy42, binding.txtCategory42);
                });
    }

    private void checkWinningNumbers() {

    }

    public void setViewsVisibility(List<NumbersEntity> entityList, RecyclerView recyclerView, TextView textView) {
        if (entityList.size() != 0) {
            binding.txtNoDataMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDeleteClicked(NumbersEntity entity) {
        String category = entity.getCategory();
        viewModel.deleteEntity(entity);
        switch (category) {
            case Constants.SIX49:
                entities49.remove(entity);
                adapter49.notifyDataSetChanged();
                break;
            case Constants.FIVE35:
                entities35.remove(entity);
                adapter35.notifyDataSetChanged();
                break;
            case Constants.SIX42:
                entities42.remove(entity);
                adapter42.notifyDataSetChanged();
                break;
        }
        Toast.makeText(getContext(), entity.getValues().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEditClicked(NumbersEntity entity, int position) {
        this.position = position;
        List<String> values = entity.getValues();
        String category = entity.getCategory();
        int id = entity.getId();
        openAddFragment((ArrayList<String>) values, category, id);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                openAddFragment(null, null, -1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddFragment(ArrayList<String> values, String category, int id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddNumbersFragment addNumbersDialogFragment = AddNumbersFragment.newInstance(values, category, id);
        addNumbersDialogFragment.setTargetFragment(MyNumbersFragment.this, 300);
        addNumbersDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onDataSaved(NumbersEntity entity, String category) {

        switch (category){
            case Constants.SIX49:
                if(position > 0){
                    entities49.set(position, entity);
                }   else {
                    entities49.add(entity);
                }
                adapter49.notifyDataSetChanged();
                break;
            case Constants.FIVE35:
                if(position > 0){
                    entities35.set(position, entity);
                }   else {
                    entities35.add(entity);
                }
                adapter35.notifyDataSetChanged();
                break;
            case Constants.SIX42:
                if(position > 0){
                    entities42.set(position, entity);
                }   else {
                    entities42.add(entity);
                }
                adapter42.notifyDataSetChanged();
                break;
        }
    }


}