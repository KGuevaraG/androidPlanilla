package sv.edu.itca.proyecto_daute_sis42b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Planilla_de_pago extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_planilla_de_pago);

        //Titulo para Planilla de pago
        setTitle(R.string.tituloPlanilla);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.usuario_Ajustes_cuenta:
                Intent ventana = new Intent(Planilla_de_pago.this,Modificar_dato_empleado.class);
                startActivity(ventana);
                break;

            case R.id.Cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Confirmacion");
                builder.setMessage("¿Desea Cerrar Sesión?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Ventana_Cerrar_sesion = new Intent(Planilla_de_pago.this,Iniciar_Sesion.class);
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
                builder.show();


        }
        return super.onOptionsItemSelected(item);
    }
}