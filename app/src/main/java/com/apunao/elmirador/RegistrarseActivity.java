package com.apunao.elmirador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;
import java.util.HashMap;

public class RegistrarseActivity extends AppCompatActivity{
    private EditText nombre,mail,pass,telefono;
    private Button btnRegistrarUsuario;
    private FirebaseAuth auth;
    private DatabaseReference referencia;
    private ProgressBar progressBar;
    private Uri fileUri = null;
    private File actualImage;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    private String mUri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("REGISTRARSE");
        nombre = (EditText) findViewById(R.id.nombre);
        telefono = (EditText) findViewById(R.id.telefono);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        btnRegistrarUsuario = (Button) findViewById(R.id.btnCuentaUsuario);
        storageReference= FirebaseStorage.getInstance().getReference("Usuarios");
        progressBar = findViewById(R.id.pb);

        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtNombre=nombre.getText().toString();
                String txtMail=mail.getText().toString();
                String txtTel=telefono.getText().toString();
                String txtPass=pass.getText().toString();

                if(TextUtils.isEmpty(txtNombre)||TextUtils.isEmpty(txtMail)||TextUtils.isEmpty(txtPass)||TextUtils.isEmpty(txtTel))
                    Toast.makeText(getApplicationContext(),"Completar los datos",Toast.LENGTH_SHORT).show();
                else
                if(txtPass.length()<6)
                    Toast.makeText(getApplicationContext(),"Su contraseña debe tener mas de 6 caracteres",Toast.LENGTH_SHORT).show();
                else
                    registrarUsuario(txtNombre, txtTel, txtMail, txtPass);

            }
        });

    }

    private void registrarUsuario(final String name, final String tel, final String email, final String passw){

        progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    final String userid = auth.getCurrentUser().getUid();
                    referencia = FirebaseDatabase.getInstance().getReference("Usuario").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("nombre", name);
                    hashMap.put("correo", email);
                    hashMap.put("telefono", tel);


                    referencia.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Intent intent = new Intent(RegistrarseActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegistrarseActivity.this, "Cuenta creada !", Toast.LENGTH_SHORT).show();

                                /*String refreshToken  = Fire;//FirebaseInstanceId.getInstance().getToken();
                                Token token = new Token(refreshToken);
                                FirebaseDatabase.getInstance().getReference("Token").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
                                */

                                finish();
                            } else {
                                Toast.makeText(RegistrarseActivity.this, "Faltan datos!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegistrarseActivity.this, "Usuario y existe!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistrarseActivity.this, "Error al crear, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(RegistrarseActivity.this, Splash.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
