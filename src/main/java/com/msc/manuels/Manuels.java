/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.msc.manuels;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Michael
 */
public class Manuels {

   
    public static void main(String[] args) throws Exception {
        new Manuels().go(args);
    }

    private void launchPdf2Img(File pdf) throws Exception {
        //Attention tres lent

        int pos = pdf.getName().lastIndexOf('.');
        String fileNameNoExt = pdf.getName().substring(0, pos);

        File result = new File(pdf.getParentFile(), fileNameNoExt + ".jpg");

        String args[] = {"magick", "-density", "300", pdf.getAbsolutePath(), result.getAbsolutePath()};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }


    private void launchImg2PdfA5(File[] files) throws Exception {
        int pos = files[0].getName().lastIndexOf('.');
        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
        File dest = new File(destStr);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, PageSize.A5);

        for (int i = 0; i < files.length; i++) {
            Image image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
            image = image.scaleToFit(PageSize.A5.getWidth(), PageSize.A5.getHeight());
            pdfDoc.addNewPage(PageSize.A5);
            image.setFixedPosition(i + 1, 0, 0);
            doc.add(image);
        }
        doc.close();
    }

    private void laumchCropMagik(File file, String crop, int i) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNameNoExt = file.getName().substring(0, pos);
        int lastTiret = fileNameNoExt.lastIndexOf('-');
        String chiffreStr = fileNameNoExt.substring(lastTiret + 1, fileNameNoExt.length());
        int chiffre = Integer.parseInt(chiffreStr);
        boolean add = chiffre < 10;

        String newFileName = fileNameNoExt.substring(0, lastTiret + 1);
        newFileName += (add ? "0" + chiffreStr : chiffreStr);

        String filename = file.getParentFile().getAbsolutePath() + "/rendu/" + newFileName + "-" + i + ".jpg";

        String args[] = {"magick", file.getAbsolutePath(), "-crop", crop, filename};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }

    public void go(String args[]) throws Exception {

        File folder = new File("/home/mchinchole/Téléchargements/kickoff");

        boolean pdf2Img = false;
        boolean imgToCrop = false;
        boolean img2pdf = false;

        if (pdf2Img) {
            File file = new File(folder, "KickOff97manuel.pdf");
            launchPdf2Img(file);
        }

        if (imgToCrop) {
            File files[] = folder.listFiles((File pathname) -> pathname.getName().endsWith(".jpg"));
            new File(folder, "rendu").mkdirs();
            Arrays.sort(files, Comparator.comparing(File::getName));
            for (File file : files) {
                laumchCropMagik(file, "1720x2468-10-10", 1);
                laumchCropMagik(file, "1720x2468+1720-5", 2);
            }
        }

        if (img2pdf) {
            File newFolder = new File(folder, "rendu");
            newFolder.mkdirs(); // au cas ou
            File files[] = newFolder.listFiles((File pathname) -> pathname.getName().endsWith(".jpg"));
            Arrays.sort(files, Comparator.comparing(File::getName));
            launchImg2PdfA5(files);
        }

    }
}