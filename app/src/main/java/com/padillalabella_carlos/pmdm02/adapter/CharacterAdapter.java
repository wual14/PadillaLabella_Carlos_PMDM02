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

/**
 * Adaptador para mostrar una lista de personajes en un RecyclerView.
 * Cada ítem de la lista contiene el nombre y la imagen del personaje, y al hacer clic
 * se abre una nueva actividad mostrando los detalles del personaje.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<Pair<String, Integer>> characterList;
    private Context context;

    /**
     * Constructor del adaptador.
     *
     * @param characterList Lista de personajes a mostrar en el RecyclerView
     * @param context Contexto de la actividad que utiliza el adaptador
     */
    public CharacterAdapter(List<Pair<String, Integer>> characterList, Context context) {
        this.characterList = characterList;
        this.context = context;
    }

    /**
     * Crea una nueva vista de ítem en el RecyclerView.
     *
     * @param parent El ViewGroup al que se añadirá la vista creada
     * @param viewType Tipo de vista (no utilizado en este caso)
     * @return Un nuevo ViewHolder que contiene la vista del ítem
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_cardview, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos del personaje a la vista correspondiente en el ViewHolder.
     *
     * @param holder El ViewHolder que contiene la vista del ítem
     * @param position La posición del ítem en la lista
     */
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

    /**
     * Devuelve la cantidad de elementos en la lista.
     *
     * @return El número de ítems en la lista
     */
    @Override
    public int getItemCount() {
        return characterList.size();
    }

    /**
     * ViewHolder que contiene las vistas de cada ítem en el RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView crdImage;
        private TextView crdText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crdImage = itemView.findViewById(R.id.cardImage);
            crdText = itemView.findViewById(R.id.cardText);
        }
    }

    /**
     * Devuelve la descripción del personaje según su nombre.
     *
     * @param characterName El nombre del personaje
     * @return La descripción correspondiente al personaje
     */
    private String getCharacterDescription(String characterName) {
        switch (characterName) {
            case "Mario": return context.getString(R.string.desc_mario);
            case "Luigi": return context.getString(R.string.desc_luigi);
            case "Peach": return context.getString(R.string.desc_peach);
            case "Toad": return context.getString(R.string.desc_toad);
            default: return context.getString(R.string.desc_unknown);
        }
    }

    /**
     * Devuelve las habilidades del personaje según su nombre.
     *
     * @param characterName El nombre del personaje
     * @return Las habilidades correspondientes al personaje
     */
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
