package nlu.edu.vn.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.models.NewProductModel;
import nlu.edu.vn.ecommerce.models.PopularProductModel;

public class DetailActivity extends AppCompatActivity {
    private ImageView detailImage;
    private TextView description;
    private TextView name;
    private TextView rating;
    private TextView price;
    private ImageView btnPlus;
    private ImageView btnRemove;
    private NewProductModel newProductModel = null;
    private PopularProductModel popularProductModel= null;
    private FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mapping();
        final Object obj = getIntent().getSerializableExtra("detailed");
        final  Object objPopularProduct = getIntent().getSerializableExtra("popularDetailed");
        if(objPopularProduct instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) objPopularProduct;

        }
        if(obj instanceof NewProductModel){
            newProductModel = (NewProductModel) obj;

        }
        if(popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailImage);
            name.setText(popularProductModel.getName());
            description.setText(popularProductModel.getDescription());
            rating.setText(popularProductModel.getRating());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int priceFormat = popularProductModel.getPrice();
            price.setText(decimalFormat.format(priceFormat)+ "??");
        }

        if(newProductModel != null){
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailImage);
            name.setText(newProductModel.getName());
            description.setText(newProductModel.getDescription());
            rating.setText(newProductModel.getRating());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            int priceFormat = newProductModel.getPrice();
            price.setText(decimalFormat.format(priceFormat)+ "??");
        }
    }


    private void mapping() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        detailImage = findViewById(R.id.detail_img);
        description = findViewById(R.id.detail_description);
        name = findViewById(R.id.detail_name);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.price);
        btnPlus = findViewById(R.id.btn_plus);
        btnRemove = findViewById(R.id.btn_subtract);

    }

}