package com.softwareag.apilyzer.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.Issues;
import com.softwareag.apilyzer.model.SubCategory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class APILyzerReport {

  private Document doc;
  private ByteArrayOutputStream b;
  private static Font reportTitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
  private static Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
  private static Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
  private static Font subCategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
  private List<Category> categories;

  public APILyzerReport(List<Category> categories) throws DocumentException {
    this.categories = categories;
    init();
    addMetaData();
    doc.open();
    write();
    doc.close();
  }

  public static void main(String[] args) throws DocumentException {
    Issues issue = new Issues();
    issue.setSummary("Numeric parameter 'limit' of type 'integer' has no maximum defined");
    issue.setDescription("Some numeric parameters in your API do not have the maximum value specified.");
    issue.setRemedy("Set both the minimum and maximum values for numeric parameters to limit the accepted values to the range that works for your application.");
    issue.setSeverity("Low");

    Issues issue1 = new Issues();
    issue1.setSummary("String parameter 'petId' has no pattern defined");
    issue1.setDescription("Some string parameters in your API do not define any pattern for the accepted strings. This means that they do not limit the values that get passed to the API.");
    issue1.setRemedy("Set a well-defined regular expression in the pattern field of string parameters. This ensures that only strings matching the set pattern get passed to your API.");
    issue1.setSeverity("Low");

    Issues issue2 = new Issues();
    issue2.setSummary("Response that should contain a body has no schema defined");
    issue2.setDescription("You have not defined any schemas for responses that should contain a body.");
    issue2.setRemedy("Define schemas for all responses that should have a body.Alternatively, if you do not want to include a body, you can change the HTTP status code in the response to one that should not have a body.");
    issue2.setSeverity("High");

    List<Issues> issuesList = new ArrayList<>();
    issuesList.add(issue);
    issuesList.add(issue1);

    List<Issues> issuesList1 = new ArrayList<>();
    issuesList1.add(issue2);

    SubCategory subCategory = new SubCategory();
    subCategory.setName("Parameters");
    subCategory.setIssues(issuesList);

    SubCategory subCategory1 = new SubCategory();
    subCategory1.setName("Response Definition");
    subCategory1.setIssues(issuesList1);

    List<SubCategory> subCategoryList = new ArrayList<>();
    subCategoryList.add(subCategory);
    subCategoryList.add(subCategory1);

    Category category = new Category();
    category.setName("API Standard");
    category.setScore(70);
    category.setSubCategory(subCategoryList);

    List<Category> categoryList = new ArrayList<>();
    categoryList.add(category);

    APILyzerReport apiLyzerReport = new APILyzerReport(categoryList);

  }

  private void init() {
    this.b = new ByteArrayOutputStream();
    doc = new Document();
    try {
      PdfWriter.getInstance(doc, new FileOutputStream("Apilyzer.pdf"));
    } catch (DocumentException | FileNotFoundException e) {
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
    PdfPTable table = new PdfPTable(2);
    try {
      table.setWidthPercentage(50);
      float sideLength = PageSize.A4.rotate().getWidth() * 1 / 2;
      table.setWidths(new float[]{sideLength * 5 / 10, sideLength * 5 / 10});
      table.addCell(Objects.requireNonNull(getLogo()));
      table.addCell(getTitle());
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      doc.add(table);
      doc.add(Chunk.NEXTPAGE);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }
  }

  private PdfPCell getLogo() {
    try {
      Image image = Image.getInstance("SAG.png");
      PdfPCell cell = new PdfPCell(image, true);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setPaddingTop(PageSize.A4.rotate().getHeight() / 4);
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      return cell;
    } catch (BadElementException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private PdfPCell getTitle() {
    Paragraph paragraph = new Paragraph("API Analysis Report", reportTitleFont);
    paragraph.setSpacingBefore(8);
    paragraph.setSpacingAfter(8);
    PdfPCell cell = new PdfPCell(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPaddingTop(PageSize.A4.rotate().getHeight() / 4);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    return cell;
  }

  private void write() throws DocumentException {
    firstPage();
    for (int cIndex = 0; cIndex < categories.size(); cIndex++) {
      Category category = this.categories.get(cIndex);
      Phrase phrase = new Phrase(category.getName(), categoryFont);
      doc.add(phrase);
      doc.add(Chunk.NEWLINE);
      doc.add(Chunk.NEWLINE);
      for (int sc_Index = 0; sc_Index < category.getSubCategory().size(); sc_Index++) {
        SubCategory subCategory = category.getSubCategory().get(sc_Index);
        phrase = new Phrase(subCategory.getName(), subCategoryFont);
        doc.add(phrase);
        createTable(subCategory.getIssues());
        doc.add(Chunk.NEWLINE);
      }
    }

  }

  private void createTable(List<Issues> issues) {
    PdfPTable table = new PdfPTable(new float[]{1, 2, 3, 1});
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.setWidthPercentage(100);

    writeHeader(table);

    for (Issues issue : issues) {
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


