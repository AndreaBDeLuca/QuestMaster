
package com.uni_project.questmaster.di;

import android.content.Context;

import com.uni_project.questmaster.data.local.CommentDao;
import com.uni_project.questmaster.data.local.QuestDao;
import com.uni_project.questmaster.data.local.QuestMasterDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public QuestMasterDatabase provideQuestMasterDatabase(@ApplicationContext Context context) {
        return QuestMasterDatabase.getDatabase(context);
    }

    @Provides
    public CommentDao provideCommentDao(QuestMasterDatabase database) {
        return database.commentDao();
    }

    @Provides
    public QuestDao provideQuestDao(QuestMasterDatabase database) {
        return database.questDao();
    }
}
