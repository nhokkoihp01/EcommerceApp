package nlu.edu.vn.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import nlu.edu.vn.ecommerce.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtLogin;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mapping();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
        }
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Nhập tên tài khoản",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Nhập email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Nhập mật khẩu",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(),"Mật khẩu quá ngắn,tối thiểu là 6 kí tự",Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this,"Đăng ký thất bại"+ task.getException(),Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }
    private void mapping(){
        auth = FirebaseAuth.getInstance();
        txtLogin = findViewById(R.id.txtLogin);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }


}