
package com.uni_project.questmaster.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.uni_project.questmaster.model.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Comment> comments);

    @Query("SELECT * FROM comments WHERE questId = :questId ORDER BY timestamp DESC")
    List<Comment> getCommentsForQuest(String questId);

    @Query("DELETE FROM comments WHERE questId = :questId")
    void deleteCommentsForQuest(String questId);
}
