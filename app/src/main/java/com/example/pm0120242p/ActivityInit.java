package com.example.pm0120242p;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Configuracion.SQLiteConexion;
import Configuracion.Trans;

public class ActivityInit extends AppCompatActivity
{
    EditText nombres, apellidos, edad, correo;
    Button btnagregar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_init);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nombres = (EditText) findViewById(R.id.nombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        edad = (EditText) findViewById(R.id.edad);
        correo = (EditText) findViewById(R.id.correo);
        btnagregar = (Button) findViewById(R.id.btningresar);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregar();
            }
        });

    }

    private void Agregar()
    {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Trans.DBname, null, Trans.Version);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores  = new ContentValues();
            valores.put(Trans.nombres, nombres.getText().toString());
            valores.put(Trans.apellidos, apellidos.getText().toString());
            valores.put(Trans.edad, edad.getText().toString());
            valores.put(Trans.correo, correo.getText().toString());

            Long resultado = db.insert(Trans.TablePersonas, Trans.id, valores);


            Toast.makeText(getApplicationContext(),"Registro ingresado con exito " + resultado.toString(),
                    Toast.LENGTH_LONG).show();

            db.close();

        }
        catch (Exception ex)
        {
            ex.toString();
        }


    }
}