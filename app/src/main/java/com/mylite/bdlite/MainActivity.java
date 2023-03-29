package com.mylite.bdlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText CEDULA, NOMBRE, TELEFONO;

    Button INSERTAR, CONSULTAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CEDULA = findViewById(R.id.txtCC);
        NOMBRE = findViewById(R.id.txtNOM);
        TELEFONO = findViewById(R.id.txtTEL);

        INSERTAR = findViewById(R.id.btnInsert);
        CONSULTAR = findViewById(R.id.btnQuery);
    }

    public  void onStart()
    {
        super.onStart();

        INSERTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });

        CONSULTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }
        });

    }

    public void insertar(View view)
    {
        AdminBD admin = new AdminBD(this, "BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();


        String cedula, nombre, telefono;
        cedula = CEDULA.getText().toString();
        nombre = NOMBRE.getText().toString();
        telefono = TELEFONO.getText().toString();


        if(!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty())
        {
            ContentValues Registro = new ContentValues();
            Registro.put("cedula",cedula);
            Registro.put("nombre",nombre);
            Registro.put("telefono",telefono);
            BD.insert("usuarios",null,Registro);
            BD.close();


            CEDULA.setText("");
            NOMBRE.setText("");
            TELEFONO.setText("");

            Toast.makeText(this,"Insertado correctamente", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(this,"Rellene los campos", Toast.LENGTH_LONG).show();
        }


    }


    public  void consultar(View view)
    {
        AdminBD admin = new AdminBD(this, "BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String cedula = CEDULA.getText().toString();

        if(!cedula.isEmpty())
        {
            Cursor fila = BD.rawQuery("select nombre, telefono from usuarios where cedula="+cedula,null);

            if(fila.moveToFirst())
            {
                NOMBRE.setText(fila.getString(0));
                TELEFONO.setText(fila.getString(1));
                BD.close();
            }else
            {
              Toast.makeText(this,"No se encuentra el usuario",Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(this,"Ingrese la cédula para su consulta",Toast.LENGTH_LONG).show();
        }

    }











}