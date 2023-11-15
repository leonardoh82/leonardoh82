package com.apunao.elmirador;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReservaActivity extends AppCompatActivity {
    public RecyclerView recyclerViewMenu;
    private DatabaseReference referenceMenu;
    private DatabaseReference referenceMesa;
    private ArrayList<ItemMesa> listReserva;
    private ArrayList<ItemMesa> listMesa;
    private AdaptadorMenu adaptadorMenu;
    String fecha, fechaReserva, hora, cantidad, id, nombre, correo,telefono;
    public TextView fechaElegida;
    int aNums[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fecha = getIntent().getExtras().getString("fecha");
        fechaReserva = getIntent().getExtras().getString("fechaR");
        hora = getIntent().getExtras().getString("hora");
        cantidad= getIntent().getExtras().getString("cantidad");

        id= getIntent().getExtras().getString("id");
        nombre= getIntent().getExtras().getString("nombre");
        correo= getIntent().getExtras().getString("correo");
        telefono= getIntent().getExtras().getString("telefono");
        hora= getIntent().getExtras().getString("hora");


        recyclerViewMenu =(RecyclerView) findViewById(R.id.listaReserva);
        fechaElegida =(TextView) findViewById(R.id.tvFechaElegida);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(new GridLayoutManager(this, 3));
        listReserva =new ArrayList<ItemMesa>();
        listMesa =new ArrayList<ItemMesa>();

        fechaElegida.setText(fechaReserva);

        //cargarMesa();


        for (int i=1; i<10;i++){
                    ItemMesa iMe = new ItemMesa();
                    iMe.id = "mesa0"+i;
                    iMe.capacidad = "libre";
                    listMesa.add(iMe);
                }
                for (int i=10; i<21;i++){
                    ItemMesa iMe = new ItemMesa();
                    iMe.id = "mesa"+i;
                    iMe.capacidad = "libre";
                    listMesa.add(iMe);
                }

        cargar();

    }

    private void cargar() {
        referenceMenu = FirebaseDatabase.getInstance().getReference().child("Reserva");
        referenceMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listReserva.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ItemReserva item = dataSnapshot1.getValue(ItemReserva.class);

                    if (item.getFecha().equals(fecha)) {
                        ItemMesa iMe = new ItemMesa();
                        iMe.id = item.getMesa();
                        iMe.capacidad = "ocupada";
                        listReserva.add(iMe);
                        switch (iMe.getId()){
                            case "mesa01": listMesa.get(0).setCapacidad("ocupada");
                            break;
                            case "mesa02": listMesa.get(1).setCapacidad("ocupada");
                                break;
                            case "mesa03": listMesa.get(2).setCapacidad("ocupada");
                                break;
                            case "mesa04": listMesa.get(3).setCapacidad("ocupada");
                                break;
                            case "mesa05": listMesa.get(4).setCapacidad("ocupada");
                                break;
                            case "mesa06": listMesa.get(5).setCapacidad("ocupada");
                                break;
                            case "mesa07": listMesa.get(6).setCapacidad("ocupada");
                                break;
                            case "mesa08": listMesa.get(7).setCapacidad("ocupada");
                                break;
                            case "mesa09": listMesa.get(8).setCapacidad("ocupada");
                                break;
                            case "mesa10": listMesa.get(9).setCapacidad("ocupada");
                                break;
                            case "mesa11": listMesa.get(10).setCapacidad("ocupada");
                                break;
                            case "mesa12": listMesa.get(11).setCapacidad("ocupada");
                                break;
                            case "mesa13": listMesa.get(12).setCapacidad("ocupada");
                                break;
                            case "mesa14": listMesa.get(13).setCapacidad("ocupada");
                                break;
                            case "mesa15": listMesa.get(14).setCapacidad("ocupada");
                                break;
                            case "mesa16": listMesa.get(15).setCapacidad("ocupada");
                                break;
                            case "mesa17": listMesa.get(16).setCapacidad("ocupada");
                                break;
                            case "mesa18": listMesa.get(17).setCapacidad("ocupada");
                                break;
                            case "mesa19": listMesa.get(18).setCapacidad("ocupada");
                                break;
                            case "mesa20": listMesa.get(19).setCapacidad("ocupada");
                                break;
                        }
                    }

                }

                adaptadorMenu = new AdaptadorMenu(ReservaActivity.this,listMesa,listReserva,fecha,hora, cantidad, nombre, id, correo,telefono);
                recyclerViewMenu.setAdapter(adaptadorMenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Opsss.....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarMesa() {
        referenceMesa = FirebaseDatabase.getInstance().getReference().child("Mesa");
        referenceMesa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMesa.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ItemMesa item = dataSnapshot1.getValue(ItemMesa.class);
                    listMesa.add(item);

                }

                //adaptadorMenu = new AdaptadorMenu(ReservaActivity.this,listReserva,listMesa,fecha);
                //recyclerViewMenu.setAdapter(adaptadorMenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Opsss.....", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
