package com.softwareag.apilyzer.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

public class ReportPageHandler extends PdfPageEventHelper {

  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    try {
      Image image = Image.getInstance("powered_by.jpg");
      image.setAbsolutePosition(665,5);
      image.scalePercent(15f);
      Image image1 = Image.getInstance("powered-By_SAG.png");
      image1.scalePercent(55f);
      image1.setAbsolutePosition(745, 20);
      writer.getDirectContent().addImage(image);
      writer.getDirectContent().addImage(image1);
    } catch (IOException | DocumentException e) {
      e.printStackTrace();
    }
  }


}
