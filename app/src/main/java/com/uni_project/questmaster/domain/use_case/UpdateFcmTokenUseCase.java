package com.uni_project.questmaster.domain.use_case;

import com.uni_project.questmaster.domain.repository.UserRepository;
import javax.inject.Inject;

public class UpdateFcmTokenUseCase {
    private final UserRepository userRepository;

    @Inject
    public UpdateFcmTokenUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String uid, String token) {
        userRepository.updateFcmToken(uid, token);
    }
}
