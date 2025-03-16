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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
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

    //    A ne pas delete
    private void launchImg2Pdf(File[] files) throws Exception {
        int pos = files[0].getName().lastIndexOf('.');
        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
        File dest = new File(destStr);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Image image = new Image(ImageDataFactory.create(files[0].getAbsolutePath()));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(image.getImageWidth() / 4, image.getImageHeight() / 4));

        for (int i = 0; i < files.length; i++) {
            image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
            image = image.scaleToFit(image.getImageWidth() / 4, image.getImageHeight() / 4);
            pdfDoc.addNewPage(new PageSize(image.getImageWidth() / 4, image.getImageHeight() / 4));
            image.setFixedPosition(i + 1, 0, 0);
            doc.add(image);
        }
        doc.close();
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
            if (files[i].getName().contains("paysage")) {
                pdfDoc.addNewPage(PageSize.A5.rotate());
            } else {
                pdfDoc.addNewPage(PageSize.A5);
            }
            image.setFixedPosition(i + 1, 0, 0);

            doc.add(image);
        }
        doc.close();
    }

    private void launchImg2PdfBdBelge(File[] files) throws Exception {
        int pos = files[0].getName().lastIndexOf('.');
        String destStr = files[0].getParentFile().getAbsolutePath() + "/" + files[0].getName().substring(0, pos) + ".pdf";
        File dest = new File(destStr);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        PageSize BDBELGE = new PageSize(936, 1248);

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, BDBELGE);

        for (int i = 0; i < files.length; i++) {
            Image image = new Image(ImageDataFactory.create(files[i].getAbsolutePath()));
            if (files[i].getName().contains("paysage")) {
                pdfDoc.addNewPage(BDBELGE.rotate());
                image = image.scaleToFit(BDBELGE.getHeight(), BDBELGE.getWidth());
            } else {
                image = image.scaleToFit(BDBELGE.getWidth(), BDBELGE.getHeight());
                pdfDoc.addNewPage(BDBELGE);
            }
            image.setFixedPosition(i + 1, 0, 0);

            doc.add(image);
        }
        doc.close();
    }

    Pattern p = Pattern.compile("([a-zA-Z0-9 ]*)[-]*([0-9]+).+");

    private void laumchCropMagik(File file, String crop, int i) throws Exception {

        Matcher m = p.matcher(file.getName());
        if (!m.find()) {
            return;
        }
        String fileNameNoExt = m.group(1);
        String chiffreStr = m.group(2);
        int chiffre = Integer.parseInt(chiffreStr);
        boolean add = chiffre < 10;
        String newFileName = fileNameNoExt + "-" + (add ? "0" + chiffreStr : chiffreStr);

        String filename = file.getParentFile().getAbsolutePath() + "/rendu/" + newFileName + "-" + i + ".jpg";

        String args[] = {"magick", file.getAbsolutePath(), "-crop", crop, filename};
        Process p = Runtime.getRuntime().exec(args);

        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        for (String line : IOUtils.readLines(p.getInputStream(), "UTF-8")) {
            System.out.println(line);
        }
        p.waitFor();
    }

    private void laumchRotateMagik(File file) throws Exception {
        String args[] = {"magick", file.getAbsolutePath(), "-rotate", "90", file.getAbsolutePath()};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }

    private void launchConvertInm(File file, String ext) throws Exception {
        int pos = file.getName().lastIndexOf('.');
        String fileNoExt = file.getName().substring(0, pos);

        String args[] = {"magick", file.getAbsolutePath(), new File(file.getParent(), fileNoExt + ext).getAbsolutePath()};
        Process p = Runtime.getRuntime().exec(args);
        for (String line : IOUtils.readLines(p.getErrorStream(), "UTF-8")) {
            System.err.println(line);
        }
        p.waitFor();
    }

    private File[] getFiles(File folder, boolean inRendu, String ext) {
        File files[] = null;
        if (inRendu) {
            File newFolder = new File(folder, "rendu");
            newFolder.mkdirs(); // au cas ou
            files = newFolder.listFiles((File pathname) -> pathname.getName().endsWith(ext));
        } else {
            files = folder.listFiles((File pathname) -> pathname.getName().endsWith(ext));
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        return files;
    }

    public void go(String args[]) throws Exception {

        File folder = new File("E:\\code\\ltf-manuel\\05905 Livret Mafia");

        boolean pdf2Img = false;
        boolean imgsToCrop = false;
        boolean img2pdf = true;
        boolean rename = false;
        boolean rotate = false;
        boolean convert = false;

        if (pdf2Img) {
            File originalFolder = new File(folder, "originals");
            originalFolder.mkdirs();
            File[] files = getFiles(folder, false, ".pdf");
            for (File file : files) {
                launchPdf2Img(file);
                FileUtils.moveFile(file, new File(originalFolder, file.getName()));
            }
        }

        if (imgsToCrop) {
            new File(folder, "rendu").mkdirs();
            File[] files = getFiles(folder, false, ".jpg");
            for (File file : files) {
                laumchCropMagik(file, "1404x2126-10-10", 1);
                laumchCropMagik(file, "1404x2126+1404-5", 2);
            }
        }

        if (img2pdf) {
            File[] files = getFiles(folder, true, ".jpg");
            launchImg2PdfA5(files);
            //launchImg2Pdf(files);
            //launchImg2PdfBdBelge(files);
        }

        if (rename) {
            File files[] = getFiles(folder, false, ".jpeg");
            for (File file : files) {
                int pos = file.getName().lastIndexOf('.');
                String fileNoExt = file.getName().substring(0, pos);
                file.renameTo(new File(folder, fileNoExt + ".jpg"));
            }
        }

        if (rotate) {
            File files[] = getFiles(folder, false, ".jpg");
            for (File file : files) {
                laumchRotateMagik(file);
            }
        }

        if (convert) {
            File files[] = getFiles(folder, false, ".png");
            for (File file : files) {
                launchConvertInm(file, ".jpg");
            }
        }

    }
}
