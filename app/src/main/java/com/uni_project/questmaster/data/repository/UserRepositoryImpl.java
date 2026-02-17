
package com.uni_project.questmaster.data.repository;

import com.uni_project.questmaster.data.source.UserDataSource;
import com.uni_project.questmaster.domain.repository.UserRepository;
import com.uni_project.questmaster.model.User;
import com.google.android.gms.tasks.Task;
import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {
    private final UserDataSource userDataSource;

    @Inject
    public UserRepositoryImpl(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public Task<User> getUser(String uid) {
        return userDataSource.getUser(uid);
    }

    @Override
    public Task<Void> createUser(User user) {
        return userDataSource.createUser(user);
    }

    @Override
    public Task<Void> toggleSavedQuest(String uid, String questId, boolean isSaved) {
        return userDataSource.toggleSavedQuest(uid, questId, isSaved);
    }

    @Override
    public Task<Void> completeQuest(String uid, String questId, long ppq) {
        return userDataSource.completeQuest(uid, questId, ppq);
    }
}
