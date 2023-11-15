package com.apunao.elmirador;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ReservaOk extends AppCompatActivity {

    String fecha, hora, cantidad, mesa, id, nombre, telefono, correo;
    TextView tvFecha, tvHora, tvCantidad, tvMesa, tvId, tvCliente, tvTelefono, tvCorreo,tvNumero;

    Button btnAceptar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ok);

        fecha= getIntent().getExtras().getString("fecha");
        hora= getIntent().getExtras().getString("hora");
        cantidad= getIntent().getExtras().getString("cantidad");
        mesa= getIntent().getExtras().getString("mesa");
        id= getIntent().getExtras().getString("id");
        nombre= getIntent().getExtras().getString("nombre");
        telefono= getIntent().getExtras().getString("telefono");
        correo= getIntent().getExtras().getString("correo");

        tvFecha=(TextView) findViewById(R.id.tvFecha);
        tvHora=(TextView) findViewById(R.id.tvHora);
        tvCantidad=(TextView) findViewById(R.id.tvCantidad);
        tvMesa=(TextView) findViewById(R.id.tvMesa);
        tvCliente=(TextView) findViewById(R.id.tvCliente);
        tvNumero =(TextView) findViewById(R.id.tvNumero);
        btnAceptar=(Button) findViewById(R.id.btnAceptar);

        tvFecha.setText(fecha);
        tvHora.setText(hora);
        tvCantidad.setText(cantidad);
        tvMesa.setText(mesa);
        tvCliente.setText(nombre);

        Date date = new Date();
        SimpleDateFormat dateFormatInfo = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        //SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String codigo = dateFormatInfo.format(date);
        tvNumero.setText(codigo);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Reserva");

                HashMap<String,Object> mapRef=new HashMap<>();

                mapRef.put("cliente",id);
                mapRef.put("fecha",fecha);
                mapRef.put("hora",hora);
                mapRef.put("cantidad",cantidad);
                mapRef.put("mesa", mesa);
                mapRef.put("nombre", nombre);
                mapRef.put("id", codigo);

                ref.child(codigo).updateChildren(mapRef);


                AlertDialog.Builder alertbox = new AlertDialog.Builder(ReservaOk.this);
                alertbox.setMessage("SU RESERVA FUE EXITOSA...");
                alertbox.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    //Funcion llamada cuando se pulsa el boton Si
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(ReservaOk.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                /*alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    //Funcion llamada cuando se pulsa el boton No
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(mContext, "Seleccione una MESA", Toast.LENGTH_LONG).show();
                        v.setBackgroundColor(mContext.getResources().getColor(R.color.colorDisponible));
                    }
                });*/

                alertbox.show();


            }
        });
    }
}