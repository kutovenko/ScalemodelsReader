package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.MyApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.GetValueCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.notification.NotificationJobCreator;
import com.blogspot.alexeykutovenko.scalemodelsreader.notification.ScalemodelsNotificationService;
import com.evernote.android.job.JobManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.CHANNEL_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.KEY_POST_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.KEY_POST_TITLE;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.UI_ELEVATION;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp app = (MyApp) this.getApplication();
        DataRepository dataRepository = app.getRepository();
        updateNewsDatabase(dataRepository);

        createNotificationChannel();
        JobManager.create(this).addJobCreator(new NotificationJobCreator(dataRepository, this));

        ScalemodelsNotificationService.schedulePeriodic();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setElevation(UI_ELEVATION);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void updateNewsDatabase(DataRepository dataRepository) {
        dataRepository.getScalemodelsPosts(this, new GetValueCallback() {
            @Override
            public void onSuccess(int value) {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
        dataRepository.getScalemodelsFeatured();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    public void show(Post post) {
        Bundle bundle = new Bundle(2);
        bundle.putLong(KEY_POST_ID, post.getId());
        bundle.putString(KEY_POST_TITLE, post.getTitle());
        navController.navigate(R.id.postFragment, bundle);
    }
}