package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.PriorityQueue;

import com.example.wellness360.ml.Cnnmodel1;

public class ADP extends AppCompatActivity {

    Button gallery;
    ImageView imageView;
    TextView result;
    int imageSize = 256;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adp);

        gallery = findViewById(R.id.button2);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });
    }
    public void classifyImage(Bitmap image , int topK) {
        try {
            Cnnmodel1 model = Cnnmodel1.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            Cnnmodel1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


//            float[] confidences = outputFeature0.getFloatArray();
//            int maxPos = 0;
//            float maxConfidence = 0;
//            for (int i = 0; i < confidences.length; i++) {
//                if (confidences[i] > maxConfidence) {
//                    maxConfidence = confidences[i];
//                    maxPos = i;
//                }
//            }

            float[] confidences = outputFeature0.getFloatArray();

            PriorityQueue<Integer> topKIndices = getTopKIndices(confidences, topK);

            String[] classes = {"Alzheimer's Disease", "Cognitively Normal", "Early Mild Cognitive Impairment",
                    "Late Mild Cognitive Impairment", "Mild Cognitive Impairment"};

            // Display the top-K predictions with their confidence scores
            StringBuilder predictionText = new StringBuilder();
            for (int i = 0; i < topK; i++) {
                int classIndex = topKIndices.poll();
                String className = classes[classIndex];
                float confidence = confidences[classIndex];
                predictionText.append(className).append(": ").append(confidence).append("\n");
            }
            result.setText(predictionText.toString());

            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    private PriorityQueue<Integer> getTopKIndices(float[] confidences, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (i1, i2) -> Float.compare(confidences[i1], confidences[i2]));
        for (int i = 0; i < confidences.length; i++) {
            pq.offer(i);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image,5);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}