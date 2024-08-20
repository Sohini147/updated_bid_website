package com.buysellplatform.controller;

import com.buysellplatform.dao.ProductDAO;
import com.buysellplatform.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the ProductDAO object
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve the list of products
            List<Product> products = productDAO.getAllProducts();

            // Log the number of products retrieved
            System.out.println("Number of products retrieved: " + products.size());

            // Check if products list is null
            if (products == null) {
                System.out.println("Products list is null");
                // Redirect to an error page or handle appropriately
                response.sendRedirect("error.jsp?message=Unable to retrieve products");
                return;
            }

            // Set the products list as a request attribute and forward to JSP
            request.setAttribute("products", products);
            request.getRequestDispatcher("productList.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=An error occurred while retrieving the product list");
        }
    }
}
