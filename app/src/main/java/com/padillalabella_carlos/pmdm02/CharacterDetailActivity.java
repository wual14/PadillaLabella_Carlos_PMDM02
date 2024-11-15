package com.padillalabella_carlos.pmdm02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


/**
 * CharacterDetailActivity muestra los detalles de un personaje seleccionado, incluyendo su imagen,
 * nombre, descripción y habilidades.
 * También incluye un botón para regresar a la actividad anterior.
 */
public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailDescription = findViewById(R.id.detailDescription);
        TextView detailSkills = findViewById(R.id.detailSkills);

        Intent intent = getIntent();

        if (intent != null) {
            String name = intent.getStringExtra("name");
            int imageResId = intent.getIntExtra("image", 0);
            String description = intent.getStringExtra("description");
            String skills = intent.getStringExtra("skills");

            detailName.setText(name);
            detailImage.setImageResource(imageResId);
            detailDescription.setText(description);
            detailSkills.setText(skills);

            // Apartado G: Añade el Mensaje Toast
            Toast.makeText(this, getString(R.string.toast_1) + ": " + name, Toast.LENGTH_SHORT).show();

        }

        Button btnBack = findViewById(R.id.characterDetailReturnBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}