package nlu.edu.vn.ecommerce.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import nlu.edu.vn.ecommerce.activities.AllProductActivity;
import nlu.edu.vn.ecommerce.adapters.CategoryAdapter;
import nlu.edu.vn.ecommerce.adapters.NewProductAdapter;
import nlu.edu.vn.ecommerce.adapters.PopularProductAdapter;
import nlu.edu.vn.ecommerce.models.CategoryModel;
import nlu.edu.vn.ecommerce.models.NewProductModel;
import nlu.edu.vn.ecommerce.models.PopularProductModel;


public class HomeFragment extends Fragment {
    private ProgressDialog progressDialog;
    private LinearLayout homeLinerLayout;

    private RecyclerView categoryRecyclerView;
    //new product recyclerview
    private RecyclerView newProductRecyclerView;
    private List<NewProductModel> newProductModels;
    private NewProductAdapter newProductAdapter;
    // popular product recyclerview
    private RecyclerView popularProductRecyclerView;
    private List<PopularProductModel> popularProductModels;
    private PopularProductAdapter popularProductAdapter;

    // Category recyclerview
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModels;
    // fireStore
    private FirebaseFirestore db;
    private TextView categoryShowAll;
    private TextView popularShowAll;
    private TextView newProductShowAll;


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mapping(root);
        homeLinerLayout.setVisibility(View.GONE);
        // image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.board, "Discount On Shoes Items 1", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board2, "Discount On Shoes Items 2", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.board3, "Discount On Shoes Items 3", ScaleTypes.CENTER_CROP));
        // Progress Dialog
        progressDialog.setTitle("Welcome To My App Ecommerce App");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        imageSlider.setImageList(slideModels);

        // Category recyclerview
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
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
                                homeLinerLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            }
                        } else {
                            Toast.makeText(getActivity(),"" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //New Product recyclerview
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductAdapter = new NewProductAdapter(getContext(),newProductModels);
        newProductRecyclerView.setAdapter(newProductAdapter);
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductModel newProductModel = document.toObject(NewProductModel.class);
                                newProductModels.add(newProductModel);
                                newProductAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"" + task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        //Popular Product recyclerview
        popularProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductAdapter = new PopularProductAdapter(getContext(),popularProductModels);
        popularProductRecyclerView.setAdapter(popularProductAdapter);
        db.collection("PopularProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                                popularProductModels.add(popularProductModel);
                                popularProductAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"" + task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        categoryShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProductActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProductActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProductActivity.class);
                startActivity(intent);
            }
        });




        return root;
    }

    private void mapping( View root) {
        progressDialog = new ProgressDialog(getActivity());
        db = FirebaseFirestore.getInstance();

        categoryRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView = root.findViewById(R.id.new_product_rec);
        popularProductRecyclerView = root.findViewById(R.id.popular_rec);
        homeLinerLayout = root.findViewById(R.id.home_layout);
        categoryShowAll = root.findViewById(R.id.category_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);

        categoryModels = new ArrayList<>();
        newProductModels = new ArrayList<>();
        popularProductModels = new ArrayList<>();
    }
}