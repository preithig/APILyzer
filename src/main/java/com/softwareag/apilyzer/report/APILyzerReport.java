package com.softwareag.apilyzer.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.model.SubCategory;
import com.softwareag.apilyzer.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class APILyzerReport {

  private Document doc;
  private ByteArrayOutputStream b;
  private static Font reportTitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 23, Font.BOLD);
  private static Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
  private static Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
  private static Font subCategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
  private EvaluationResult result;
  private List<Category> categories;
  private IssuesRepository issuesRepository;

  @Autowired
  public void setIssuesRepository(IssuesRepository issuesRepository) {
    this.issuesRepository = issuesRepository;
  }

  public APILyzerReport(EvaluationResult result) throws DocumentException {
    this.result = result;
    this.categories = result.getCategories();
    init();
    addMetaData();
    doc.open();
    write();
    doc.close();
  }


  private void init() {
    this.b = new ByteArrayOutputStream();
    doc = new Document();
    try {
      PdfWriter writer = PdfWriter.getInstance(doc, b);
      ReportPageHandler reportPageHandler = new ReportPageHandler();
      writer.setPageEvent(reportPageHandler);
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    doc.setPageSize(PageSize.A4.rotate());
  }

  private void addMetaData() {
    doc.addTitle("API Analysis Report");
    doc.addCreationDate();
  }

  public byte[] export() {
    try {
      byte[] bytes = b.toByteArray();
      b.close();
      return bytes;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void add(Element e) {
    try {
      doc.add(e);
    } catch (DocumentException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void firstPage() {
    PdfPTable table = new PdfPTable(1);
    try {
      table.setWidthPercentage(60);
      float sideLength = PageSize.A4.rotate().getWidth() * 1 / 2;
      table.setWidths(new float[]{sideLength});
      table.addCell(getTitle());
      table.addCell(getInfo("API Name: " + result.getApiName() + " (" + String.format("%.2f", result.getScore()) + "%)"));
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      table.addCell(getInfo("Evaluated On: " + dateFormat.format(result.getEvaluationDate())));
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      doc.add(table);
      doc.add(Chunk.NEXTPAGE);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }
  }

  private PdfPCell getTitle() {
    Paragraph paragraph = new Paragraph("API Analysis Report", reportTitleFont);
    paragraph.setSpacingBefore(10);
    paragraph.setSpacingAfter(10);
    PdfPCell cell = new PdfPCell(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPaddingTop(200);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    return cell;
  }

  private PdfPCell getInfo(String text) {
    Paragraph paragraph = new Paragraph(text);
    paragraph.setSpacingBefore(10);
    paragraph.setSpacingAfter(10);
    PdfPCell cell = new PdfPCell(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPaddingTop(10);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    return cell;
  }

  private void write() throws DocumentException {
    firstPage();
    for (int cIndex = 0; cIndex < categories.size(); cIndex++) {
      Phrase phrase;
      Category category = this.categories.get(cIndex);
      if (category.getSubCategories().size() > 0) {
        phrase = new Phrase(category.getName().replace("_", " "), categoryFont);
        doc.add(phrase);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
      }
      for (int sc_Index = 0; sc_Index < category.getSubCategories().size(); sc_Index++) {
        SubCategory subCategory = category.getSubCategories().get(sc_Index);
        phrase = new Phrase(subCategory.getName().replace("_", " "), subCategoryFont);
        doc.add(phrase);
        createTable(subCategory.getIssueList());
        doc.add(Chunk.NEWLINE);
      }
    }
  }

  private void createTable(List<Issue> issues) {
    PdfPTable table = new PdfPTable(new float[]{1, 2, 3, 1});
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.setWidthPercentage(100);
    table.setKeepTogether(true);

    writeHeader(table);

    for (Issue issue : issues) {
      table.addCell(issue.getSummary());
      table.addCell(issue.getDescription());
      table.addCell(issue.getRemedy());
      table.addCell(issue.getSeverity());
    }

    add(table);
  }

  private void writeHeader(PdfPTable table) {
    PdfPCell cell = new PdfPCell(new Phrase("Summary", fontBold));
    cell.setPaddingBottom(5);
    cell.setPaddingTop(5);
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cell);
    cell = new PdfPCell(new Phrase("Description", fontBold));
    cell.setPaddingBottom(5);
    cell.setPaddingTop(5);
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cell);
    cell = new PdfPCell(new Phrase("Remedy", fontBold));
    cell.setPaddingBottom(5);
    cell.setPaddingTop(5);
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cell);
    cell = new PdfPCell(new Phrase("Severity", fontBold));
    cell.setPaddingBottom(5);
    cell.setPaddingTop(5);
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cell);
  }

}


