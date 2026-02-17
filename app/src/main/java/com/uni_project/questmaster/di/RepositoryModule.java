package com.uni_project.questmaster.di;

import com.uni_project.questmaster.data.repository.AuthRepositoryImpl;
import com.uni_project.questmaster.data.repository.CommentRepositoryImpl;
import com.uni_project.questmaster.data.repository.QuestRepositoryImpl;
import com.uni_project.questmaster.data.repository.UserRepositoryImpl;
import com.uni_project.questmaster.domain.repository.AuthRepository;
import com.uni_project.questmaster.domain.repository.CommentRepository;
import com.uni_project.questmaster.domain.repository.QuestRepository;
import com.uni_project.questmaster.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepositoryImpl);

    @Binds
    @Singleton
    public abstract QuestRepository bindQuestRepository(QuestRepositoryImpl questRepositoryImpl);

    @Binds
    @Singleton
    public abstract UserRepository bindUserRepository(UserRepositoryImpl userRepositoryImpl);

    @Binds
    @Singleton
    public abstract CommentRepository bindCommentRepository(CommentRepositoryImpl commentRepositoryImpl);
}
