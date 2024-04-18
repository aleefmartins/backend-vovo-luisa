package com.backendvovoluisa.controller;

import java.util.stream.Stream;
import com.backendvovoluisa.entity.UserAccount;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFGenerator {

    public byte[] generatePDF(List<UserAccount> userAccounts) throws IOException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph(" "));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String title = "Documento gerado em " + now.format(formatter);
        Paragraph titleParagraph = new Paragraph(title);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setSpacingBefore(10);
        addTableHeader(table);
        for (UserAccount userAccount : userAccounts) {
            addTableRow(table, userAccount);
        }
        document.add(table);

        document.close();
        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Nome", "CPF", "Telefone","Email","Data de Registro")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTableRow(PdfPTable table, UserAccount userAccount) {
        table.addCell(userAccount.getUserName());
        table.addCell(String.valueOf(userAccount.getUserCpf()));
        table.addCell(String.valueOf(userAccount.getUserPhone()));
        table.addCell(userAccount.getUserEmail());
        table.addCell(userAccount.getUserDataCriacao().toString());
    }
}

