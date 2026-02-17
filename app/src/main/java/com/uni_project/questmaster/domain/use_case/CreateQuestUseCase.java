package com.uni_project.questmaster.domain.use_case;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uni_project.questmaster.domain.repository.AuthRepository;
import com.uni_project.questmaster.domain.repository.QuestRepository;
import com.uni_project.questmaster.model.Quest;
import com.uni_project.questmaster.model.QuestLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class CreateQuestUseCase {
    private final QuestRepository questRepository;
    private final AuthRepository authRepository;
    private final FirebaseStorage storage;

    @Inject
    public CreateQuestUseCase(QuestRepository questRepository, AuthRepository authRepository, FirebaseStorage storage) {
        this.questRepository = questRepository;
        this.authRepository = authRepository;
        this.storage = storage;
    }

    public Task<Void> execute(String title, String description, long ppq, List<Uri> mediaUris, QuestLocation location) {
        String ownerId = authRepository.getCurrentUser().getUid();

        if (mediaUris == null || mediaUris.isEmpty()) {
            return createQuest(title, description, ppq, ownerId, new ArrayList<>(), location);
        }

        List<Task<Uri>> uploadTasks = new ArrayList<>();
        for (Uri uri : mediaUris) {
            StorageReference ref = storage.getReference().child("quest_media/" + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            });
            uploadTasks.add(urlTask);
        }

        return Tasks.whenAllSuccess(uploadTasks).onSuccessTask(urls -> {
            List<String> downloadUrls = new ArrayList<>();
            for (Object url : urls) {
                downloadUrls.add(url.toString());
            }
            return createQuest(title, description, ppq, ownerId, downloadUrls, location);
        });
    }

    private Task<Void> createQuest(String title, String description, long ppq, String ownerId, List<String> imageUrls, QuestLocation location) {
        Quest quest = new Quest();
        quest.setTitle(title);
        quest.setDescription(description);
        quest.setOwnerId(ownerId);
        quest.setImageUrls(imageUrls);
        quest.setLocation(location);
        quest.setPpq(ppq);
        return questRepository.createQuest(quest);
    }
}
