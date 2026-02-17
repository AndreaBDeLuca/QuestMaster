
package com.uni_project.questmaster.data.source;

import com.uni_project.questmaster.model.Quest;
import com.google.android.gms.tasks.Task;
import java.util.List;

public interface QuestDataSource {
    Task<List<Quest>> getQuests();
    Task<List<Quest>> getQuests(List<String> questIds);
    Task<Quest> getQuest(String questId);
    Task<Void> createQuest(Quest quest);
    Task<Void> deleteQuest(String questId);
    Task<List<Quest>> getQuestsByOwnerId(String ownerId);
}
