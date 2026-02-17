package com.uni_project.questmaster.di;

import com.google.firebase.storage.FirebaseStorage;
import com.uni_project.questmaster.domain.repository.AuthRepository;
import com.uni_project.questmaster.domain.repository.CommentRepository;
import com.uni_project.questmaster.domain.repository.QuestRepository;
import com.uni_project.questmaster.domain.repository.UserRepository;
import com.uni_project.questmaster.domain.use_case.*;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    // --- Auth Use Cases ---

    @Provides
    public LoginUserUseCase provideLoginUserUseCase(AuthRepository authRepository) {
        return new LoginUserUseCase(authRepository);
    }

    @Provides
    public CreateUserUseCase provideCreateUserUseCase(AuthRepository authRepository, UserRepository userRepository) {
        return new CreateUserUseCase(authRepository, userRepository);
    }

    @Provides
    public GoogleSignInUseCase provideGoogleSignInUseCase(AuthRepository authRepository) {
        return new GoogleSignInUseCase(authRepository);
    }

    @Provides
    public GetCurrentUserUseCase provideGetCurrentUserUseCase(AuthRepository authRepository) {
        return new GetCurrentUserUseCase(authRepository);
    }

    @Provides
    public LogoutUseCase provideLogoutUseCase(AuthRepository authRepository) {
        return new LogoutUseCase(authRepository);
    }


    // --- Quest Use Cases ---

    @Provides
    public CreateQuestUseCase provideCreateQuestUseCase(QuestRepository questRepository, AuthRepository authRepository, FirebaseStorage storage) {
        return new CreateQuestUseCase(questRepository, authRepository, storage);
    }

    @Provides
    public GetQuestUseCase provideGetQuestUseCase(QuestRepository questRepository) {
        return new GetQuestUseCase(questRepository);
    }

    @Provides
    public DeleteQuestUseCase provideDeleteQuestUseCase(QuestRepository questRepository) {
        return new DeleteQuestUseCase(questRepository);
    }

    @Provides
    public GetSavedQuestsUseCase provideGetSavedQuestsUseCase(AuthRepository authRepository, UserRepository userRepository, QuestRepository questRepository) {
        return new GetSavedQuestsUseCase(authRepository, userRepository, questRepository);
    }


    // --- User Use Cases ---

    @Provides
    public GetUserProfileUseCase provideGetUserProfileUseCase(UserRepository userRepository) {
        return new GetUserProfileUseCase(userRepository);
    }

    @Provides
    public ToggleSavedQuestUseCase provideToggleSavedQuestUseCase(UserRepository userRepository, AuthRepository authRepository) {
        return new ToggleSavedQuestUseCase(userRepository, authRepository);
    }

    @Provides
    public CompleteQuestUseCase provideCompleteQuestUseCase(UserRepository userRepository, AuthRepository authRepository) {
        return new CompleteQuestUseCase(userRepository, authRepository);
    }


    // --- Comment Use Cases ---

    @Provides
    public GetCommentsUseCase provideGetCommentsUseCase(CommentRepository commentRepository) {
        return new GetCommentsUseCase(commentRepository);
    }

    @Provides
    public AddCommentUseCase provideAddCommentUseCase(CommentRepository commentRepository, AuthRepository authRepository, UserRepository userRepository) {
        return new AddCommentUseCase(commentRepository, authRepository, userRepository);
    }
}
