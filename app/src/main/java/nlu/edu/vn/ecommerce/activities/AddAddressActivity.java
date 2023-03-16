package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import nlu.edu.vn.ecommerce.R;

public class AddAddressActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtAddress;
    private EditText txtCity;
    private EditText txtNumberPhone;
    private EditText txtCode;
    private Button btnAddAddress;
    private Toolbar addAddressToolBar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mapping();
        actionBar();
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtName.getText().toString();
                String userCity = txtCity.getText().toString();
                String userAddress = txtAddress.getText().toString();
                String userCode = txtCode.getText().toString();
                String userNumberPhone = txtNumberPhone.getText().toString();
                String final_address = "";
                if (!userName.isEmpty()) {
                    final_address += userName + "/";
                }
                if (!userCity.isEmpty()) {
                    final_address += userCity + "/";
                }
                if (!userAddress.isEmpty()) {
                    final_address += userAddress + "/";
                }
                if (!userCode.isEmpty()) {
                    final_address += userCode + "/";
                }
                if (!userNumberPhone.isEmpty()) {
                    final_address += userNumberPhone + "/";
                }
                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumberPhone.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", final_address);
                    db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this,DetailActivity.class));
                                        finish();
                                    }

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm dịa chỉ thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(addAddressToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mapping() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        addAddressToolBar = findViewById(R.id.add_address_toolbar);
        txtName = findViewById(R.id.add_name);
        txtAddress = findViewById(R.id.add_address);
        txtCity = findViewById(R.id.add_city);
        txtCode = findViewById(R.id.add_code);
        txtNumberPhone = findViewById(R.id.add_phone);
        btnAddAddress = findViewById(R.id.btn_add_address);

    }
}