
package com.uni_project.questmaster.domain.use_case;

import com.google.android.gms.tasks.Task;
import com.uni_project.questmaster.domain.repository.QuestRepository;

import javax.inject.Inject;

public class DeleteQuestUseCase {
    private final QuestRepository repository;

    @Inject
    public DeleteQuestUseCase(QuestRepository repository) {
        this.repository = repository;
    }

    public Task<Void> execute(String questId) {
        return repository.deleteQuest(questId);
    }
}
