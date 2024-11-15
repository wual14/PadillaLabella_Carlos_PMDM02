package com.padillalabella_carlos.pmdm02.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.padillalabella_carlos.pmdm02.CharacterDetailActivity;
import com.padillalabella_carlos.pmdm02.R;

import java.util.List;
import android.util.Pair;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<Pair<String, Integer>> characterList;
    private Context context;

    public CharacterAdapter(List<Pair<String, Integer>> characterList, Context context) {
        this.characterList = characterList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, Integer> character = characterList.get(position);
        holder.crdText.setText(character.first);
        holder.crdImage.setImageResource(character.second);

        String description = getCharacterDescription(character.first);
        String skills = getCharacterSkills(character.first);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CharacterDetailActivity.class);
            intent.putExtra("name", character.first);
            intent.putExtra("image", character.second);
            intent.putExtra("description", description);
            intent.putExtra("skills", skills);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView crdImage;
        private TextView crdText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crdImage = itemView.findViewById(R.id.cardImage);
            crdText = itemView.findViewById(R.id.cardText);
        }
    }

    private String getCharacterDescription(String characterName) {
        switch (characterName) {
            case "Mario": return context.getString(R.string.desc_mario);
            case "Luigi": return context.getString(R.string.desc_luigi);
            case "Peach": return context.getString(R.string.desc_peach);
            case "Toad": return context.getString(R.string.desc_toad);
            default: return context.getString(R.string.desc_unknown);
        }
    }

    private String getCharacterSkills(String characterName) {
        switch (characterName) {
            case "Mario": return context.getString(R.string.skills_mario);
            case "Luigi": return context.getString(R.string.skills_luigi);
            case "Peach": return context.getString(R.string.skills_peach);
            case "Toad": return context.getString(R.string.skills_toad);
            default: return context.getString(R.string.skills_none);
        }
    }

}
