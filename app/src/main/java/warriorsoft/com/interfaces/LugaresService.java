package warriorsoft.com.interfaces;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import warriorsoft.com.Lugar;

/**
 * Created by Erick on 06/11/2017.
 */

public interface LugaresService {
    @Multipart
    @POST("save_place.php")
    Call<ResponseBody> CrearLugar(
            @Part("nombre") RequestBody nombre,
            @Part("descripcion") RequestBody descripcion,
            @Part MultipartBody.Part foto);

}
