package com.uni_project.questmaster.domain.use_case;

import com.uni_project.questmaster.domain.repository.AuthRepository;

public class LogoutUseCase {

    private final AuthRepository repository;

    public LogoutUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.signOut();
    }
}
