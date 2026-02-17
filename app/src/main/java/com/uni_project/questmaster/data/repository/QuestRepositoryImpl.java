
package com.uni_project.questmaster.data.repository;

import com.uni_project.questmaster.data.source.QuestDataSource;
import com.uni_project.questmaster.domain.repository.QuestRepository;
import com.uni_project.questmaster.model.Quest;
import com.google.android.gms.tasks.Task;
import java.util.List;
import javax.inject.Inject;

public class QuestRepositoryImpl implements QuestRepository {
    private final QuestDataSource questDataSource;

    @Inject
    public QuestRepositoryImpl(QuestDataSource questDataSource) {
        this.questDataSource = questDataSource;
    }

    @Override
    public Task<List<Quest>> getQuests() {
        return questDataSource.getQuests();
    }

    @Override
    public Task<List<Quest>> getQuests(List<String> questIds) {
        return questDataSource.getQuests(questIds);
    }

    @Override
    public Task<Quest> getQuest(String questId) {
        return questDataSource.getQuest(questId);
    }

    @Override
    public Task<Void> createQuest(Quest quest) {
        return questDataSource.createQuest(quest);
    }

    @Override
    public Task<Void> deleteQuest(String questId) {
        return questDataSource.deleteQuest(questId);
    }

    @Override
    public Task<List<Quest>> getQuestsByOwnerId(String ownerId) {
        return questDataSource.getQuestsByOwnerId(ownerId);
    }
}
