package sv.edu.itca.proyecto_daute_sis42b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Administrador extends AppCompatActivity {
    private TableLayout tableLayout1,tableLayout2,tableLayout3;
    private TableRow tableRo;
    private TextView  tvNombre, tvApellido, tvUser, tvTelefono, tvSalario, tvHoras, tvRango, tvIsss, tvAFP, tvExtras, tvTotal;
    private EditText et_buscar_empleado;
    String url;

    private String id, user,emprea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_administrador);

        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");
        user=bundle.getString("user");
        emprea=bundle.getString("emprea");
        tableLayout1=findViewById(R.id.tabla_Empleado);

        et_buscar_empleado= findViewById(R.id.et_buscar_empleado);

//tabla de empleados
        RequestParams params = new RequestParams();
        params.put("empresa",emprea);
        params.put("user","");
        AsyncHttpClient client = new AsyncHttpClient();
        url="http://192.168.1.2/appplanilla/listaempleados.php";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String s = new String(responseBody);
                    try {

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            tableRo=new TableRow(getApplicationContext());

                            tvNombre = new TextView(getApplicationContext());
                            tvApellido = new TextView(getApplicationContext());
                            tvUser = new TextView(getApplicationContext());
                            tvTelefono = new TextView(getApplicationContext());
                            tvSalario = new TextView(getApplicationContext());


                             tvNombre.setText(object.getString("nombre"));
                             tvApellido.setText(object.getString("apellido"));
                             tvUser.setText(object.getString("usuario"));
                             tvTelefono.setText(object.getString("tel"));
                             tvSalario.setText(object.getString("salario"));

                            tvNombre.setGravity(Gravity.CENTER);
                            tvNombre.setPadding(1,1,1,1);

                            tvApellido.setGravity(Gravity.CENTER);
                            tvApellido.setPadding(1,1,1,1);

                            tvUser.setGravity(Gravity.CENTER);
                            tvUser.setPadding(1,1,1,1);

                            tvTelefono.setGravity(Gravity.CENTER);
                            tvTelefono.setPadding(1,1,1,1);

                            tvSalario.setGravity(Gravity.CENTER);
                            tvSalario.setPadding(1,1,1,1);



                             tableRo.addView(tvUser);
                             tableRo.addView(tvNombre);
                             tableRo.addView(tvApellido);
                             tableRo.addView(tvTelefono);
                             tableRo.addView(tvSalario);

                            tableLayout1.addView(tableRo);

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

        //tabla de asistencia
        tableLayout2=findViewById(R.id.tabla_Asistencia);
        RequestParams params2 = new RequestParams();
        params2.put("emp",emprea);

        AsyncHttpClient client2 = new AsyncHttpClient();
        url="http://192.168.1.2/appplanilla/asistencia.php";
        client.post(url, params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String s = new String(responseBody);

                    try {

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            tableRo=new TableRow(getApplicationContext());

                            tvNombre = new TextView(getApplicationContext());
                            tvHoras = new TextView(getApplicationContext());


                            tvNombre.setText(object.getString("nombre"));
                            tvHoras.setText(object.getString("tiempo"));

                            tvNombre.setGravity(Gravity.CENTER);
                            tvNombre.setPadding(1,1,1,1);


                            tvHoras.setGravity(Gravity.CENTER);
                            tvHoras.setPadding(1,1,1,1);



                            tableRo.addView(tvNombre);

                            tableRo.addView(tvHoras);

                            tableLayout2.addView(tableRo);

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

        //tabla Planilla
        tableLayout3=findViewById(R.id.tabla_Admin_Planilla);
        RequestParams params3 = new RequestParams();
        params3.put("emp",emprea);

        AsyncHttpClient client3 = new AsyncHttpClient();
        url="http://192.168.1.2/appplanilla/planillapago.php";
        client.post(url, params3, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String s = new String(responseBody);
                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                    try {

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            float sal =Float.parseFloat(object.getString("salario"));

                            float af2= (float) (sal*0.0725);

                            float iss2= (float) (sal*0.075);

                            float tot = sal+(af2+iss2);

                            //tvRango=new TextView(getApplicationContext());
                            tvNombre=new TextView(getApplicationContext());
                            tvSalario= new TextView(getApplicationContext());
                            tvHoras=new TextView(getApplicationContext());
                            tvExtras=new TextView(getApplicationContext());
                            tvAFP= new TextView(getApplicationContext());
                            tvIsss=new TextView(getApplicationContext());
                            tvTotal=new TextView(getApplicationContext());

                            //tvRango.setText(object.getString("inicio")+"-"+object.getString("fin"));
                            tvNombre.setText(object.getString("nombre")+" "+object.getString("apellido"));
                            tvSalario.setText(object.getString("salario"));
                            tvHoras.setText(object.getString("horasnormales"));
                            tvExtras.setText(object.getString("horasextras"));
                            tvAFP.setText(af2+"");
                            tvIsss.setText(iss2+"");
                            tvTotal.setText(tot+"");

                            tableRo=new TableRow(getApplicationContext());

                            //tableRo.addView(tvRango);
                            tableRo.addView(tvNombre);
                            tableRo.addView(tvSalario);
                            tableRo.addView(tvHoras);
                            tableRo.addView(tvExtras);
                            tableRo.addView(tvIsss);
                            tableRo.addView(tvAFP);
                            tableRo.addView(tvTotal);

                            tableLayout3.addView(tableRo);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"error de json",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



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


    public void agregar(View view) {
        Intent intent = new Intent(getApplicationContext(),Registro_de_usuario.class);
        intent.putExtra("Empresa",emprea);
        intent.putExtra("admin",false);
        startActivity(intent);
    }

    public void modificar(View view) {
        String usuario= et_buscar_empleado.getText().toString();

        RequestParams params = new RequestParams();
        params.put("empresa",emprea);
        params.put("user",usuario);

        AsyncHttpClient client =  new AsyncHttpClient();

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject!=null){
                        if(jsonObject.names().get(0).equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("usuario"),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Modificar_dato_empleado.class);
                            intent.putExtra("datos",s);
                            intent.putExtra("id",jsonObject.getString("id"));
                            intent.putExtra("admin",true);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Json nullo",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Json error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}