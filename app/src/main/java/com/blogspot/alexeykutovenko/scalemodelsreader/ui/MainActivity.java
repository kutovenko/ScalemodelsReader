package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Featured;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        bundle.putInt("id", post.getId());
        bundle.putString("type", "post");
        navController.navigate(R.id.postFragment, bundle);
    }

    public void show(Featured featured) {
        Bundle bundle = new Bundle(2);
        bundle.putInt("id", featured.getId());
        bundle.putString("type", "featured");
        navController.navigate(R.id.postFragment, bundle);
    }

}