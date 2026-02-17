
package com.uni_project.questmaster.data.source;

import com.uni_project.questmaster.model.Comment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CommentRemoteDataSource implements CommentDataSource {
    private final FirebaseFirestore firestore;

    @Inject
    public CommentRemoteDataSource(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<List<Comment>> getComments(String questId) {
        return firestore.collection("quests").document(questId).collection("comments").orderBy("timestamp").get().continueWith(task -> {
            List<Comment> comments = new ArrayList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                comments.add(document.toObject(Comment.class));
            }
            return comments;
        });
    }

    @Override
    public Task<Void> addComment(String questId, Comment comment) {
        return firestore.collection("quests").document(questId).collection("comments").add(comment).continueWith(task -> null);
    }
}
