package com.uni_project.questmaster.services;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uni_project.questmaster.config.QuestMasterApplication;
import com.uni_project.questmaster.R;
import com.uni_project.questmaster.domain.repository.AuthRepository;
import com.uni_project.questmaster.domain.use_case.UpdateFcmTokenUseCase;
import com.uni_project.questmaster.ui.home.HomeActivity;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Inject
    public UpdateFcmTokenUseCase updateFcmTokenUseCase;

    @Inject
    public AuthRepository authRepository;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent intent;
        Map<String, String> data = remoteMessage.getData();
        String type = data.get("type");

        if (type != null) {
            switch (type) {
                case "NEW_COMMENT":
                    String questId = data.get("questId");
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("open_fragment", "quest_view");
                    intent.putExtra("questId", questId);
                    break;
                case "NEW_FOLLOWER":
                    String userId = data.get("userId");
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("open_fragment", "profile");
                    intent.putExtra("userId", userId);
                    break;
                default:
                    intent = new Intent(this, HomeActivity.class);
                    break;
            }
        } else {
            intent = new Intent(this, HomeActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = QuestMasterApplication.CHANNEL_ID;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        FirebaseUser currentUser = authRepository.getCurrentUser();
        if (currentUser != null) {
            updateFcmTokenUseCase.execute(currentUser.getUid(), token);
        }
    }
}
