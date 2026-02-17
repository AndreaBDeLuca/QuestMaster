package com.uni_project.questmaster.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.firestore.ServerTimestamp;
import com.uni_project.questmaster.data.local.Converters;

import java.util.Date;

@Entity(tableName = "comments")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int commentId;
    private String questId;
    private String userId;
    private String username;
    private String avatarUrl;
    private String text;
    @ServerTimestamp
    @TypeConverters(Converters.class)
    private Date timestamp;

    public Comment() {
    }

    public Comment(String questId, String userId, String username, String avatarUrl, String text) {
        this.questId = questId;
        this.userId = userId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.text = text;
        this.timestamp = new Date();
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
