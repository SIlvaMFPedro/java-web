package main.java.fdmc.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface HtmlReader {
    String readHtmlFail(String htmlFilePath) throws IOException;
}