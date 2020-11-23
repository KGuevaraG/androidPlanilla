package sv.edu.itca.proyecto_daute_sis42b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static java.security.AccessController.getContext;

public class Registro_de_usuario extends AppCompatActivity {
    private EditText nombre, apellido, user, pass, cel, empresa;
    private Button registrar;
    private String emp;
    private boolean admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro_usuario);

        nombre = findViewById(R.id.et_Nombre);
        apellido = findViewById(R.id.et_Apellido);
        user = findViewById(R.id.et_NombreUsuario);
        pass = findViewById(R.id.et_Contrasenia);
        cel = findViewById(R.id.et_NumeroTelefono);
        registrar = findViewById(R.id.btn_Registrarse);
        //titulo para Registro de Usuario
        setTitle(R.string.titulo_registro_de_usuario);

        Bundle bundle = getIntent().getExtras();
        emp = bundle.getString("Empresa");
        admin= bundle.getBoolean("admin");


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("name", nombre.getText().toString());
                params.put("apel", apellido.getText().toString());
                params.put("user", user.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("empresa", emp);
                params.put("cel", cel.getText().toString());
                params.put("admin",admin);

                //*
                AsyncHttpClient cliente = new AsyncHttpClient();

                cliente.post("http://192.168.1.2/appplanilla/registroEmpleados.php", params, new AsyncHttpResponseHandler() {
                    //cliente.post("https://appplanilla.herokuapp.com/codigo.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200) {
                            String datos = new String(responseBody);
                            //Toast.makeText(getApplicationContext(), datos, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject json = new JSONObject(datos);
                                if (json == null) {
                                    Toast.makeText(getApplicationContext(), "Json vacio", Toast.LENGTH_LONG).show();
                                } else {
                                    if (json.names().get(0).equals("error")) {
                                        Toast.makeText(getApplicationContext(), json.getString("error"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Intent registro = new Intent(getBaseContext(), Iniciar_Sesion.class);

                                        Toast.makeText(getApplicationContext(), json.getString("exito"), Toast.LENGTH_LONG).show();
                                        //startActivity(registro);
                                        finish();
                                    }

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            //Toast.makeText(getApplicationContext(),"exit",Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "Error al conectar", Toast.LENGTH_LONG).show();
                    }
                });

                 //*/
            }
        });


    }

    public void cancelar(View view) {
        finish();
    }
}




