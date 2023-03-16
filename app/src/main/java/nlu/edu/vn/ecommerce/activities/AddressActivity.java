package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.adapters.AddressAdapter;
import nlu.edu.vn.ecommerce.models.AddressModel;
import nlu.edu.vn.ecommerce.models.AllProductModel;
import nlu.edu.vn.ecommerce.models.CartModel;
import nlu.edu.vn.ecommerce.models.NewProductModel;
import nlu.edu.vn.ecommerce.models.PopularProductModel;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private Toolbar addressToolbar;
    private RecyclerView addressRecyclerView;
    private Button btnAddAddress;
    private Button btnChoosePayment;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String mAddress = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mapping();
        actionBar();
        //get data from detailed activity
        Object obj = getIntent().getSerializableExtra("item");
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelList, this);
        addressRecyclerView.setAdapter(addressAdapter);
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddressModel addressModel = document.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
        btnChoosePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = 0.0;
                if(obj instanceof NewProductModel){
                    NewProductModel newProductModel = (NewProductModel) obj;
                    amount = newProductModel.getPrice();
                }
                if(obj instanceof PopularProductModel){
                    PopularProductModel popularProductModel = (PopularProductModel) obj;
                    amount = popularProductModel.getPrice();
                }
                if(obj instanceof AllProductModel){
                    AllProductModel allProductModel = (AllProductModel) obj;
                    amount = allProductModel.getPrice();
                }
                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(addressToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mapping() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        addressToolbar = findViewById(R.id.address_toolbar);
        addressRecyclerView = findViewById(R.id.address_recycler);
        btnAddAddress = findViewById(R.id.add_address_btn);
        btnChoosePayment = findViewById(R.id.payment_btn);
        addressModelList = new ArrayList<>();
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}