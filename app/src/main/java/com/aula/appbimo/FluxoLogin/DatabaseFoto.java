package com.aula.appbimo.FluxoLogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DatabaseFoto {
    public void uploadFoto(Context c, ImageView foto, Map<String, String> docData) {

        // conversão
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        // abrir banco de dados
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("galeria").child("galeria_" + System.currentTimeMillis() + ".jpg")
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // obter a url da imagem
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(c, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();
                                        docData.put("url", uri.toString());
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(c, "Algo deu errado!", Toast.LENGTH_SHORT).show();
                });

    }
}
