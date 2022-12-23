package com.example.polyclinicprogram.reports;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.polyclinicprogram.models.Patient;
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

public class PatientsReport {
    String fileName;

    public PatientsReport(String fileName) {
        this.fileName = fileName;
    }

    public void createPDF(Context context, ArrayList<Patient> patientArrayList) throws IOException, DocumentException {
        Document document = new Document();
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File("/storage/emulated/0/Download/" + fileName + ".pdf");

        PdfWriter.getInstance(document,new FileOutputStream(file));
        document.open();  // open the directory

        java.util.List<Paragraph> paragraphs = new ArrayList<>();
        final String FONT = "/assets/arial.ttf";

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 14, Font.NORMAL);

        for(Patient patient:patientArrayList){
            Paragraph p1 = new Paragraph();
            p1.setFont(font);

            p1.add(patient.toPDFString());

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

        Toast.makeText(context, "Файл сохранён в загрузки", Toast.LENGTH_SHORT).show();
    }
}
