package nlu.edu.vn.ecommerce.activities;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import nlu.edu.vn.ecommerce.R;

public class PaymentActivity extends AppCompatActivity {
    private Toolbar paymentToolBar;
    private ImageView cardImage;
    private TextView subTotal;
    private TextView discount;
    private TextView shipping;
    private TextView total;
    private Button btnPayment;
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
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("AddToCart")
                        .document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Tạo batch để xóa các tài liệu
                                    WriteBatch batch = db.batch();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Thêm tài liệu vào batch để xóa

                                        batch.delete(document.getReference());
                                    }
                                    // Thực thi batch để xóa tất cả các tài liệu
                                    batch.commit()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Xóa thành công
                                                    Toast.makeText(getApplicationContext(),"Thanh toán thành công",Toast.LENGTH_LONG);
                                                    startActivity(new Intent(PaymentActivity.this,MainActivity.class));
                                                    Log.d(TAG, "All documents deleted successfully");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Xảy ra lỗi khi xóa
                                                    Log.d(TAG, "Error deleting documents: ", e);
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "Error getting user documents: ", task.getException());
                                }
                            }
                        });
            }
        });

    }

    private void actionBar() {
        setSupportActionBar(paymentToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        paymentToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        btnPayment = findViewById(R.id.pay_btn);
        paymentToolBar = findViewById(R.id.payment_toolbar);
        cardImage = findViewById(R.id.card_image);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shiping);
        total = findViewById(R.id.total_amt);


    }
}