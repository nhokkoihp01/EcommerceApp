package nlu.edu.vn.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nlu.edu.vn.ecommerce.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

    }
}