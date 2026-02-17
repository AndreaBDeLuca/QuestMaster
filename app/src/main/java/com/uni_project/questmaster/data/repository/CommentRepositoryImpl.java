
package com.uni_project.questmaster.data.repository;

import com.uni_project.questmaster.data.source.CommentDataSource;
import com.uni_project.questmaster.domain.repository.CommentRepository;
import com.uni_project.questmaster.model.Comment;
import com.google.android.gms.tasks.Task;

import java.util.List;
import javax.inject.Inject;

public class CommentRepositoryImpl implements CommentRepository {
    private final CommentDataSource commentDataSource;

    @Inject
    public CommentRepositoryImpl(CommentDataSource commentDataSource) {
        this.commentDataSource = commentDataSource;
    }

    @Override
    public Task<List<Comment>> getComments(String questId) {
        return commentDataSource.getComments(questId);
    }

    @Override
    public Task<Void> addComment(String questId, Comment comment) {
        return commentDataSource.addComment(questId, comment);
    }
}
