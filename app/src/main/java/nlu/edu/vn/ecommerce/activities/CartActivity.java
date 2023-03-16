package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.adapters.CartAdapter;
import nlu.edu.vn.ecommerce.models.AllProductModel;
import nlu.edu.vn.ecommerce.models.CartModel;

public class CartActivity extends AppCompatActivity {
    private Toolbar cartToolbar;
    private RecyclerView cartRecyclerView;
    private List<CartModel> cartModelList;
    private CartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private TextView overAllAMount;
    private int overAllTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mapping();
        actionBar();
        //get data from cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartModelList);
        cartRecyclerView.setAdapter(cartAdapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CartModel cartModel = document.toObject(CartModel.class);
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overAllAMount.setText("Tổng tiền: " + decimalFormat.format(totalBill) +"đ");
        }
    };


    private void actionBar() {
        setSupportActionBar(cartToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        cartToolbar = findViewById(R.id.cart_toolbar);
        cartRecyclerView = findViewById(R.id.cart_rec);
        overAllAMount = findViewById(R.id.total_amount_price);
        cartModelList = new ArrayList<>();

    }
}