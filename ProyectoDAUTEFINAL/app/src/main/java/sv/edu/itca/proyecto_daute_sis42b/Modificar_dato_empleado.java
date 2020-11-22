package sv.edu.itca.proyecto_daute_sis42b;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Modificar_dato_empleado extends AppCompatActivity {
    private EditText usuario, passAterior, passNew,passNewR, tel;
    private CheckBox cambiarPass;
    private  String datos, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_modificar_dato_empleado);
        Bundle bundle = getIntent().getExtras();
        datos = bundle.getString("datos");
        id = bundle.getString("id");
        usuario = findViewById(R.id.et_UsuarioNuevo);
        passAterior = findViewById(R.id.et_Contraseniaantigua);
        passNew = findViewById(R.id.et_ContraseniaNueva);
        passNewR = findViewById(R.id.et_ContraseniaNueva_repetida);
        tel = findViewById(R.id.et_TelefonoNuevo);
        cambiarPass = findViewById(R.id.cb_actualizar_contrasenia);

        try {
            JSONObject jsonObject = new JSONObject(datos);
            if(jsonObject!=null){
                usuario.setText(jsonObject.getString("usuario"));
                tel.setText(jsonObject.getString("tel"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Titulo para Administrador
        setTitle(R.string.titulo_Modificar_Empleado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_usuario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.Cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Confirmacion");
                builder.setMessage("¿Desea Cerrar Sesión?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Ventana_Cerrar_sesion = new Intent(Modificar_dato_empleado.this,Iniciar_Sesion.class);
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

    public void cancelar(View view) {
        finish();
    }

    public void actualizar(View view) {
        RequestParams params = new RequestParams();
        params.put("id",id);
        params.put("user",usuario.getText().toString());
        params.put("pass",passAterior.getText().toString());
        params.put("tel",tel.getText().toString());

        if(cambiarPass.isChecked()){
            params.put("passnew",passNew.getText().toString());
            params.put("passR",passNewR.getText().toString());

        }else {
            params.put("passnew","0");
            params.put("passR","0");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://192.168.1.2/appPlanilla/modificarempleado.php";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String s = new String(responseBody);
                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if(jsonObject.names().get(0).equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("resultado"),Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),"erro de conexion",Toast.LENGTH_LONG).show();

            }
        });
    }
}
