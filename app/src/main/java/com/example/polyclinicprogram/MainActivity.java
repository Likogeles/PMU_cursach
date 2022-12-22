package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button patientsBtn = findViewById(R.id.patientsBtn);
        patientsBtn.setOnClickListener(view -> {
            Intent patientListActivity = new Intent(this, PatientListActivity.class);
            startActivity(patientListActivity);
        });

        Button therapiesBtn = findViewById(R.id.therapiesBtn);
        therapiesBtn.setOnClickListener(view -> {
            Intent therapiesListActivity = new Intent(this, TherapiesListActivity.class);
            startActivity(therapiesListActivity);
        });

        Button reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(view -> {

            ArrayList<Patient> patientArrayList = new ArrayList<>();
            PatientsDBService patientsDBService = new PatientsDBService(this);
            patientsDBService.readPatients(patientArrayList);
            try {
                createPDF(patientArrayList, "Name");
            }catch (Exception ex){
                System.out.println(ex);
            }
        });

        Intent bundleIntent = getIntent();
        ArrayList<User> arr = (ArrayList<User>) bundleIntent.getSerializableExtra("user");
        if (arr != null){
            user = arr.get(0);
            if (!user.admin){
                therapiesBtn.setEnabled(false);
            }
        }
    }

    public void createPDF(ArrayList<Patient> patientArrayList, String name) throws IOException, DocumentException {
        System.out.println("Worked!!!");
        Document document = new Document();  // create the document
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists()) {
            root.mkdirs();   // create root directory in sdcard
        }
        File file = new File("/storage/emulated/0/Download/PDF/" + name + ".pdf");

        PdfWriter.getInstance(document,new FileOutputStream(file));
        document.open();  // open the directory

        java.util.List<Paragraph> paragraphs = new ArrayList<>();
        final String FONT = "/assets/arial.ttf";

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 14, Font.NORMAL);

        for(Patient patient:patientArrayList){
            Paragraph p1 = new Paragraph();
            p1.setFont(font);

            p1.add(patient.toString());

            paragraphs.add(p1);
        }
        Paragraph p2 = new Paragraph();
        p2.setFont(font);
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        p2.add("Пациенты:");
        document.add(p2);

        for(Paragraph paragraph:paragraphs){
            document.add(paragraph);
        }
        document.addCreationDate();
        document.close();
    }

}
