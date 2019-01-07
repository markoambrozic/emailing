package com.kumuluz.ee.samples.microservices.simple;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.kumuluz.ee.logs.cdi.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;



@Path("/email")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class EmailingResource {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LogManager.getLogger(EmailingResource.class.getName());



    @GET
    public Response sendEmail() {
        JSONObject obj = new JSONObject();
        //send mail here from url params

        //TODO url params
        //TODO da je pro SMTP server

        obj.put("emailing", "fancy info about people");

        // Recipient's email ID needs to be mentioned.
        String to = "dmvoicestudios@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            obj.put("Message status ", "sent succesfully");
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            obj.put("Message status ", mex.toString());
        }

        return Response.ok(obj.toString()).build();
    }

    @POST
    @Path("/sendEmail")
    public Response sendEmail(String emailJSON) {
        //TODO actual sending of email
        LOG.trace("New mail sent!");

        return Response.status(Response.Status.OK).entity(emailJSON).build();
    }

}
