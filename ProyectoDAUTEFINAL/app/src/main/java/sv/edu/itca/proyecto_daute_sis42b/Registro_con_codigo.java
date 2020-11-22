package sv.edu.itca.proyecto_daute_sis42b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registro_con_codigo extends AppCompatActivity{
    private EditText codigo;
    private Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro_con_codigo);

        //Titulo para Iniciar Sesion
        setTitle(getString(R.string.titulo_Registro_con_codigo));

        codigo=findViewById(R.id.et_codigo);
        btnRegistrar=findViewById(R.id.btn_ingresar_codigo);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("cod",codigo.getText().toString());
                AsyncHttpClient cliente = new AsyncHttpClient();

                cliente.post("http://192.168.1.2/appplanilla/codigo.php", params, new AsyncHttpResponseHandler() {
                //cliente.post("https://appplanilla.herokuapp.com/codigo.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode==200){
                            String datos = new String(responseBody);

                            try {
                                JSONObject json = new JSONObject(datos);
                                if(json.names().get(0).equals("error")){
                                    Toast.makeText(getApplicationContext(),json.getString("error"),Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),json.getString("nombre"),Toast.LENGTH_LONG).show();
                                    Intent registro=new Intent(getApplicationContext(),Registro_de_usuario.class);
                                    registro.putExtra("Empresa",json.getString("id"));
                                    registro.putExtra("admin",false);
                                    abrirRegistro(registro);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        //Toast.makeText(getApplicationContext(),"exit",Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(),"Error al conectar",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void registraradmin(View view) {
        Intent ventana = new Intent(getApplicationContext(),Registrar_empresa.class);
        ventana.putExtra("admin",true);
        abrirRegistro(ventana);
    }

    public void abrirRegistro(Intent intent){
        startActivity(intent);
        finish();
    }

}