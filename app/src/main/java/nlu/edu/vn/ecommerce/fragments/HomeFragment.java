package nlu.edu.vn.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.adapters.CategoryAdapter;
import nlu.edu.vn.ecommerce.models.CategoryModel;


public class HomeFragment extends Fragment {
    private RecyclerView categoryRecyclerView;
    // Category recyclerview
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModels;
    // fireStore
    private FirebaseFirestore db;


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mapping(root);
        // image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.board, "Discount On Shoes Items 1", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board2, "Discount On Shoes Items 2", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board3, "Discount On Shoes Items 3", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModels);
        categoryRecyclerView.setAdapter(categoryAdapter);
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModels.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();

                            }
                        } else {

                        }
                    }
                });
        return root;
    }

    private void mapping( View root) {
        db = FirebaseFirestore.getInstance();
        categoryRecyclerView = root.findViewById(R.id.rec_category);

    }
}