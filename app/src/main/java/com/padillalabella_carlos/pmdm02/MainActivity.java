package com.padillalabella_carlos.pmdm02;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.padillalabella_carlos.pmdm02.adapter.CharacterAdapter;
import com.padillalabella_carlos.pmdm02.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

/**
 * MainActivity es la clase principal de la aplicación que gestiona la interfaz de usuario.
 * Configura el DrawerLayout, la navegación, y la interacción con las preferencias de idioma.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerCharacter;
    CharacterAdapter characterAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences preferences;

    /**
     * Método que se llama cuando se crea la actividad. Configura los elementos visuales, la barra de herramientas,
     * el menú de navegación y las preferencias.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración de la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        preferences = getSharedPreferences("settings", MODE_PRIVATE);

        initElements();
    }

    /**
     * Infla el menú de opciones de la aplicación.
     *
     * @param menu El menú en el que inflar las opciones.
     * @return true si el menú se infló correctamente.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Muestra un cuadro de diálogo con información sobre la aplicación.
     */
    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setIcon(R.drawable.mario)
                .setMessage(R.string.about_message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Cambia el idioma de la aplicación según el parámetro proporcionado.
     *
     * @param isEnglish Si es true, cambia el idioma a inglés; si es false, a español.
     */
    private void changeLanguage(boolean isEnglish) {
        String language = isEnglish ? "en" : "es";
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        recreate();
    }

    /**
     * Gestiona las opciones seleccionadas del menú de opciones.
     *
     * @param item El item del menú que fue seleccionado.
     * @return true si se gestionó la opción correctamente.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            showAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra un cuadro de diálogo para cambiar el idioma de la aplicación utilizando un Switch.
     */
    private void showLanguageSwitch() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_language_switch, null);
        Switch switchLanguage = view.findViewById(R.id.switch_language);

        switchLanguage.setChecked(preferences.getBoolean("language", false));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar idioma");
        builder.setView(view);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("language", switchLanguage.isChecked());
            editor.apply();

            changeLanguage(switchLanguage.isChecked());
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    /**
     * Configura el contenido del menú de navegación.
     *
     * @param navigationView El NavigationView que contiene los elementos de navegación.
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (id == R.id.nav_language) {
                showLanguageSwitch();
                return true;
            }
            return false;
        });
    }

    /**
     * Inicializa los elementos de la interfaz, como el RecyclerView para mostrar los personajes.
     */
    public void initElements() {

        recyclerCharacter = findViewById(R.id.recycler);
        recyclerCharacter.setLayoutManager(new LinearLayoutManager(this));

        List<Pair<String, Integer>> characterList = new ArrayList<>();
        characterList.add(new Pair<>("Mario", R.drawable.mario));
        characterList.add(new Pair<>("Luigi", R.drawable.luigi));
        characterList.add(new Pair<>("Peach", R.drawable.peach));
        characterList.add(new Pair<>("Toad", R.drawable.toad));

        characterAdapter = new CharacterAdapter(characterList, this);
        recyclerCharacter.setAdapter(characterAdapter);

        Snackbar.make(findViewById(R.id.main), getString(R.string.snackbar_1), Snackbar.LENGTH_LONG).show();

    }
}
