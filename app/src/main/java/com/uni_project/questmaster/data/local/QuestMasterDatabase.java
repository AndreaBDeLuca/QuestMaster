
package com.uni_project.questmaster.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.uni_project.questmaster.model.Comment;
import com.uni_project.questmaster.model.Quest;

@Database(entities = {Comment.class, Quest.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class QuestMasterDatabase extends RoomDatabase {
    public abstract CommentDao commentDao();
    public abstract QuestDao questDao();

    private static volatile QuestMasterDatabase INSTANCE;

    public static QuestMasterDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestMasterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestMasterDatabase.class, "questmaster_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
