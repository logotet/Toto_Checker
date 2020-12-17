package com.logotet.totochecker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.logotet.totochecker.databinding.FragmentAddBinding;
import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.models.AddNumbersFragmentViewModel;
import com.logotet.totochecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddNumbersFragment extends DialogFragment {

    private FragmentAddBinding binding;
    private AddNumbersFragmentViewModel viewModel;
    private List<String> userNumbers;
    private String category;
    private OnDataSavedListener onDataSavedListener;

    private final static String ARG_ID = "id";
    private final static String ARG_LIST = "list";
    private final static String ARG_CATEGORY = "category";

    public static AddNumbersFragment newInstance(ArrayList<String> values, String category, int id) {
        AddNumbersFragment fragment = new AddNumbersFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_LIST, values);
        args.putString(ARG_CATEGORY, category);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 3500);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddNumbersFragmentViewModel.class);

        List<String> valuesArg = getArguments().getStringArrayList(ARG_LIST);
        String categoryArg = getArguments().getString(ARG_CATEGORY);
        int idArg = getArguments().getInt(ARG_ID);

        boolean isEditMode = valuesArg != null && categoryArg != null;

        if (isEditMode) {
            category = categoryArg;
            int count = 6;
            if (valuesArg.size() == 5) {
                binding.edtUserInput.edtSixthBall.setVisibility(View.INVISIBLE);
                count = 5;
            }
            EditText[] userTexts = getEditTextArray();
            for (int i = 0; i < count; i++) {
                userTexts[i].setText(valuesArg.get(i));
            }

            binding.layoutCategories.setGravity(Gravity.CENTER);
            switch (category){
                case Constants.SIX49:
                    binding.btn49.setEnabled(false);
                    binding.btn35.setVisibility(View.GONE);
                    binding.btn42.setVisibility(View.GONE);

                    break;
                case Constants.FIVE35:
                    binding.btn35.setEnabled(false);
                    binding.btn49.setVisibility(View.GONE);
                    binding.btn42.setVisibility(View.GONE);
                    break;
                case Constants.SIX42:
                    binding.btn42.setEnabled(false);
                    binding.btn49.setVisibility(View.GONE);
                    binding.btn35.setVisibility(View.GONE);
                    break;
            }
        } else {

            binding.btn49.setOnTouchListener((view49, motionEvent) -> {
                AddNumbersFragment.this.toggleBtnState(binding.btn49, binding.btn35, binding.btn42);
                binding.edtUserInput.edtSixthBall.setVisibility(View.VISIBLE);
                category = binding.btn49.getText().toString();
                return true;
            });

            binding.btn35.setOnTouchListener((view35, motionEvent) -> {
                AddNumbersFragment.this.toggleBtnState(binding.btn35, binding.btn49, binding.btn42);
                binding.edtUserInput.edtSixthBall.setVisibility(View.GONE);
                category = binding.btn35.getText().toString();
                return true;
            });

            binding.btn42.setOnTouchListener((view42, motionEvent) -> {
                AddNumbersFragment.this.toggleBtnState(binding.btn42, binding.btn35, binding.btn49);
                binding.edtUserInput.edtSixthBall.setVisibility(View.VISIBLE);
                category = binding.btn42.getText().toString();
                return true;
            });
        }

        binding.btnOk.setOnClickListener(viewOk -> {
            setUserNumbers();
            NumbersEntity entity = convertToEntity(userNumbers, category);
            entity.setId(idArg);
            if(isEditMode){
                //@Update worked only after the id of the entity from the parent fragment was passed and set to the updated entity
                viewModel.updateEntity(entity);
            }else {
                viewModel.insertNumbersIntoDb(entity);
            }
            onDataSavedListener = (OnDataSavedListener) getTargetFragment();
            onDataSavedListener.onDataSaved(entity, category);
            Toast.makeText(getContext(), userNumbers.toString() + category, Toast.LENGTH_LONG).show();
            dismiss();
        });

        binding.btnCancel.setOnClickListener(viewCancel -> dismiss());
    }

    private NumbersEntity convertToEntity(List<String> numbers, String category) {
        return new NumbersEntity(numbers, category);
    }

    private void setUserNumbers() {
        EditText[] userTexts = getEditTextArray();
        userNumbers = new ArrayList<>();
        for (EditText userText : userTexts) {
            String userInput = userText.getText().toString();
//            int inputAsInt = Integer.parseInt(userInput);
//            maxInput = Integer.parseInt(title.substring(4));
//            TODO: Add validation as this validation provides only false
//            if(Utils.isValidInput(inputAsInt, maxInput) && !userNumbers.contains(userInput))
            userNumbers.add(userInput);
        }
    }

    private EditText[] getEditTextArray() {
        return new EditText[]{binding.edtUserInput.edtBallValueOne,
                binding.edtUserInput.edtBallValueTwo,
                binding.edtUserInput.edtBallValueThree,
                binding.edtUserInput.edtBallValueFour,
                binding.edtUserInput.edtBallValueFive,
                binding.edtUserInput.edtBallValueSix};
    }

    private void toggleBtnState(Button btnPressed, Button btnNotPressedOne, Button btnNotPressedTwo) {
        btnPressed.setPressed(true);
        btnPressed.setFocusable(true);
        btnNotPressedOne.setPressed(false);
        btnNotPressedTwo.setPressed(false);
    }

    public interface OnDataSavedListener {
        void onDataSaved(NumbersEntity entity, String category);
    }
}
