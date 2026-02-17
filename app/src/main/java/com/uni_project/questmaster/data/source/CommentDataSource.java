
package com.uni_project.questmaster.data.source;

import com.uni_project.questmaster.model.Comment;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface CommentDataSource {
    Task<List<Comment>> getComments(String questId);
    Task<Void> addComment(String questId, Comment comment);
}
