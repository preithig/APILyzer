package com.softwareag.apilyzer.report;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.model.SubCategory;
import com.softwareag.apilyzer.repository.IssuesRepository;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class APILyzerReport {

  private Document doc;
  private ByteArrayOutputStream b;
  private PdfWriter writer;
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
      writer = PdfWriter.getInstance(doc, b);
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

  private void chartPage() {
    try {
      PdfPTable table = new PdfPTable(categories.size());
      Paragraph paragraph = new Paragraph("API COMPLIANCE BY CATEGORY", fontBold);
      paragraph.setSpacingBefore(8);
      paragraph.setSpacingAfter(8);
      doc.add(paragraph);
      doc.add(new LineSeparator());
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.setWidthPercentage(100);
      table.setSpacingBefore(75f);
      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
      table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.getDefaultCell().setPaddingBottom(12f);
      table.setKeepTogether(true);
      Font textFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

      for (Category category : categories) {
        JFreeChart chart = createChart(category.getScore());
        Image image = drawChart(writer.getDirectContent(), chart);
        table.addCell(image);
      }

      for (Category category : categories) {
        table.addCell(new Phrase(category.getName(), textFont));
      }

      add(table);
      doc.add(Chunk.NEXTPAGE);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }

  private JFreeChart createChart(double score) {
    DefaultValueDataset valueDataset = new DefaultValueDataset();
    valueDataset.setValue(score);
    MeterPlot meterPlot = new MeterPlot(valueDataset);
    meterPlot.setRange(new Range(0, 100));
    meterPlot.addInterval(new MeterInterval("Low", new Range(0, 0)));
    meterPlot.addInterval(new MeterInterval("Moderate", new Range(50, 50)));
    meterPlot.addInterval(new MeterInterval("High", new Range(100, 100)));
    meterPlot.setNeedlePaint(Color.darkGray);
    meterPlot.setDialBackgroundPaint(Color.CYAN);
    meterPlot.setDialShape(DialShape.CHORD);
    meterPlot.setMeterAngle(180);
    meterPlot.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    meterPlot.setUnits("%");
    meterPlot.setTickPaint(Color.gray);
    meterPlot.setValuePaint(Color.black);
    meterPlot.setBackgroundPaint(Color.WHITE);

    JFreeChart chart = new JFreeChart("", meterPlot);
    chart.getLegend().visible = false;
    chart.setBackgroundImage(null);
    chart.setBackgroundPaint(Color.white);
    return chart;

  }

  /*private JFreeChart createChart() {
   *//*DefaultPieDataset dataSet = new DefaultPieDataset();
    categories.forEach(category -> dataSet.setValue(category.getName(), category.getScore()));
    dataSet.setValue("API Standard", 71);
    dataSet.setValue("Ease of Use", 75);
    dataSet.setValue("Security Standards", 50);
    JFreeChart chart = ChartFactory.createRingChart("API Score", dataSet, true, true, false);
    chart.getPlot().setBackgroundPaint(Color.WHITE);
    RingPlot ringPlot = (RingPlot) chart.getPlot();
    ringPlot.setSectionDepth(0.3D);
    ringPlot.setSectionOutlinesVisible(false);
    ringPlot.setSeparatorsVisible(false);
    ringPlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
    ringPlot.setLabelFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 12));
    ringPlot.setShadowPaint(null);
    ringPlot.setOutlinePaint(null);
    chart.getLegend().setFrame(BlockBorder.NONE);
    return chart;*//*

    DefaultValueDataset valueDataset = new DefaultValueDataset();
    valueDataset.setValue(71);
    MeterPlot meterPlot = new MeterPlot(valueDataset);
    meterPlot.setRange(new Range(0, 100));
    meterPlot.addInterval(new MeterInterval("Low", new Range(0, 0)));
    meterPlot.addInterval(new MeterInterval("Moderate", new Range(50, 50)));
    meterPlot.addInterval(new MeterInterval("High", new Range(100, 100)));
    meterPlot.setNeedlePaint(Color.darkGray);
    meterPlot.setDialBackgroundPaint(Color.CYAN);
    meterPlot.setDialShape(DialShape.CHORD);
    meterPlot.setMeterAngle(180);
    meterPlot.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    meterPlot.setUnits("%");
    meterPlot.setTickPaint(Color.gray);
    meterPlot.setValuePaint(Color.black);
    meterPlot.setBackgroundPaint(Color.WHITE);
    //meterPlot.setOutlinePaint(Color.white);
    //meterPlot.setOutlineStroke(null);
    //meterPlot.setOutlineVisible(false);

    JFreeChart chart = new JFreeChart("API Standard", meterPlot);
    chart.getLegend().visible = false;
    chart.setBackgroundImage(null);
    chart.setBackgroundPaint(Color.white);
    return chart;
  }*/

  private Image drawChart(PdfContentByte pdfContentByte, JFreeChart chart) {
    float width = PageSize.A4.getWidth() * 3 / 4;
    float height = PageSize.A4.getHeight() * 4 / 10;
    PdfTemplate pdfTemplateChartHolder = pdfContentByte.createTemplate(width, height);
    Graphics2D graphicsChart = pdfTemplateChartHolder.createGraphics(width, height, new DefaultFontMapper());
    Rectangle2D chartRegion = new Rectangle2D.Double(0, 0, width, height);
    chart.draw(graphicsChart, chartRegion);
    graphicsChart.dispose();
    return getImage(pdfTemplateChartHolder);
  }

  private Image getImage(PdfTemplate pdfTemplateChartHolder) {
    try {
      Image chartAsImage = Image.getInstance(pdfTemplateChartHolder);
      chartAsImage.setAlignment(Element.ALIGN_CENTER);
      return chartAsImage;
    } catch (BadElementException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void write() throws DocumentException {
    firstPage();
    chartPage();
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


