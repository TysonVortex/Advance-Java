package com.calculator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculatorServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Get form parameters
            String num1Str = request.getParameter("num1");
            String num2Str = request.getParameter("num2");
            String operation = request.getParameter("operation");
            
            // Convert strings to double
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            
            double result = 0;
            String operationSymbol = "";
            String errorMessage = "";
            
            // Perform calculation based on operation
            switch(operation) {
                case "add":
                    result = num1 + num2;
                    operationSymbol = "+";
                    break;
                case "subtract":
                    result = num1 - num2;
                    operationSymbol = "-";
                    break;
                case "multiply":
                    result = num1 * num2;
                    operationSymbol = "×";
                    break;
                case "divide":
                    if (num2 != 0) {
                        result = num1 / num2;
                        operationSymbol = "÷";
                    } else {
                        errorMessage = "Error: Division by zero is not allowed!";
                    }
                    break;
                default:
                    errorMessage = "Error: Invalid operation selected!";
            }
            
            // Generate HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Result</h1>");
            
            if (!errorMessage.isEmpty()) {
                out.println("<p>" + errorMessage + "</p>");
            } else {
                out.println("<p>" + num1 + " " + operationSymbol + " " + num2 + " = " + result + "</p>");
            }
            
            out.println("<a href='index.html'>Calculate Again</a>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h1>Error</h1>");
            out.println("<p>Please enter valid numbers!</p>");
            out.println("<a href='index.html'>Go Back</a>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect GET requests to the form
        response.sendRedirect("index.html");
    }
}