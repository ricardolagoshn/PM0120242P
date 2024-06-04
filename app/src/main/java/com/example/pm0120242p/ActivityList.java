package com.example.pm0120242p;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Configuracion.Personas;
import Configuracion.SQLiteConexion;
import Configuracion.Trans;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listperson;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        conexion = new SQLiteConexion(this, Trans.DBname, null, Trans.Version);
        listperson = (ListView) findViewById(R.id.listperson);

        ObtenerInfo();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        listperson.setAdapter(adp);

    }

    private void ObtenerInfo()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        lista = new ArrayList<Personas>();

        // Cursor para recorrer los datos de la tabla
        Cursor cursor = db.rawQuery(Trans.SelectAllPerson,null);

        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));

            lista.add(person);
        }

        cursor.close();

        FillDate();

    }

    private void FillDate()
    {
        Arreglo = new ArrayList<String>();
        for(int i=0; i < lista.size(); i++)
        {
            Arreglo.add(lista.get(i).getId() + " "+
                    lista.get(i).getNombres() + " " +
                    lista.get(i).getApellidos());

        }
    }
}