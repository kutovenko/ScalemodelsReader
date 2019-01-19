package com.blogspot.alexeykutovenko.scalemodelsreader.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.CHANNEL_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.NEWS_NOTIFICATION_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.SCALEMODELSRU;

/**
 * Main Notifications service class based on Evernote Android-Job library
 * https://android.jlelse.eu/easy-job-scheduling-with-android-job-4a2c020b9742
 */
public class ScalemodelsNotificationService extends Job {
    static final String TAG = SCALEMODELSRU;
    private DataRepository dataRepository;
    private Context context;

    /**
     * Required empty constructor
     */
    public ScalemodelsNotificationService() {
    }

    ScalemodelsNotificationService(DataRepository dataRepository, Context context) {
        this.dataRepository = dataRepository;
        this.context = context;
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        AtomicInteger numberOfNews = new AtomicInteger();
        new Handler().postDelayed(() -> numberOfNews.set(dataRepository.getScalemodelsPosts(context)), 300);

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);

        if (numberOfNews.get() > 0) {
            Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                    .setContentTitle(MyAppConctants.APP_NAME)
                    .setContentText(MessageFormat.format(context.getString(R.string.news_notification), numberOfNews, TAG))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setShowWhen(true)
                    .setColor(Color.BLUE)
                    .setLocalOnly(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();

            NotificationManagerCompat.from(getContext())
                    .notify(NEWS_NOTIFICATION_ID, notification);
        }

        return Result.SUCCESS;
    }

    public static void schedulePeriodic() {
        new JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(11), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}
