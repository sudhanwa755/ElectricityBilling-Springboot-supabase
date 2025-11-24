
package com.example.ebs.service;

import com.example.ebs.entity.Bill;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public PdfService() {
    }

    private double safeVal(Double d) {
        return d == null ? 0.0 : d;
    }

    public byte[] billToPdf(Bill b) {
        try {
            Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font h1 = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font h2 = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font text = new Font(Font.HELVETICA, 11);

            Paragraph title = new Paragraph("Electricity Bill", h1);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));

            // Main bill table
            PdfPTable tableMain = new PdfPTable(2);
            tableMain.setWidthPercentage(100);
            tableMain.addCell(cell("Bill No", h2));
            tableMain.addCell(cell(b.getBillNo(), text));
            tableMain.addCell(cell("Customer", h2));
            tableMain.addCell(cell(b.getCustomer().getName(), text));
            tableMain.addCell(cell("Bill Date", h2));
            tableMain.addCell(cell(String.valueOf(b.getBillDate()), text));
            tableMain.addCell(cell("Units", h2));
            tableMain.addCell(cell(String.valueOf(b.getUnits()), text));
            tableMain.addCell(cell("Energy Charge (₹)", h2));
            tableMain.addCell(cell(String.valueOf(safeVal(b.getEnergyCharge())), text));
            tableMain.addCell(cell("Fixed Charge (₹)", h2));
            tableMain.addCell(cell(String.valueOf(safeVal(b.getFixedCharge())), text));
            tableMain.addCell(cell("Taxes (₹)", h2));
            tableMain.addCell(cell(String.valueOf(safeVal(b.getTaxes())), text));
            tableMain.addCell(cell("Total (₹)", h2));
            tableMain.addCell(cell(String.valueOf(safeVal(b.getTotal())), text));
            tableMain.addCell(cell("Status", h2));
            tableMain.addCell(cell(b.getStatus(), text));
            doc.add(tableMain);

            // Billing period
            if (b.getReading() != null) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
                PdfPTable periodTbl = new PdfPTable(2);
                periodTbl.setWidthPercentage(100);
                periodTbl.addCell(cell("Period", h2));
                String periodStr = b.getReading().getPeriodStart().format(fmt) + " to " + b.getReading().getPeriodEnd().format(fmt);
                periodTbl.addCell(cell(periodStr, text));
                doc.add(new Paragraph(" "));
                doc.add(periodTbl);
            }

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    private PdfPCell cell(String s, Font f) {
        PdfPCell c = new PdfPCell(new Phrase(s, f));
        c.setPadding(6);
        return c;
    }
}
