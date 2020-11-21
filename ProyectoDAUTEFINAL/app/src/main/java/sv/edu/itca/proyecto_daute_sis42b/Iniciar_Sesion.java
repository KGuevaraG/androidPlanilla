package sv.edu.itca.proyecto_daute_sis42b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Iniciar_Sesion extends AppCompatActivity {
    private EditText usuario;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_iniciar_sesion);

        usuario = findViewById(R.id.et_Usuario_login);
        password = findViewById((R.id.et_Contrasenia_login));


        //Titulo para Iniciar Sesion
        setTitle(R.string.tituloIniciarSesion);

    }

    //Prueba para Ver Administrador
    public void ProcesoIniciarSesion(View v){

        RequestParams parametros = new RequestParams();
        parametros.put("user",usuario.getText().toString());
        parametros.put("pass",password.getText().toString());
        String url ="http://192.168.1.2/appplanilla/login.php";
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    String cadena = new String(responseBody);

                    try {
                        JSONObject datos = new JSONObject(cadena);
                        if(datos==null){
                            Toast.makeText(getApplicationContext(),"json vacio",Toast.LENGTH_LONG).show();
                        }else {
                           if(datos.names().get(0).equals("error")){
                               Toast.makeText(getApplicationContext(),datos.getString("error"),Toast.LENGTH_LONG).show();
                           }else {
                               Toast.makeText(getApplicationContext(),"Encontrado",Toast.LENGTH_LONG).show();
                               Intent ventana;
                               if(datos.getString("admin").equals("f")||datos.getString("admin").equals("null")){
                                    ventana = new Intent(Iniciar_Sesion.this,Usuario.class);

                               }else {
                                    ventana = new Intent(Iniciar_Sesion.this,Administrador.class);

                               }
                               ventana.putExtra("user",usuario.getText().toString());
                               ventana.putExtra("id",datos.getString("id"));
                               startActivity(ventana);

                           }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }

    //Prueba para ver Registro con código
    public void ProcesoRegistro(View v){
        Intent ventana2 = new Intent(Iniciar_Sesion.this,Registro_con_codigo.class);
        startActivity(ventana2);
    }

    //Prueba para ver Usuario
    public void ProcesoUsuario(View v){
        Intent ventana2 = new Intent(Iniciar_Sesion.this,Usuario.class);
        startActivity(ventana2);
    }

}