package com.example.msgme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.msgme.Adapter.FragmentsAdapter;
import com.example.msgme.Fragments.ChatsFragment;
import com.example.msgme.databinding.ActivityMainBinding;
import com.example.msgme.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sttings:
                Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.logOut:
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this,activity_sign_in.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}