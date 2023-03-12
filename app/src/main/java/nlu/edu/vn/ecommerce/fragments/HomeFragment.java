package nlu.edu.vn.ecommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import nlu.edu.vn.ecommerce.R;


public class HomeFragment extends Fragment {

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.board, "Discount On Shoes Items 1", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board2, "Discount On Shoes Items 2", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board3, "Discount On Shoes Items 3", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
        return root;
    }
}