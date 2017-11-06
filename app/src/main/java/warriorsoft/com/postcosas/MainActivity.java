package warriorsoft.com.postcosas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);

        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GuardaLugar(etNombre.getText().toString(),etDescripcion.getText().toString(),MainActivity.this)){
                    Toast.makeText(getApplicationContext(),"Guardado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error:", Toast.LENGTH_SHORT).show();
                }
            }
        });

        
        findViewById(R.id.ivPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Abrir el image chooser", Toast.LENGTH_SHORT).show();
            }
        });
        
    }


    private boolean GuardaLugar(final String nombre, final String Descripcion,final Context context){
        final boolean resultado = true;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://148.202.37.19/api_php/save_place.php";

        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Res",response);
                if (response.contains("OK!")){
                    Toast.makeText(context, "Guardado ok!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Error al guardar en server"+response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error de comunicación", Toast.LENGTH_SHORT).show();
                Log.d("Res",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombre",nombre);
                parametros.put("descripcion",Descripcion);
                return parametros;
            }
        };


        queue.add(request);

        return resultado;
    }
}
