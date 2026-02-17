package com.uni_project.questmaster.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.uni_project.questmaster.R;
import com.uni_project.questmaster.model.Quest;
import com.uni_project.questmaster.model.QuestLocation;
import com.uni_project.questmaster.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class QuestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_QUEST = 0;
    private static final int VIEW_TYPE_USER = 1;

    private final Context context;
    private final List<Object> items = new ArrayList<>();
    private OnQuestClickListener onQuestClickListener;
    private OnUserClickListener onUserClickListener;

    public interface OnQuestClickListener {
        void onQuestClick(Quest quest);
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public QuestAdapter(Context context) {
        this.context = context;
    }

    public void setOnQuestClickListener(OnQuestClickListener listener) {
        this.onQuestClickListener = listener;
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.onUserClickListener = listener;
    }

    public void setData(List<Quest> quests, List<User> users) {
        int oldSize = items.size();
        items.clear();
        notifyItemRangeRemoved(0, oldSize);
        if (quests != null) {
            items.addAll(quests);
        }
        if (users != null) {
            items.addAll(users);
        }
        notifyItemRangeInserted(0, items.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Quest) {
            return VIEW_TYPE_QUEST;
        } else {
            return VIEW_TYPE_USER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_QUEST) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_quest, parent, false);
            return new QuestViewHolder(view, context);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false);
            return new UserViewHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_QUEST) {
            Quest quest = (Quest) items.get(position);
            ((QuestViewHolder) holder).bind(quest);
            holder.itemView.setOnClickListener(v -> {
                if (onQuestClickListener != null) {
                    onQuestClickListener.onQuestClick(quest);
                }
            });
        } else {
            User user = (User) items.get(position);
            ((UserViewHolder) holder).bind(user);
            holder.itemView.setOnClickListener(v -> {
                if (onUserClickListener != null) {
                    onUserClickListener.onUserClick(user);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class QuestViewHolder extends RecyclerView.ViewHolder {
        TextView questNameTextView;
        TextView usernameTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        ViewPager2 questImagesViewPager;
        TabLayout tabIndicator;
        Context context;

        QuestViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            questNameTextView = itemView.findViewById(R.id.questNameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            questImagesViewPager = itemView.findViewById(R.id.questImagesViewPager);
            tabIndicator = itemView.findViewById(R.id.tabIndicator);
        }

        void bind(Quest quest) {
            questNameTextView.setText(quest.getTitle());
            usernameTextView.setText(quest.getOwnerName());
            descriptionTextView.setText(quest.getDescription());

            if (quest.getTimestamp() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                dateTextView.setText(sdf.format(quest.getTimestamp()));
            } else {
                dateTextView.setVisibility(View.GONE);
            }

            if (quest.getImageUrls() != null && !quest.getImageUrls().isEmpty()) {
                questImagesViewPager.setVisibility(View.VISIBLE);
                tabIndicator.setVisibility(View.VISIBLE);
                List<Uri> imageUris = quest.getImageUrls().stream().map(Uri::parse).collect(Collectors.toList());
                QuestMediaAdapter mediaAdapter = new QuestMediaAdapter(context, imageUris, null, false, quest.getLocation(), context.getString(R.string.google_maps_key));
                questImagesViewPager.setAdapter(mediaAdapter);

                new TabLayoutMediator(tabIndicator, questImagesViewPager, (tab, position) -> {
                }).attach();

            } else {
                questImagesViewPager.setVisibility(View.GONE);
                tabIndicator.setVisibility(View.GONE);
            }
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfileImageView;
        TextView userNameTextView;
        Context context;

        UserViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            userProfileImageView = itemView.findViewById(R.id.user_profile_image);
            userNameTextView = itemView.findViewById(R.id.user_name);
        }

        void bind(User user) {
            userNameTextView.setText(user.getUsername());
            String imageUrl = user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()
                    ? user.getProfileImageUrl()
                    : user.getAvatarUrl();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.profile_menu_icon)
                    .error(R.drawable.profile_menu_icon)
                    .into(userProfileImageView);
        }
    }
}
