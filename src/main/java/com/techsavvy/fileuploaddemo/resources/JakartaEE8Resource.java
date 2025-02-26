package com.techsavvy.fileuploaddemo.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author 
 */
@Path("rest")
public class JakartaEE8Resource {
    
    @GET
    @Produces("application/json")
    public Response ping() {
        return Response
                .ok("{id:1,username:shakil}")
                .build();
    }
   
//    @POST
//    @Path("/image")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String uploadImage(
//            @FormDataParam("file") InputStream uploadedInputStream,
//            @FormDataParam("file") FormDataContentDisposition fileDetails) {
//
//        System.out.println(fileDetails.getFileName());
//
//        String uploadedFileLocation = "/Users/temp/" + fileDetails.getFileName();
//
//        // save it
//        writeToFile(uploadedInputStream, uploadedFileLocation);
//
//        String output = "File uploaded to : " + uploadedFileLocation;
////
////        ResponseBean responseBean = new ResponseBean();
////
////        responseBean.setCode(StatusConstants.SUCCESS_CODE);
////        responseBean.setMessage(fileDetails.getFileName());
////        responseBean.setResult(null);
//        return output;
//    }

// save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
