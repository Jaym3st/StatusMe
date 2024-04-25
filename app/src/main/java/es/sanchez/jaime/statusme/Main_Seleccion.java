package es.sanchez.jaime.statusme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main_Seleccion extends AppCompatActivity implements View.OnClickListener{

    FirebaseManager firebaseManager;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seleccion);

        firebaseManager = new FirebaseManager();


        CheckBox checkBoxFeliz = findViewById(R.id.feliz);
        CheckBox checkBoxMedio = findViewById(R.id.medio);
        CheckBox checkBoxMal = findViewById(R.id.mal);
        Button guardar = findViewById(R.id.botonguardar);

        checkBoxFeliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxFeliz.isChecked()) {
                    checkBoxMedio.setChecked(false);
                    checkBoxMal.setChecked(false);
                }
            }
        });

        checkBoxMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxMedio.isChecked()) {
                    checkBoxFeliz.setChecked(false);
                    checkBoxMal.setChecked(false);
                }
            }
        });

        checkBoxMal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxMal.isChecked()) {
                    checkBoxFeliz.setChecked(false);
                    checkBoxMedio.setChecked(false);
                }
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                String nombreUsuario = usuario.getEmail();
                firebaseManager.guardarArrayListEnFirebase(nombreUsuario, guardarRegistro());
                Intent back = new Intent(Main_Seleccion.this, Main_Home.class);
                startActivity(back);
            }
        });
    }

    public ArrayList<ArrayList> guardarRegistro() {
        ArrayList<String> valoresSeleccionados = new ArrayList<>();
        ArrayList<String> actividadesSeleccionadas = new ArrayList<>();

        CheckBox checkBox_bien = findViewById(R.id.feliz);
        CheckBox checkBox_normal = findViewById(R.id.medio);
        CheckBox checkBox_mal = findViewById(R.id.mal);
        CheckBox checkBox_correr = findViewById(R.id.correr);
        CheckBox checkBox_jugar = findViewById(R.id.jugar);
        CheckBox checkBox_trabajar = findViewById(R.id.trabajar);
        CheckBox checkBox_familia = findViewById(R.id.familia);
        CheckBox checkBox_amigos = findViewById(R.id.amigos);
        CheckBox checkBox_cita = findViewById(R.id.amor);
        CheckBox checkBox_television = findViewById(R.id.television);
        CheckBox checkBox_compras = findViewById(R.id.compras);
        CheckBox checkBox_leer = findViewById(R.id.leer);

        if (checkBox_bien.isChecked()) {
            valoresSeleccionados.add("Bien");
        }
        if (checkBox_normal.isChecked()) {
            valoresSeleccionados.add("Normal");
        }
        if (checkBox_mal.isChecked()) {
            valoresSeleccionados.add("Mal");
        }
        if (checkBox_correr.isChecked()) {
            actividadesSeleccionadas.add("Correr");
        }
        if (checkBox_jugar.isChecked()) {
            actividadesSeleccionadas.add("Jugar");
        }
        if (checkBox_trabajar.isChecked()) {
            actividadesSeleccionadas.add("Trabjar");
        }
        if (checkBox_familia.isChecked()) {
            actividadesSeleccionadas.add("Familia");
        }
        if (checkBox_amigos.isChecked()) {
            actividadesSeleccionadas.add("Amigos");
        }
        if (checkBox_cita.isChecked()) {
            actividadesSeleccionadas.add("Cita");
        }
        if (checkBox_television.isChecked()) {
            actividadesSeleccionadas.add("TV");
        }
        if (checkBox_compras.isChecked()) {
            actividadesSeleccionadas.add("Compras");
        }
        if (checkBox_leer.isChecked()) {
            actividadesSeleccionadas.add("Leer");
        }

        ArrayList<ArrayList> dia = new ArrayList<>();
        dia.add(valoresSeleccionados);
        dia.add(actividadesSeleccionadas);
        return dia;
    }


    @Override
    public void onClick(View v) {

    }

}