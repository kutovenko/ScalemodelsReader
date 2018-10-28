package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import static com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants.KEY_POST_ID;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setElevation(8);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    public void show(Post post) {
        Bundle bundle = new Bundle(2);
        bundle.putInt(KEY_POST_ID, post.getId());
        bundle.putString(MyAppConctants.KEY_POST_TITLE, post.getTitle());
        navController.navigate(R.id.postFragment, bundle);
    }

}