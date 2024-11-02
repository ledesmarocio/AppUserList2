package com.example.appuserlist2;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appuserlist2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserLiveViewModel userLiveViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userLiveViewModel=new ViewModelProvider(this).get(UserLiveViewModel.class);
       tarea();
    }
    public void tarea(){
        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre= binding.etNombre.getText().toString();
                int edad;
                if(!nombre.isEmpty() && !binding.etEdad.getText().toString().isEmpty()){
                    try{
                        edad=Integer.parseInt(binding.etEdad.getText().toString());
                        Usuario usuario= new Usuario(nombre,edad);
                        userLiveViewModel.addUser(usuario);
                        binding.etNombre.setText("");
                        binding.etEdad.setText("");
                    }catch (NumberFormatException e){
                        binding.tvVerUsuario.setText("Error de formato en la edad");
                    }
                } else{
                    binding.tvVerUsuario.setText("ERROR");
                }
                binding.etNombre.setText("");
                binding.etEdad.setText("");
            }
        });
        final Observer<List<Usuario>> listaObservada= new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                String texto="";
                for(Usuario u: usuarios){
                    texto+="Nombre: "+u.getNombre()+" Edad: " +u.getEdad()+"\n";
                }
                binding.tvVerUsuario.setText(texto);
            }
        };
        userLiveViewModel.getUserList().observe(this, listaObservada);
    }
}