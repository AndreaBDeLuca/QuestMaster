
package com.uni_project.questmaster.data.repository;

import com.uni_project.questmaster.data.local.CommentDao;
import com.uni_project.questmaster.data.source.CommentDataSource;
import com.uni_project.questmaster.domain.repository.CommentRepository;
import com.uni_project.questmaster.model.Comment;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class CommentRepositoryImpl implements CommentRepository {
    private final CommentDataSource commentDataSource;
    private final CommentDao commentDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public CommentRepositoryImpl(CommentDataSource commentDataSource, CommentDao commentDao) {
        this.commentDataSource = commentDataSource;
        this.commentDao = commentDao;
    }

    @Override
    public Task<List<Comment>> getComments(String questId) {
        return Tasks.call(executor, () -> {
            List<Comment> comments = commentDao.getCommentsForQuest(questId);
            if (comments == null || comments.isEmpty()) {
                List<Comment> remoteComments = Tasks.await(commentDataSource.getComments(questId));
                for(Comment comment : remoteComments) {
                    comment.setQuestId(questId);
                }
                commentDao.insertAll(remoteComments);
                return remoteComments;
            } else {
                return comments;
            }
        });
    }

    @Override
    public Task<Void> addComment(String questId, Comment comment) {
        return commentDataSource.addComment(questId, comment).addOnSuccessListener(aVoid -> {
            executor.execute(() -> {
                // Invalidate cache for the specific quest
                commentDao.deleteCommentsForQuest(questId);
                // Fetch again from remote and update cache
                commentDataSource.getComments(questId).addOnSuccessListener(remoteComments -> {
                    for(Comment remoteComment : remoteComments) {
                        remoteComment.setQuestId(questId);
                    }
                    executor.execute(() -> commentDao.insertAll(remoteComments));
                });
            });
        });
    }
}
