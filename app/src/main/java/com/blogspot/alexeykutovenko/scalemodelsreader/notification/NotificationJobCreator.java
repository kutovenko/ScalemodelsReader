package com.blogspot.alexeykutovenko.scalemodelsreader.notification;

import android.content.Context;

import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class NotificationJobCreator implements JobCreator {
    private DataRepository dataRepository;
    private Context context;

    public NotificationJobCreator(DataRepository dataRepository, Context context) {
        this.dataRepository = dataRepository;
        this.context = context;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case ScalemodelsNotificationService.TAG:
                return new ScalemodelsNotificationService(dataRepository, context);
            default:
                return null;
        }
    }
}
