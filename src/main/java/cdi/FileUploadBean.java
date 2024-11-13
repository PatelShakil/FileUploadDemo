/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package cdi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author M.SHAKIL PATEL
 */
@Named(value = "fileUploadBean")
@RequestScoped
public class FileUploadBean {

    private String uploadedFilePath;

    public FileUploadBean() {
    }

    public String getUploadedFilePath() {
        return uploadedFilePath;
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("file Arrived");
        UploadedFile uploadedFile = event.getFile();

        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload failed: File is null", null));
            return;
        }

        try {
            String resourcesFolderPath = "D:\\ICT\\JAVA\\Projects\\FileUploadDemo\\src\\main\\java\\com\\techsavvy\\fileuploaddemo\\resources";
            System.out.println("Resource path: " + resourcesFolderPath);

            String sanitizedFileName = uploadedFile.getFileName().replaceAll("[<>:\"/\\\\|?*]", "_");
            File targetFile = new File(resourcesFolderPath, sanitizedFileName);

            if (!targetFile.exists()) {
                if (!targetFile.createNewFile()) {
                    throw new IOException("File could not be created");
                }
            }

            try (InputStream input = uploadedFile.getInputStream(); FileOutputStream output = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }

            // Store the uploaded file path for display
            uploadedFilePath = "/uploads/" + sanitizedFileName;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", null));
            System.out.println(targetFile.getAbsolutePath());

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload failed: " + e.getMessage(), null));
        }
    }

}
