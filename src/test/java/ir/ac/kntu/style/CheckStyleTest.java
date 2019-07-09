package ir.ac.kntu.style;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.InputSource;

/**
 * 40 points
 * @author mhrimaz
 */
public class CheckStyleTest {
    
    /**
     *
     */
    @Test
    public void testCheckStyleIndentation() {

        /*
         * Files
         */
        File ROOT = new File("src/main/");
        System.out.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");

        List<File> files = new ArrayList<>();
        listFiles(files, ROOT, "java");
        System.out.println("Found " + files.size() + " Java source files.");

        /*
         * Listener
         */
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(sos, AutomaticBean.OutputStreamOptions.NONE);

        /*
         * Configuration
         */
        File CONF = new File("src/test/java/ir/ac/kntu/style/config.xml");
        InputSource inputSource = null;
        try {
            inputSource = new InputSource(new FileInputStream(CONF));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Configuration configuration = null;
        try {
            configuration = ConfigurationLoader.loadConfiguration(inputSource,
                    new PropertiesExpander(System.getProperties()),
                    ConfigurationLoader.IgnoredModulesOptions.OMIT);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
         * Create checker
         */
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        try {
            checker.configure(configuration);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        checker.addListener(listener);

        /*
         * Process
         */
        int errors = 0;
        try {
            errors = checker.process(files);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Found " + errors + " check style errors.");
        System.out.println(sos.toString());
        assertTrue(errors + " check style errors found. " + sos.toString(), errors == 0);

        /*
         * Clean up
         */
        checker.destroy();
        System.err.println("$$$GRADER$$$ | { type:\"SCORE\" , amount:20 , reason:\"Indentation.\" } | $$$GRADER$$$");
    }

    /**
     *
     */
    @Test
    public void testCheckStyleNaming() {

        /*
         * Files
         */
        File ROOT = new File("src/main/");
        System.out.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");

        List<File> files = new ArrayList<>();
        listFiles(files, ROOT, "java");
        System.out.println("Found " + files.size() + " Java source files.");

        /*
         * Listener
         */
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(sos, AutomaticBean.OutputStreamOptions.NONE);

        /*
         * Configuration
         */
        File CONF = new File("src/test/java/ir/ac/kntu/style/naming.xml");
        InputSource inputSource = null;
        try {
            inputSource = new InputSource(new FileInputStream(CONF));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Configuration configuration = null;
        try {
            configuration = ConfigurationLoader.loadConfiguration(inputSource,
                    new PropertiesExpander(System.getProperties()),
                    ConfigurationLoader.IgnoredModulesOptions.OMIT);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
         * Create checker
         */
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        try {
            checker.configure(configuration);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        checker.addListener(listener);

        /*
         * Process
         */
        int errors = 0;
        try {
            errors = checker.process(files);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Found " + errors + " check style errors.");
        System.out.println(sos.toString());
        assertTrue(errors + " check style errors found. " + sos.toString(), errors == 0);

        /*
         * Clean up
         */
        checker.destroy();
        System.err.println("$$$GRADER$$$ | { type:\"SCORE\" , amount:20 , reason:\"Naming.\" } | $$$GRADER$$$" );
    }

    private static void listFiles(List<File> files, File folder, String extension) {
        if (folder.canRead()) {
            if (folder.isDirectory()) {
                for (File f : folder.listFiles()) {
                    listFiles(files, f, extension);
                }
            } else if (folder.toString().endsWith("." + extension)) {
                files.add(folder);
            }
        }
    }
}
