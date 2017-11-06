package warriorsoft.com.postcosas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import warriorsoft.com.Lugar;
import warriorsoft.com.interfaces.LugaresService;
import warriorsoft.com.utils.FileUtils;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etDescripcion;
    ImageView imageView;
    TextView tvRuta;
    Uri imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        imageView = findViewById(R.id.ivPhoto);
        tvRuta = findViewById(R.id.tvRuta);

        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagen!=null){
                    if(GuardaLugar(etNombre.getText().toString(),etDescripcion.getText().toString(),MainActivity.this,imagen)){
                        Toast.makeText(getApplicationContext(),"Guardado",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Error:", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,151);
                Toast.makeText(MainActivity.this, "Abrir el image chooser", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==151 && data!=null){
            if(resultCode==RESULT_OK){
                imagen = data.getData();

                imageView.setImageURI(imagen);
                String ruta = imagen.getPath();
                tvRuta.setText(ruta);
            }

        }
    }

    private String getRealPathFromURI(Uri imagen, Activity context) {
        String result;
        Cursor cursor = context.getContentResolver().query(imagen,null,null,null,null);
        if(cursor == null){
            result = imagen.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = getString(idx);
            cursor.close();
        }

        return result;
    }

    private boolean GuardaLugar(final String nombre, final String Descripcion, final Context context, Uri archivo){
        final boolean resultado = true;
        String url = "http://148.202.37.19/api_php/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        File imagenOriginal = FileUtils.getFile(context,archivo);

        RequestBody descripcionPart = RequestBody.create(MultipartBody.FORM,Descripcion);
        RequestBody nombrePart = RequestBody.create(MultipartBody.FORM,nombre);

        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(archivo)), imagenOriginal);

        MultipartBody.Part subir = MultipartBody.Part.createFormData("imagen",imagenOriginal.getName(),filePart);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LugaresService servicio = retrofit.create(LugaresService.class);

        Call<ResponseBody> lugarCall = servicio.CrearLugar(nombrePart,descripcionPart,subir);
        lugarCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Toast.makeText(context, "Subido ok!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error al subir", Toast.LENGTH_SHORT).show();
            }
        });

        return resultado;
    }
}
