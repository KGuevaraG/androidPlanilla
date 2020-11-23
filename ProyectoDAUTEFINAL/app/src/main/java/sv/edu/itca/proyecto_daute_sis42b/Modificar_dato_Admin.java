package sv.edu.itca.proyecto_daute_sis42b;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Modificar_dato_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_modificar_dato_admin);

        //Titulo para Administrador
        setTitle(R.string.titulo_modificar_datos_administrador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case R.id.admin_Cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this); //Codigo de dialogo de confirmacion
                builder.setCancelable(false);
                builder.setTitle("Confirmacion");
                builder.setMessage("¿Desea Cerrar Sesión?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Ventana_Cerrar_sesion = new Intent(Modificar_dato_Admin.this,Iniciar_Sesion.class);
                        startActivity(Ventana_Cerrar_sesion);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //no se coloca nada
                    }
                });
                builder.create();
                builder.show(); // fin de codigo de dialogo de confirmacion
        }
        return super.onOptionsItemSelected(item);
    }
}
