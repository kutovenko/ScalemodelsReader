package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.MyApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.notification.NotificationJobCreator;
import com.blogspot.alexeykutovenko.scalemodelsreader.notification.ScalemodelsNotificationService;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.CHANNEL_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.KEY_POST_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.KEY_POST_TITLE;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.UI_ELEVATION;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp app = (MyApp) this.getApplication();
        DataRepository dataRepository = app.getRepository();
        dataRepository.getScalemodelsPosts(this);
        dataRepository.getScalemodelsFeatured();

        createNotificationChannel();
        JobManager.create(this).addJobCreator(new NotificationJobCreator(dataRepository, this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setElevation(UI_ELEVATION);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        ScalemodelsNotificationService.schedulePeriodic();

//        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        int result = 0;
//        if (scheduler != null) {
//            result = scheduler.schedule(createNotificationSheduler());
//        }
//        if (result == JobScheduler.RESULT_SUCCESS) {
//            Log.d("Notifications", "Job scheduled successfully!");
//        }

//        buildNotification();

    }

    private void buildNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_background_splash)
                .setContentTitle("ScalemodelsReader")
                .setContentText("10 news")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(MyAppConctants.NEWS_NOTIFICATION_ID, mBuilder.build());
    }

//    private JobInfo createNotificationSheduler() {
//        ComponentName serviceName = new ComponentName(this, ScalemodelsNotificationService.class);
//        return new JobInfo.Builder(1, serviceName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING)
//                .setRequiresDeviceIdle(true)
//                .setRequiresCharging(true)
//                .setPeriodic(NOTIFICATION_PERIOD)
//                .build();
//    }

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