package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.adapters.AllProductAdapter;
import nlu.edu.vn.ecommerce.models.AllProductModel;


public class AllProductActivity extends AppCompatActivity {
    private RecyclerView allProductRecyclerView;
    private AllProductAdapter allProductAdapter;
    private List<AllProductModel> allProductModels;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        mapping();
        String type = getIntent().getStringExtra("type");

        allProductRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        allProductAdapter = new AllProductAdapter(this,allProductModels);
        allProductRecyclerView.setAdapter(allProductAdapter);

        if(type == null || type.isEmpty()){
            db.collection("AllProduct")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    AllProductModel allProductModel = document.toObject(AllProductModel.class);
                                    allProductModels.add(allProductModel);
                                    allProductAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"" + task.getException(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("smart_phone")){
            db.collection("AllProduct")
                    .whereEqualTo("type","smart_phone")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    AllProductModel allProductModel = document.toObject(AllProductModel.class);
                                    allProductModels.add(allProductModel);
                                    allProductAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"" + task.getException(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("style_female")){
            db.collection("AllProduct")
                    .whereEqualTo("type","style_female")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    AllProductModel allProductModel = document.toObject(AllProductModel.class);
                                    allProductModels.add(allProductModel);
                                    allProductAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"" + task.getException(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


    }

    private void mapping() {
        db = FirebaseFirestore.getInstance();
        allProductRecyclerView = findViewById(R.id.all_product_rec);

        allProductModels = new ArrayList<>();
    }
}