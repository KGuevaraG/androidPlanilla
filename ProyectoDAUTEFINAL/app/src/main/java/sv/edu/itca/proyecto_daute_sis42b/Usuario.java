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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Usuario extends AppCompatActivity {

   private TextView usuario;
   private String user, id;
   private Button entrada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_usuario);
        Bundle bundle = getIntent().getExtras();
        usuario= findViewById(R.id.tv_usuario);
        user=bundle.getString("user");
        id=bundle.getString("id");
        usuario.setText(user);
        entrada =findViewById(R.id.btn_Asistencia_entrada);
        setTitle(getString(R.string.titulo_Bienvenido_usuario));

        entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params= new RequestParams();
                params.put("id",id);
                AsyncHttpClient client = new AsyncHttpClient();
                String url="http://192.168.1.2/appplanilla/usuario.php";
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode==200){
                            String respuesta = new String(responseBody);

                            try {
                                JSONObject jsonObject = new JSONObject(respuesta);
                                Toast.makeText(getApplicationContext(),jsonObject.getString("resultado"),Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(),"Error de conexion",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


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

                RequestParams params = new RequestParams();
                params.put("id",id);

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "http://192.168.1.2/appPlanilla/datosempleado.php";
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode==200){
                            String respuesta = new String(responseBody);
                            Intent ventana = new Intent(Usuario.this,Modificar_dato_empleado.class);
                            ventana.putExtra("datos", respuesta);
                            ventana.putExtra("id",id);
                            startActivity(ventana);

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


                break;

            case R.id.Cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Confirmacion");
                builder.setMessage("¿Desea Cerrar Sesión?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Ventana_Cerrar_sesion = new Intent(Usuario.this,Iniciar_Sesion.class);
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

    //Pantalla de Planilla de pago de Usuario
    public void ProcesoPlanilladepago(View v){

        RequestParams params = new RequestParams();
        params.put("id",id);

        AsyncHttpClient client = new AsyncHttpClient();

        String url="http://192.168.1.2/appplanilla/planilla.php";

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String datos = new String(responseBody);
                    Intent ventana = new Intent(Usuario.this,Planilla_de_pago.class);
                    ventana.putExtra("datos",datos);
                    startActivity(ventana);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
}