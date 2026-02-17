
package com.uni_project.questmaster.domain.use_case;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.uni_project.questmaster.domain.repository.AuthRepository;

import javax.inject.Inject;

public class LoginUserUseCase {
    private final AuthRepository repository;

    @Inject
    public LoginUserUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public Task<AuthResult> execute(String email, String password) {
        return repository.signInWithEmailAndPassword(email, password);
    }
}
