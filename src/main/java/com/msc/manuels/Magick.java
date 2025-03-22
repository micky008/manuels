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
        File result = new File(pdf.getParentFile(), fileNameNoExt + "-00%d.jpg");
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

        // PageSize newPageSize = new PageSize(image.getImageWidth() / 4, image.getImageHeight() / 4);
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
//
//    public void launchImg2PdfA5(File[] files) throws Exception {
//        int pos = files[0].getName().lastIndexOf('.');
//        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
//        File dest = new File(destStr);
//        if (!dest.exists()) {
//            dest.createNewFile();
//        }
//
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc, PageSize.A5);
//
//        for (int i = 0; i < files.length; i++) {
//            Image image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
//            image = image.scaleToFit(PageSize.A5.getWidth(), PageSize.A5.getHeight());
//            if (files[i].getName().contains("paysage")) {
//                pdfDoc.addNewPage(PageSize.A5.rotate());
//            } else {
//                pdfDoc.addNewPage(PageSize.A5);
//            }
//            image.setFixedPosition(i + 1, 0, 0);
//
//            doc.add(image);
//        }
//        doc.close();
//    }
//
//    public void launchImg2PdfBdBelge(File[] files) throws Exception {
//        int pos = files[0].getName().lastIndexOf('.');
//        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
//        File dest = new File(destStr);
//        if (!dest.exists()) {
//            dest.createNewFile();
//        }
//
        PageSize BDBELGE = new PageSize(936, 1248);
//
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc, BDBELGE);
//
//        for (int i = 0; i < files.length; i++) {
//            Image image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
//            if (files[i].getName().contains("paysage")) {
//                pdfDoc.addNewPage(BDBELGE.rotate());
//                image = image.scaleToFit(BDBELGE.getHeight(), BDBELGE.getWidth());
//            } else {
//                image = image.scaleToFit(BDBELGE.getWidth(), BDBELGE.getHeight());
//                pdfDoc.addNewPage(BDBELGE);
//            }
//            image.setFixedPosition(i + 1, 0, 0);
//
//            doc.add(image);
//        }
//        doc.close();
//    }

    public void laumchCropMagik(File file, String crop, int i) throws Exception {

        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        String filename = file.getParentFile().getAbsolutePath() + "/rendu/" + fileNoExt + "-" + i + ".jpg";

        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-crop", crop, filename};
        launchMagick(args);
    }

    public void laumchRotateMagik(File file) throws Exception {
        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-rotate", "-90", file.getAbsolutePath()};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }

    public void launchConvertInm(File file, String ext) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        File newFIle = new File(file.getParent(), "rendu/" + fileNoExt + ext);
        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), newFIle.getAbsolutePath()};
        launchMagick(args);
    }

    public void launchResize(File file, String taille) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        File newFIle = new File(file.getParent(), "rendu/" + fileNoExt + ".jpg");
        String args[] = {CONFIG.magickCommande, file.getAbsolutePath(), "-resize", taille, newFIle.getAbsolutePath()};
        launchMagick(args);
        File fileToOriginals = new File(file.getParent(), "originals/" + file.getName());
        FileUtils.moveFile(file, fileToOriginals);
        FileUtils.moveFile(newFIle, new File(file.getParent(), newFIle.getName()));
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
//
//    private File[] getFiles(File folder, boolean inRendu, String ext) {
//        File files[] = null;
//        if (inRendu) {
//            File newFolder = new File(folder, "rendu");
//            newFolder.mkdirs(); // au cas ou
//            files = newFolder.listFiles((File pathname) -> pathname.getName().endsWith(ext));
//        } else {
//            files = folder.listFiles((File pathname) -> pathname.getName().endsWith(ext));
//        }
//        Arrays.sort(files, Comparator.comparing(File::getName));
//        return files;
//    }
//
//    public void go(String args[]) throws Exception {
//
//        File folder = new File("/home/mchinchole/manuels/Lot de Manuels educatif par _El_Madkiller57_/ADI 2.0 Manuels/ADI 2.0 Manuel utilisation");
//
//        boolean pdf2Img = false;
//        boolean imgsToCrop = false;
//        boolean img2pdf = true;
//        boolean rename = false;
//        boolean rotate = false;
//        boolean convert = false;
//        boolean resize = false;
//
//        if (pdf2Img) {
//            File originalFolder = new File(folder, "originals");
//            originalFolder.mkdirs();
//            File[] files = getFiles(folder, false, ".pdf");
//            for (File file : files) {
//                launchPdf2Img(file);
//                FileUtils.moveFile(file, new File(originalFolder, file.getName()));
//            }
//        }
//
//        if (imgsToCrop) {
//            new File(folder, "rendu").mkdirs();
//            File[] files = getFiles(folder, false, ".jpg");
//            for (File file : files) {
//                BufferedImage img = ImageIO.read(file);
//                int h = img.getHeight();
//                int w = img.getWidth();
//
//                String crop1 = w / 2 + "x" + h + "-0-0";
//                String crop2 = w / 2 + "x" + h + "+" + w / 2 + "-0";
//
//                laumchCropMagik(file, crop1, 1);
//                laumchCropMagik(file, crop2, 2);
//            }
//        }
//
//        if (img2pdf) {
//            File[] files = getFiles(folder, true, ".jpg");
//            //launchImg2PdfA5(files);
//            launchImg2Pdf(files);
//            //launchImg2PdfBdBelge(files);
//        }
//
//        if (rename) {
//            File files[] = getFiles(folder, false, ".jpg");
//            int i = 0;
//            for (File file : files) {
//                String nb = i > 0 ? "" + i : "0" + i;
//                file.renameTo(new File(folder, nb + ".jpg"));
//                i++;
//            }
//        }
//
//        if (rotate) {
//            File files[] = getFiles(folder, false, ".jpg");
//            for (File file : files) {
//                laumchRotateMagik(file);
//            }
//        }
//
//        if (convert) {
//            new File(folder, "rendu").mkdirs();
//            File files[] = getFiles(folder, false, ".png");
//            for (File file : files) {
//                launchConvertInm(file, ".jpg");
//            }
//        }
//
//        if (resize) {
//            new File(folder, "originals").mkdirs();
//            new File(folder, "rendu").mkdirs();
//            File files[] = getFiles(folder, false, ".jpg");
//            for (File file : files) {
//                launchResize(file, "50%");
//                //launchResize(file, "1000x2450");
//            }
//        }
//
//    }
}
