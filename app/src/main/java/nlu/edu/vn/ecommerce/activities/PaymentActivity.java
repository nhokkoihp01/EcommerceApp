package nlu.edu.vn.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import nlu.edu.vn.ecommerce.R;

public class PaymentActivity extends AppCompatActivity {
    private Toolbar paymentToolBar;
    private ImageView cardImage;
    private TextView subTotal;
    private TextView discount;
    private TextView shipping;
    private TextView total;
    private FirebaseFirestore db;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();
        actionBar();
        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0);
        subTotal.setText(amount + "đ");

    }

    private void actionBar() {
        setSupportActionBar(paymentToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mapping() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        paymentToolBar = findViewById(R.id.payment_toolbar);
        cardImage = findViewById(R.id.card_image);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shiping);
        total = findViewById(R.id.total_amt);


    }
}