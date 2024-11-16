package co.edu.uniquindio.proyecto.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


import java.io.IOException;
import java.io.InputStream;

// La clase FirebaseConfig está marcada con la anotación @Configuration, lo que indica
// que es una clase de configuración en Spring que define uno o más beans para el contenedor de Spring.
@Configuration
public class FirebaseConfig {

    // Definimos un método bean con la anotación @Bean. Esto le indica a Spring que este método
    // devolverá un objeto que debe ser administrado como un bean en el contexto de la aplicación.
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // Se carga el archivo JSON de la cuenta de servicio de Firebase desde la ruta especificada.
        // Este archivo contiene las credenciales necesarias para autenticar la aplicación con Firebase.
        InputStream serviceAccount = new ClassPathResource("unieventos-1c779-firebase-adminsdk-qwowc-74bae3d23f.json").getInputStream();

        // Se configura las opciones de Firebase utilizando las credenciales leídas del archivo JSON.
        // También se establece el bucket de almacenamiento en la nube asociado a Firebase Storage.
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))  // Credenciales de la cuenta de servicio
                .setStorageBucket("unieventos-1c779.appspot.com")  // Bucket de almacenamiento en Firebase
                .build();

        // Verificamos si no existen instancias previas de FirebaseApp en la aplicación.
        // FirebaseApp.getApps() devuelve una lista de instancias activas de FirebaseApp.
        // Si la lista está vacía, inicializamos FirebaseApp con las opciones configuradas anteriormente.
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);  // Inicializamos y retornamos la instancia de FirebaseApp.
        }

        // Si ya existe una instancia de FirebaseApp, no la volvemos a inicializar, devolviendo null.
        return null;
    }
}
