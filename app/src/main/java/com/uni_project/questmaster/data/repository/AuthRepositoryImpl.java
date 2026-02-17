
package com.uni_project.questmaster.data.repository;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.uni_project.questmaster.data.source.AuthDataSource;
import com.uni_project.questmaster.domain.repository.AuthRepository;
import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthDataSource authDataSource;

    @Inject
    public AuthRepositoryImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return authDataSource.getCurrentUser();
    }

    @Override
    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return authDataSource.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
        return authDataSource.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signInWithGoogle(GoogleSignInAccount account) {
        return authDataSource.signInWithGoogle(account);
    }

    @Override
    public void signOut() {
        authDataSource.signOut();
    }
}
