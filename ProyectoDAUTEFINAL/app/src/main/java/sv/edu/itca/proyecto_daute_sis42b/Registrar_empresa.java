package sv.edu.itca.proyecto_daute_sis42b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registrar_empresa extends AppCompatActivity {
    private EditText empresa;
    private Button registrar;
    private boolean admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_empresa);
        empresa= findViewById(R.id.et_nombre_empresa);
        registrar= findViewById(R.id.btn_registrar_empresa);

        Bundle bundle= getIntent().getExtras();
        admin=bundle.getBoolean("admin");

        setTitle("Registro de empresa");
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("empresa",empresa.getText().toString());

                AsyncHttpClient client = new AsyncHttpClient();
                String url="http://192.168.1.2/appPlanilla/registroEmpresa.php";
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
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("exito"),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),Registro_de_usuario.class);
                                    intent.putExtra("admin",admin);
                                    intent.putExtra("Empresa",jsonObject.getString("id"));
                                    startActivity(intent);
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
        });

    }

    public void cancelar(View view) {
        finish();
    }
}