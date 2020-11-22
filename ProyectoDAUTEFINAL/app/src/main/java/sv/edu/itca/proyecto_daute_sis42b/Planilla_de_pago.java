package sv.edu.itca.proyecto_daute_sis42b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Planilla_de_pago extends AppCompatActivity {
    private TextView rango, horas, extras, nombre, isss1,isss2, afp1,afp2, total, salario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_planilla_de_pago);



        rango = findViewById(R.id.txt_Fecha);
        nombre = findViewById(R.id.txt_Nombre_empleado);
        salario = findViewById(R.id.txt_Salario);
        horas = findViewById(R.id.txt_Horas_trabajadas);
        extras = findViewById(R.id.txt_Horas_extras);
        isss1 = findViewById(R.id.txt_Isss_empleado);
        afp1 = findViewById(R.id.txt_Afp_empleado);
        isss2 = findViewById(R.id.txt_Isss);
        afp2 = findViewById(R.id.txt_Afp);
        total = findViewById(R.id.txt_Total);

        Bundle bundle = getIntent().getExtras();
        String datos = bundle.getString("datos");

        try {
            JSONObject jsonObject = new JSONObject(datos);
            if (jsonObject==null){
                Toast.makeText(getApplicationContext(),"Json vacio",Toast.LENGTH_LONG).show();
            }else {
                float sal =Float.parseFloat(jsonObject.getString("salario"));
                float af1= (float) (sal*0.0585);
                float af2= (float) (sal*0.0725);
                float iss1= (float) (sal*0.075);
                float iss2= (float) (sal*0.075);

                float tot = sal-(af1+iss1);

                rango.setText(jsonObject.getString("inicio")+"-"+jsonObject.getString("fin"));
                nombre.setText(jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"));
                salario.setText(jsonObject.getString("salario"));
                horas.setText(jsonObject.getString("horasnormales"));
                extras.setText(jsonObject.getString("horasextras"));
                afp1.setText(af1+"");
                afp2.setText(af2+"");
                isss1.setText(iss1+"");
                isss2.setText(iss2+"");
                total.setText(tot+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Titulo para Planilla de pago
        setTitle(R.string.tituloPlanilla);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_usuario,menu);
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

                        finish();
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