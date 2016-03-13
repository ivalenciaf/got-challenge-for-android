package es.npatarino.android.gotchallenge.net;

import java.util.List;

import es.npatarino.android.gotchallenge.GoTCharacter;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Service of GoT rest api with its different endpoints
 */
public interface GoTService {
    @GET("characters")
    Call<List<GoTCharacter>> characters();
}
