
package com.uni_project.questmaster.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.uni_project.questmaster.data.local.QuestDao;
import com.uni_project.questmaster.data.source.QuestDataSource;
import com.uni_project.questmaster.domain.repository.QuestRepository;
import com.uni_project.questmaster.model.Quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class QuestRepositoryImpl implements QuestRepository {
    private final QuestDataSource questDataSource;
    private final QuestDao questDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public QuestRepositoryImpl(QuestDataSource questDataSource, QuestDao questDao) {
        this.questDataSource = questDataSource;
        this.questDao = questDao;
    }

    @Override
    public Task<List<Quest>> getQuests() {
        return Tasks.call(executor, () -> {
            List<Quest> localQuests = questDao.getAllQuests();
            if (localQuests == null || localQuests.isEmpty()) {
                List<Quest> remoteQuests = Tasks.await(questDataSource.getQuests());
                questDao.insertAll(remoteQuests);
                return remoteQuests;
            }
            return localQuests;
        });
    }

    @Override
    public Task<List<Quest>> getQuests(List<String> questIds) {
        return Tasks.call(executor, () -> {
            List<Quest> localQuests = questDao.getQuestsByIds(questIds);
            List<String> localQuestIds = localQuests.stream().map(Quest::getId).collect(Collectors.toList());

            List<String> missingIds = new ArrayList<>(questIds);
            missingIds.removeAll(localQuestIds);

            if (!missingIds.isEmpty()) {
                List<Quest> remoteQuests = Tasks.await(questDataSource.getQuests(missingIds));
                questDao.insertAll(remoteQuests);
                localQuests.addAll(remoteQuests);
            }
            return localQuests;
        });
    }

    @Override
    public Task<Quest> getQuest(String questId) {
        return Tasks.call(executor, () -> {
            Quest localQuest = questDao.getQuestById(questId);
            if (localQuest == null) {
                Quest remoteQuest = Tasks.await(questDataSource.getQuest(questId));
                if (remoteQuest != null) {
                    questDao.insertAll(Collections.singletonList(remoteQuest));
                }
                return remoteQuest;
            }
            return localQuest;
        });
    }

    @Override
    public Task<Void> createQuest(Quest quest) {
        return questDataSource.createQuest(quest).addOnSuccessListener(aVoid -> {
            executor.execute(() -> {
                questDao.insertAll(Collections.singletonList(quest));
            });
        });
    }

    @Override
    public Task<Void> deleteQuest(String questId) {
        return questDataSource.deleteQuest(questId).addOnSuccessListener(aVoid -> {
            executor.execute(() -> questDao.deleteQuestById(questId));
        });
    }

    @Override
    public Task<List<Quest>> getQuestsByOwnerId(String ownerId) {
         return Tasks.call(executor, () -> {
            List<Quest> localQuests = questDao.getQuestsByOwnerId(ownerId);
            if (localQuests == null || localQuests.isEmpty()) {
                List<Quest> remoteQuests = Tasks.await(questDataSource.getQuestsByOwnerId(ownerId));
                questDao.insertAll(remoteQuests);
                return remoteQuests;
            }
            return localQuests;
        });
    }
}
