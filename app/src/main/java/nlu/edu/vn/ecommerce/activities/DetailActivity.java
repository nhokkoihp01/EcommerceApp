package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.models.AllProductModel;
import nlu.edu.vn.ecommerce.models.NewProductModel;
import nlu.edu.vn.ecommerce.models.PopularProductModel;

public class DetailActivity extends AppCompatActivity {
    private int totalQuantity = 1;
    private int totalPrice = 0;
    private ImageView detailImage;
    private TextView description;
    private TextView name;
    private TextView rating;
    private TextView quantity;
    private TextView price;
    private ImageView btnPlus;
    private ImageView btnRemove;
    private Button btnAddCart;
    private Button btnBuy;
    private Toolbar detailedToolbar;
    private NewProductModel newProductModel = null;
    private PopularProductModel popularProductModel = null;
    private AllProductModel allProductModel = null;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mapping();
        actionBar();
        final Object obj = getIntent().getSerializableExtra("detailed");
        final Object objPopularProduct = getIntent().getSerializableExtra("popularDetailed");
        final Object objAllProduct = getIntent().getSerializableExtra("allProduct");

        if (objAllProduct instanceof AllProductModel) {
            allProductModel = (AllProductModel) objAllProduct;

        }
        if (objPopularProduct instanceof PopularProductModel) {
            popularProductModel = (PopularProductModel) objPopularProduct;

        }
        if (obj instanceof NewProductModel) {
            newProductModel = (NewProductModel) obj;

        }
        if (allProductModel != null) {
            Glide.with(getApplicationContext()).load(allProductModel.getImg_url()).into(detailImage);
            name.setText(allProductModel.getName());
            description.setText(allProductModel.getDescription());
            rating.setText(allProductModel.getRating());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int priceFormat = allProductModel.getPrice();
            price.setText(decimalFormat.format(priceFormat) + "đ");

            totalPrice = allProductModel.getPrice() * totalQuantity;
        }
        if (popularProductModel != null) {
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailImage);
            name.setText(popularProductModel.getName());
            description.setText(popularProductModel.getDescription());
            rating.setText(popularProductModel.getRating());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int priceFormat = popularProductModel.getPrice();
            price.setText(decimalFormat.format(priceFormat) + "đ");

            totalPrice = popularProductModel.getPrice() * totalQuantity;
        }

        if (newProductModel != null) {
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailImage);
            name.setText(newProductModel.getName());
            description.setText(newProductModel.getDescription());
            rating.setText(newProductModel.getRating());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int priceFormat = newProductModel.getPrice();
            price.setText(decimalFormat.format(priceFormat) + "đ");

            totalPrice = newProductModel.getPrice() * totalQuantity;
        }
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(newProductModel != null){
                        totalPrice = newProductModel.getPrice() * totalQuantity;
                    }
                    if(popularProductModel != null){
                        totalPrice = popularProductModel.getPrice() * totalQuantity;
                    }
                    if(allProductModel != null){
                        totalPrice = allProductModel.getPrice() * totalQuantity;
                    }

                }

            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,AddressActivity.class);
                if(newProductModel != null){
                    intent.putExtra("item",newProductModel);
                }
                if(popularProductModel != null){
                    intent.putExtra("item",popularProductModel);
                }
                if(allProductModel != null){
                    intent.putExtra("item",allProductModel);
                }
                startActivity(intent);

            }
        });
    }

    private void actionBar() {
        setSupportActionBar(detailedToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addToCart() {
        String saveCurrentTime;
        String saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);

        firebaseFirestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailActivity.this, "Add to a cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });



    }


    private void mapping() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        detailedToolbar = findViewById(R.id.detailed_toolbar);
        detailImage = findViewById(R.id.detail_img);
        description = findViewById(R.id.detail_description);
        name = findViewById(R.id.detail_name);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        btnPlus = findViewById(R.id.btn_plus);
        btnRemove = findViewById(R.id.btn_subtract);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnBuy = findViewById(R.id.btn_buy_now);

    }

}