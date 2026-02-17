
package com.uni_project.questmaster.domain.use_case;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.uni_project.questmaster.domain.repository.AuthRepository;

import javax.inject.Inject;

public class GoogleSignInUseCase {
    private final AuthRepository repository;

    @Inject
    public GoogleSignInUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public Task<AuthResult> execute(GoogleSignInAccount account) {
        return repository.signInWithGoogle(account);
    }
}
