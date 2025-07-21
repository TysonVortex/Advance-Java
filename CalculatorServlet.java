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
            out.println("<title>Calculator Result</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; max-width: 500px; margin: 50px auto; padding: 20px; background-color: #f5f5f5; }");
            out.println(".result-container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center; }");
            out.println("h1 { color: #333; }");
            out.println(".calculation { font-size: 24px; margin: 20px 0; padding: 20px; background-color: #f0f0f0; border-radius: 5px; }");
            out.println(".result { font-size: 32px; font-weight: bold; color: #4CAF50; margin: 20px 0; }");
            out.println(".error { font-size: 24px; font-weight: bold; color: #f44336; margin: 20px 0; }");
            out.println(".back-btn { display: inline-block; padding: 12px 24px; background-color: #2196F3; color: white; text-decoration: none; border-radius: 5px; margin-top: 20px; }");
            out.println(".back-btn:hover { background-color: #1976D2; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='result-container'>");
            out.println("<h1>Calculator Result</h1>");
            
            if (!errorMessage.isEmpty()) {
                out.println("<div class='error'>" + errorMessage + "</div>");
            } else {
                out.println("<div class='calculation'>");
                out.println(num1 + " " + operationSymbol + " " + num2 + " =");
                out.println("</div>");
                out.println("<div class='result'>" + result + "</div>");
            }
            
            out.println("<a href='index.html' class='back-btn'>Calculate Again</a>");
            out.println("</div>");
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