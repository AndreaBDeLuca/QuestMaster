package com.uni_project.questmaster.data.source;

import com.uni_project.questmaster.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import javax.inject.Inject;

public class UserRemoteDataSource implements UserDataSource {
    private final FirebaseFirestore firestore;

    @Inject
    public UserRemoteDataSource(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<User> getUser(String uid) {
        return firestore.collection("users").document(uid).get().continueWith(task -> task.getResult().toObject(User.class));
    }

    @Override
    public Task<Void> createUser(User user) {
        return firestore.collection("users").document(user.getUid()).set(user);
    }

    @Override
    public Task<Void> toggleSavedQuest(String uid, String questId, boolean isSaved) {
        if (isSaved) {
            return firestore.collection("users").document(uid).update("savedQuests", FieldValue.arrayUnion(questId));
        } else {
            return firestore.collection("users").document(uid).update("savedQuests", FieldValue.arrayRemove(questId));
        }
    }

    @Override
    public Task<Void> completeQuest(String uid, String questId, long ppq) {
        return firestore.runTransaction(transaction -> {
            transaction.update(firestore.collection("users").document(uid), "completedQuests", FieldValue.arrayUnion(questId));
            transaction.update(firestore.collection("users").document(uid), "ppq", FieldValue.increment(ppq));
            return null;
        });
    }

    public Task<Void> updateFcmToken(String uid, String token) {
        return firestore.collection("users").document(uid).update("fcmToken", token);
    }
}
