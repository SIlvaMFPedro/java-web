package main.java.fdmc.web.servlets;

import fdmc.domain.entities.Cat;
import fdmc.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@WebServlet("/cats/all") configured in web.xml manually
public class CatAllServlet extends HttpServlet {

    private static final String ALL_CATS_HTML_FILE_PATH = "";

    private final HtmlReader htmlReader;

    @Inject
    public CatAllServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String[] htmlFileContent = {this.htmlReader.readHtmlFail(ALL_CATS_HTML_FILE_PATH)};
        if (req.getSession().getAttribute("cats") == null) {
            htmlFileContent[0] = htmlFileContent[0]
                    .replace("{{allCats}}", "There are no cats. <a href=\"/cats/create\">Create some.</a>");
        } else {
            StringBuilder sb = new StringBuilder();

            ((Map<String, Cat>) req.getSession().getAttribute("cats"))
                    .values()
                    .forEach(c -> {
                        sb
                                .append(String.format("<a href=\"/cats/profile?catName=%s\">%s</a><br/>"
                                        , c.getName(), c.getName()));

                        htmlFileContent[0] = htmlFileContent[0].replace("{{allCats}}", sb.toString().trim());
                    });
        }

        resp.getWriter().println(htmlFileContent[0]);
    }
}