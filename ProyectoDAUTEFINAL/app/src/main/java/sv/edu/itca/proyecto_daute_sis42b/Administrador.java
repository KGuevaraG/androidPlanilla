package sv.edu.itca.proyecto_daute_sis42b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

public class Administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_administrador);

        //Titulo para Administrador
        setTitle(R.string.tituloAdmnistrador);


        //Probando TABHOST
        TabHost tabControl = findViewById(R.id.mitabhost);
        tabControl.setup();
        TabHost.TabSpec spec;

        spec = tabControl.newTabSpec("EMPLEADO");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Empleados");
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("ASISTENCIA");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Asistencia");
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("PLANILLA DE PAGO");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Planilla");
        tabControl.addTab(spec);
    }

    //Menu de Administrador
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Prueba de Menu Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.admin_Ajustescuenta:
                Intent ventana = new Intent(Administrador.this,Modificar_dato_Admin.class);
                startActivity(ventana);
                break;

            case R.id.admin_cargos:
                Intent ventana2 = new Intent(Administrador.this,Cargos.class);
                startActivity(ventana2);
                break;
            case R.id.admin_departamentos:
                Intent ventana3 = new Intent(Administrador.this,Departamentos.class);
                startActivity(ventana3);
                break;
            case R.id.admin_Cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this); //Codigo de dialogo de confirmacion
                builder.setCancelable(false);
                builder.setTitle("Confirmacion");
                builder.setMessage("¿Desea Cerrar Sesión?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Ventana_Cerrar_sesion = new Intent(Administrador.this,Iniciar_Sesion.class);
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

    // ventana para informacion de horas trabajadas
    public void informacionHorastrabajadas(View v){
        Intent ventana = new Intent(Administrador.this,Informacion_Horas_trabajadas.class);
        startActivity(ventana);

    }



}