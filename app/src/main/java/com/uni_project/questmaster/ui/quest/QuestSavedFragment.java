package com.uni_project.questmaster.ui.quest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni_project.questmaster.R;
import com.uni_project.questmaster.adapter.QuestAdapter;
import com.uni_project.questmaster.model.Quest;
import com.uni_project.questmaster.model.User;

import java.util.List;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuestSavedFragment extends Fragment {

    private static final String TAG = "QuestSavedFragment";
    private RecyclerView savedQuestsRecyclerView;
    private QuestAdapter questAdapter;
    private QuestSavedViewModel viewModel;

    private List<Quest> currentQuests = null;
    private Map<String, User> currentUserProfiles = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(QuestSavedViewModel.class);

        savedQuestsRecyclerView = view.findViewById(R.id.savedQuestsRecyclerView);
        savedQuestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        questAdapter = new QuestAdapter(getContext());
        questAdapter.setOnQuestClickListener(quest -> {
            Bundle bundle = new Bundle();
            bundle.putString("questId", quest.getId());
            NavHostFragment.findNavController(this).navigate(R.id.action_questSavedFragment_to_questViewFragment, bundle);
        });
        savedQuestsRecyclerView.setAdapter(questAdapter);

        viewModel.savedQuests.observe(getViewLifecycleOwner(), quests -> {
            if (quests != null) {
                currentQuests = quests;
                updateAdapterData();
            } else {
                Log.e(TAG, "Error loading saved quests");
            }
        });

        // This observer might be unnecessary if user profiles are not displayed here
        viewModel.userProfiles.observe(getViewLifecycleOwner(), userProfiles -> {
            if (userProfiles != null) {
                currentUserProfiles = userProfiles;
                updateAdapterData();
            }
        });

        viewModel.loadSavedQuests();
    }

    private void updateAdapterData() {
        // The QuestAdapter now takes Lists of Quests and Users.
        // If you only have quests, you can pass null or an empty list for users.
        // Assuming this fragment only shows saved quests, we pass null for users.
        if (currentQuests != null) {
            questAdapter.setData(currentQuests, null);
        }
    }
}
