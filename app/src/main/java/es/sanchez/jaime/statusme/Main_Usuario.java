package es.sanchez.jaime.statusme;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Main_Usuario extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageRef;
    private  ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        image = findViewById(R.id.image);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        CargarImagen();
        // Manejar el clic del botón o icono
        findViewById(R.id.image).setOnClickListener(v -> openGallery());
    }

    // Método para abrir la galería de imágenes
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // Método para manejar el resultado de la selección de la imagen desde la galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Subir la imagen seleccionada al almacenamiento en la nube
            uploadImageToCloudStorage();
        }
    }

    private void uploadImageToCloudStorage() {
        if (imageUri != null) {
            String imageName = "";
            String ruta = "";
            FirebaseStorage storage = FirebaseStorage.getInstance();

            if(SesionAuth() != null){
                imageName = "logo"+ SesionAuth();
                ruta = SesionAuth();
            } else if(SesionGoogle() != null){
                imageName = "logo"+ SesionGoogle();
                ruta = SesionGoogle();
            }

            storageRef = storage.getReference().child(ruta);
            StorageReference imageRef = storageRef.child(imageName);

            // Subir el archivo a Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // La carga se completó exitosamente
                        // Aquí puedes manejar la URL de descarga del archivo
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            // Cargar la imagen en el ImageView usando Glide
                            Glide.with(Main_Usuario.this)
                                    .load(downloadUrl)
                                    .into(image);
                        });
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(Main_Usuario.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void CargarImagen(){
        String imageName = "";
        String ruta = "";
        FirebaseStorage storage = FirebaseStorage.getInstance();

        if(SesionAuth() != null){
            imageName = "logo"+ SesionAuth();
            ruta = SesionAuth();
        } else if(SesionGoogle() != null){
            imageName = "logo"+ SesionGoogle();
            ruta = SesionGoogle();
        }

        storageRef = storage.getReference().child(ruta);
        StorageReference imageRef = storageRef.child(imageName);

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // La URL de descarga se obtuvo exitosamente
                String downloadUrl = uri.toString();

                // Cargar la imagen en el ImageView utilizando Glide
                Glide.with(image)
                        .load(downloadUrl)
                        .into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Ocurrió un error al obtener la URL de descarga de la imagen
                Log.e(TAG, "Error al obtener la URL de descarga de la imagen: " + exception.getMessage());
            }
        });
    }

    private String SesionGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String email = account.getEmail();
        if (account != null) {
            // Aquí puedes utilizar el correo electrónico como lo necesites
            Log.d(TAG, "Correo electrónico del usuario: " + email);
        } else {
            // El usuario no ha iniciado sesión con Google
        }
        return email;
    }
    private String SesionAuth(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();

        if (currentUser != null) {
            // Aquí puedes utilizar el correo electrónico como lo necesites
            Log.d(TAG, "Correo electrónico del usuario: " + email);
        } else {
            // El usuario no ha iniciado sesión con Firebase Auth
        }
        return email;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.icono1) {
            Intent signup = new Intent(Main_Usuario.this, Main_Home.class);
            startActivity(signup);
        } else if (view.getId() == R.id.icono5){
            Intent remember2 = new Intent(Main_Usuario.this, Main_Usuario.class);
            startActivity(remember2);
        }
    }
}
