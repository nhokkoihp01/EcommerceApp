package nlu.edu.vn.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nlu.edu.vn.ecommerce.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mapping();

    }
    private void mapping(){
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }



    public void register(View view) {
        String username =txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Nhập tên tài khoản",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Nhập email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Nhập mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this,"Mật khẩu quá ngắn,tối thiểu là 6 kí tự",Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}