package com.entropay.ratestetrieval.servlet;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
@WebServlet(name = "BatchServlet")
public class BatchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Batch Processing Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Batch Processing</h1>");
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            long jobId = jobOperator.start("ratesProcessJob", new Properties());
            out.println("Job submitted: " + jobId);
            out.println();
            out.println();
        } catch (JobStartException | JobSecurityException ex) {
            Logger.getLogger(BatchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
