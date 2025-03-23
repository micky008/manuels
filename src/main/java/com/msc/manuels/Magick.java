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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Michael
 */
public class Magick {

    private final Config CONFIG;

    public Magick(Config config) {
        this.CONFIG = config;
    }

    public void launchPdf2Img(File pdf, String density) throws Exception {
        //Attention tres lent
        int pos = pdf.getName().lastIndexOf('.');
        String fileNameNoExt = pdf.getName().substring(0, pos);
        File result = new File(pdf.getParentFile(), fileNameNoExt + "-%d.jpg");
        String args[] = {CONFIG.magickCommande, "-density", density, pdf.getAbsolutePath(), result.getAbsolutePath()};
        launchMagick(args);        
    }

    //    A ne pas delete
    public void launchImg2Pdf(File[] files, PageSize pg) throws Exception {
        int pos = files[0].getName().lastIndexOf('.');
        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
        File dest = new File(destStr);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Image image = null;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, pg);

        for (int i = 0; i < files.length; i++) {
            image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
            image = image.scaleToFit(pg.getWidth(), pg.getHeight());
            if (files[i].getName().contains("paysage")) {
                pdfDoc.addNewPage(pg.rotate());
            } else {
                pdfDoc.addNewPage(pg);
            }
            image.setFixedPosition(i + 1, 0, 0);
            doc.add(image);
        }
        doc.close();
    }

    public void laumchCropMagik(File file, String crop, int i) throws Exception {

        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        String filename = file.getParentFile().getAbsolutePath() + "/crop/" + fileNoExt + "-" + i + ".jpg";

        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-crop", crop, filename};
        launchMagick(args);
    }

    public void laumchRotateMagik(File file, String rotate) throws Exception {

        String filename = file.getParentFile().getAbsolutePath() + "/rotate/" + file.getName();

        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-rotate", rotate, filename};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }

    public void launchConvertInm(File file, String ext) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        File newFIle = new File(file.getParent(), "convert/" + fileNoExt + ext);
        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), newFIle.getAbsolutePath()};
        launchMagick(args);
    }

    public void launchResize(File file, String taille) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        File newFIle = new File(file.getParent(), "resize/" + fileNoExt + ".jpg");
        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-resize", taille, newFIle.getAbsolutePath()};
        launchMagick(args);

    }

    public void launchMagick(String args[]) {
        try {
            Process p = Runtime.getRuntime().exec(args);
            for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
                System.err.println(line);
            }
            for (String line : IOUtils.readLines(p.getInputStream(), "UTF-8")) {
                System.out.println(line);
            }
            p.waitFor();
        } catch (Exception ex) {
            Logger.getLogger(Magick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
