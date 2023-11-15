package com.apunao.elmirador;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    DatabaseReference refUser;
    FirebaseUser user;
    String telefono, nombre, id, correo, fechaSeleccionada, fechaClick;

    TextView tvSeleccionada;
    Button siguiente;

    EditText mHora, mCantidad;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mHora = (EditText) findViewById(R.id.etHora);
        mCantidad = (EditText) findViewById(R.id.etCantidad);
        siguiente = (Button) findViewById(R.id.btnSig);
        tvSeleccionada = (TextView)findViewById(R.id.tvSeleccionada);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        refUser = FirebaseDatabase.getInstance().getReference().child("Usuario");
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ItemUsuario e = dataSnapshot1.getValue(ItemUsuario.class);
                    if(e.getId().equals(user.getUid())) {
                        telefono=e.getTelefono();
                        nombre = e.getNombre();
                        correo = e.getCorreo();
                        id = e.getId();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Opsss.....", Toast.LENGTH_SHORT).show();
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentQR = new Intent(MainActivity.this, ReservaActivity.class);
                Bundle bundleQR = new Bundle();
                bundleQR.putString("fecha", fechaClick);
                bundleQR.putString("fechaR", fechaSeleccionada);
                bundleQR.putString("id", id);
                bundleQR.putString("nombre", nombre);
                bundleQR.putString("correo", correo);
                bundleQR.putString("telefono", telefono);
                bundleQR.putString("cantidad", mCantidad.getText().toString());
                bundleQR.putString("hora", mHora.getText().toString());
                intentQR.putExtras(bundleQR);
                startActivities(new Intent[]{intentQR});

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String fechaFromDate(LocalDate date)
    {
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return date.format(fechaFormatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText)
    {
        //Toast.makeText(this, "POSICION " + position, Toast.LENGTH_SHORT).show();
        if(!dayText.equals("") )
            if(position==5 || position==6 || position==7
                    || position==12 || position==13 || position==14
                    || position==19 || position==20 || position==21
                    || position==26 || position==27 || position==28
                    || position==33 || position==34 || position==35)
            {
                fechaSeleccionada = dayText + " " + monthYearFromDate(selectedDate);
                fechaClick = dayText + "/" +fechaFromDate(selectedDate);
                tvSeleccionada.setText("Fecha Seleccionada: " + fechaSeleccionada);
                Toast.makeText(this, "Fecha Seleccionada " + fechaSeleccionada, Toast.LENGTH_SHORT).show();


            }
            else{
                Toast.makeText(this, "NO ESTA DISPONIBLE. Por favor Seleccione otra Fecha.", Toast.LENGTH_LONG).show();
            }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage("DESEA SALIR DE LA APP.?");
        alertbox.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton Si
            public void onClick(DialogInterface arg0, int arg1) {
                if (currentUser != null) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent1 = new Intent(MainActivity.this, Splash.class);
                    startActivity(intent1);
                    finish();
                } else
                    Toast.makeText(MainActivity.this, "CERRAR SESION", Toast.LENGTH_SHORT).show();

            }
        });
        alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton No
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertbox.show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}