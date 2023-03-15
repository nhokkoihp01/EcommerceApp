package nlu.edu.vn.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment homeFragment;
    private Toolbar homeToolbar;
    private FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        actionBar();
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void actionBar() {
        setSupportActionBar(homeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();

        } else if (id == R.id.menu_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));

        }
        return true;
    }

    private void mapping() {
        auth = FirebaseAuth.getInstance();
        homeToolbar = findViewById(R.id.home_toolbar);
    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }
}