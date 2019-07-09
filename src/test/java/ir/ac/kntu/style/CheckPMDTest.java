/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.ac.kntu.style;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.cache.AnalysisCache;
import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.CPDConfiguration;
import net.sourceforge.pmd.cpd.LanguageFactory;
import net.sourceforge.pmd.cpd.Match;
import net.sourceforge.pmd.lang.Language;
import org.junit.Assert;
import org.junit.Test;

/**
 * 4o points
 * @author hossein
 */
public class CheckPMDTest {
    static{
        System.err.println("$$$GRADER$$$ | { type:\"MSG\" , key:\"TOTAL\" , value:80, priority:1  }  | $$$GRADER$$$");
    
    }

    /**
     *
     */
    @Test
    public void testCPD() {
        CPDConfiguration cpdConfiguration = new CPDConfiguration();
        cpdConfiguration.setMinimumTileSize(50);
        cpdConfiguration.setFailOnViolation(true);
        cpdConfiguration.setLanguage(LanguageFactory.createLanguage("java"));
        CPD copyPasteDetector = new CPD(cpdConfiguration);

        File ROOT = new File("src/main/");
        System.err.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");

        List<File> files = listFiles(ROOT, "java");
        System.err.println("Found " + files.size() + " Java source files.");

        try {
            copyPasteDetector.add(files);

        } catch (IOException e) {
            e.printStackTrace();
        }

        copyPasteDetector.go();
        Iterator<Match> matches = copyPasteDetector.getMatches();
        Assert.assertFalse(matches.hasNext());

        System.err.println("$$$GRADER$$$ | { type:\"SCORE\" , amount:20 , reason:\"CPD. You have duplicate code.\" } | $$$GRADER$$$");
    }

    /**
     *
     */
    @Test
    public void testPMD() {

        File ROOT = new File("src/main/");
        System.err.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");


        List<File> files = listFiles(ROOT, "java");
        System.err.println("Found " + files.size() + " Java source files.");

        PMDConfiguration pmdConfiguration = new PMDConfiguration();
        pmdConfiguration.setRuleSets("category/java/bestpractices.xml/DefaultLabelNotLastInSwitchStmt,"
                + "category/java/design.xml/ExcessiveMethodLength,"
                + "category/java/errorprone.xml/UseEqualsToCompareStrings");


        String collect = files.stream()
                .map(f -> f.getPath())
                .collect(Collectors.joining(","));
        pmdConfiguration.setInputPaths(collect);
        pmdConfiguration.setReportFormat("text");
        int violations = PMD.doPMD(pmdConfiguration);
        Assert.assertTrue("PMD VIOLATION", violations == 0);
        System.err.println("$$$GRADER$$$ | { type:\"SCORE\" , amount:20 , reason:\"PMD.\" } | $$$GRADER$$$");
    }

    
    private static List<File> listFiles(File folder, String extension) {
        List<File> files = new ArrayList<>();
        if (folder.canRead()) {
            if (folder.isDirectory()) {
                for (File f : folder.listFiles()) {
                    files.addAll(listFiles(f, extension));
                }
            } else if (folder.toString().endsWith("." + extension)) {
                files.add(folder);
            }
        }
        return files;
    }
}