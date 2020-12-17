package com.logotet.totochecker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.logotet.totochecker.adapters.BallAdapter;
import com.logotet.totochecker.adapters.RecyclerViewInitializer;
import com.logotet.totochecker.databinding.FragmentNumbersCategoryBinding;
import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.models.NumbersCategoryFragmentViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NumbersCategoryFragment extends Fragment {

    private FragmentNumbersCategoryBinding binding;
    private NumbersCategoryFragmentViewModel viewModel;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 13;
    private static final String NUMBERS = "params";
    private static final String NUMBERS35 = "params35Second";
    private static final String TITLE = "title";
    private List<String> winNumbers;
    private List<String> winNumbersSecond;
    private String title;
    private List<String> userNumbers;
    private int maxInput;


    public NumbersCategoryFragment() {
    }

    public static NumbersCategoryFragment newInstance(ArrayList<String> numbers, ArrayList<String> numbers35Second, String title) {
        NumbersCategoryFragment fragment = new NumbersCategoryFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(NUMBERS, numbers);
        args.putStringArrayList(NUMBERS35, numbers35Second);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers_category, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NumbersCategoryFragmentViewModel.class);


        if (getArguments() != null) {
            winNumbers = (List<String>) getArguments().get(NUMBERS);
            title = getArguments().getString(TITLE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
        RecyclerViewInitializer.initBallRecView(binding.recViewNumbers, winNumbers);

        if (getArguments().get(NUMBERS35) != null) {
            winNumbersSecond = (List<String>) getArguments().get(NUMBERS35);
            binding.edtUserInput.edtSixthBall.setVisibility(View.GONE);
            RecyclerViewInitializer.initBallRecView(binding.recViewNumbers35Second, winNumbersSecond);
            binding.recViewNumbers35Second.setVisibility(View.VISIBLE);
        }

        binding.btnCheckNumbers.setOnClickListener(view -> {
            setUserNumbers();
            viewModel.setMatchingNumbers(winNumbers, userNumbers);
            if (binding.recViewMatchingNumbers.getVisibility() != View.VISIBLE) {
                RecyclerViewInitializer.initBallRecView(binding.recViewMatchingNumbers, viewModel.getMatchingNumbers());
                binding.recViewMatchingNumbers.setVisibility(View.VISIBLE);
            } else {
                BallAdapter adapter = new BallAdapter(viewModel.getMatchingNumbers());
                binding.recViewMatchingNumbers.setAdapter(adapter);
            }
        });

        binding.btnTakePic.setOnClickListener(view -> NumbersCategoryFragment.this.startCamera());

        binding.btnConvert.setOnClickListener(view -> {
            viewModel.detectText(getContext(), ((BitmapDrawable) binding.imgText.getDrawable()).getBitmap());
            String text = viewModel.getConvertedText();
            binding.txtConvertedText.setText(text);
        });

        binding.btnSaveNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserNumbers();
                NumbersEntity entity = new NumbersEntity(userNumbers, title);
                viewModel.insertIntoDb(entity);
                Toast.makeText(getContext(), "entity inserted", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUserNumbers() {
        EditText[] userTexts = new EditText[]{binding.edtUserInput.edtBallValueOne,
                binding.edtUserInput.edtBallValueTwo,
                binding.edtUserInput.edtBallValueThree,
                binding.edtUserInput.edtBallValueFour,
                binding.edtUserInput.edtBallValueFive,
                binding.edtUserInput.edtBallValueSix};
        userNumbers = new ArrayList<>();
        for (EditText userText : userTexts) {
            String userInput = userText.getText().toString();
//            int inputAsInt = Integer.parseInt(userInput);
//            maxInput = Integer.parseInt(title.substring(4));
//            TODO: The validation provides only false
//            if(Utils.isValidInput(inputAsInt, maxInput) && !userNumbers.contains(userInput))
            userNumbers.add(userInput);
        }
    }

    public void startCamera() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private void launchImgForCropping(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1920, 1080)
                .start(getContext(), this);
    }

    private void setImage(Uri uri) {
        Glide.with(this)
                .load(uri)
                .into(binding.imgText);
    }

    private Uri getImageUri(Context context, Bitmap image) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri resultUri = null;
            try {
                resultUri = getImageUri(getContext(), imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("message", e.getMessage());
            }
            launchImgForCropping(resultUri);

            Toast.makeText(getContext(), "pic taken", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "pic not taken", Toast.LENGTH_LONG).show();
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            setImage(result.getUri());
        }
    }


}
