
package com.uni_project.questmaster.domain.use_case;

import com.uni_project.questmaster.domain.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class GetCurrentUserUseCase {
    private final AuthRepository repository;

    @Inject
    public GetCurrentUserUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public FirebaseUser execute() {
        return repository.getCurrentUser();
    }
}
