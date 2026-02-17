
package com.uni_project.questmaster.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.uni_project.questmaster.model.Quest;

import java.util.List;

@Dao
public interface QuestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Quest> quests);

    @Query("SELECT * FROM quests")
    List<Quest> getAllQuests();

    @Query("SELECT * FROM quests WHERE id = :questId")
    Quest getQuestById(String questId);

    @Query("SELECT * FROM quests WHERE id IN (:questIds)")
    List<Quest> getQuestsByIds(List<String> questIds);

    @Query("SELECT * FROM quests WHERE ownerId = :ownerId")
    List<Quest> getQuestsByOwnerId(String ownerId);

    @Query("DELETE FROM quests WHERE id = :questId")
    void deleteQuestById(String questId);
}
