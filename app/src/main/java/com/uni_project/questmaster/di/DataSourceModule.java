package com.uni_project.questmaster.di;

import com.uni_project.questmaster.data.source.AuthDataSource;
import com.uni_project.questmaster.data.source.AuthDataSourceImpl;
import com.uni_project.questmaster.data.source.CommentDataSource;
import com.uni_project.questmaster.data.source.CommentRemoteDataSource;
import com.uni_project.questmaster.data.source.QuestDataSource;
import com.uni_project.questmaster.data.source.QuestRemoteDataSource;
import com.uni_project.questmaster.data.source.UserDataSource;
import com.uni_project.questmaster.data.source.UserRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {

    @Binds
    @Singleton
    public abstract AuthDataSource bindAuthDataSource(AuthDataSourceImpl authDataSourceImpl);

    @Binds
    @Singleton
    public abstract QuestDataSource bindQuestDataSource(QuestRemoteDataSource questRemoteDataSource);

    @Binds
    @Singleton
    public abstract UserDataSource bindUserDataSource(UserRemoteDataSource userRemoteDataSource);

    @Binds
    @Singleton
    public abstract CommentDataSource bindCommentDataSource(CommentRemoteDataSource commentRemoteDataSource);
}
