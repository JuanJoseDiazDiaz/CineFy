import com.example.cinefy.ui.model.Movie
import retrofit2.HttpException
import kotlinx.coroutines.delay
import retrofit2.http.GET

interface MovieApiService {
    @GET("/movies")
    suspend fun getMovies(): List<Movie>


}


