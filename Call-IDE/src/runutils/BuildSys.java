package runutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Configures build files, make executions, logs the compiler outputs.
 * @author Abdullah Talayhan
 */
public class BuildSys {
    
    /**
     * Description: Editing the xml file for compiling projects.
     * @param init path to the xml file
     * @param build path to the build directory
     * @param src  path to the source directory
     */
    public static void setPropsForCompile(String init ,String build, String src) {
        try {
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            //Node project = doc.getFirstChild();
            Node prop1 = doc.getElementsByTagName("property").item(0);
            Node prop2 = doc.getElementsByTagName("property").item(1);

            NamedNodeMap srcAttr = prop1.getAttributes();
            Node srcName = srcAttr.getNamedItem("location");
            srcName.setTextContent(src);
            
            NamedNodeMap buildAttr = prop2.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            buildName.setTextContent(build);
            
            
            updateBuildFile( filepath, doc);
            
        } catch (Exception exc) {}
    }
    /**
     * Description: Editing the xml file for compiling files.
     * @param init path to the xml file
     * @param sourceFile path to the source to be compiled
     */
    public static void setPropsForCompileFile(String init, String sourceFile) {
        try {
            File file = new File(sourceFile);
            
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            //Node project = doc.getFirstChild();
            Node prop1 = doc.getElementsByTagName("property").item(0);
            Node prop2 = doc.getElementsByTagName("property").item(1);
            Node prop3 = doc.getElementsByTagName("property").item(2);
            
            NamedNodeMap srcAttr = prop1.getAttributes();
            Node srcName = srcAttr.getNamedItem("location");
            srcName.setTextContent( file.getParent());
            
            NamedNodeMap buildAttr = prop2.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            buildName.setTextContent( file.getParent());
            
            NamedNodeMap fileAttr = prop3.getAttributes();
            Node fileName = fileAttr.getNamedItem("value");
            fileName.setTextContent( file.getName());
            
            updateBuildFile( filepath, doc);
            
        } catch (Exception ex) {}
    }
    
    /**
     * Description: Editing the xml file for compiling files with dependencies.
     * @param init path to the xml file
     * @param build path to the build folder
     * @param src path to the source folder
     * @param dependencies list of dependencies
     */
    public static void setPropsForCompileWithDepend(String init ,String build, String src, ArrayList<File> dependencies) {
        try {
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            //Node project = doc.getFirstChild();
            Node prop1 = doc.getElementsByTagName("property").item(0);
            Node prop2 = doc.getElementsByTagName("property").item(1);

            NamedNodeMap srcAttr = prop1.getAttributes();
            Node srcName = srcAttr.getNamedItem("location");
            srcName.setTextContent(src);
            
            NamedNodeMap buildAttr = prop2.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            buildName.setTextContent(build);
            
            String allDeps = "";
            
            for(int i = 0; i < dependencies.size(); i++) {
                allDeps = allDeps + dependencies.get(i).getAbsolutePath() + ";";
            }
            
            //allDeps.substring(allDeps.length()-1);

            Node deps = doc.getElementsByTagName("pathelement").item(0);
            
            NamedNodeMap depsAttr = deps.getAttributes();
            Node depNames = depsAttr.getNamedItem("path");
            depNames.setTextContent(allDeps);

            
            updateBuildFile( filepath, doc);
            
        } catch (Exception exc) {}
    }
    
    /**
     * Description: Editing the xml file for building jar files.
     * @param init path to the xml file
     * @param buildDir path to the build directory
     * @param distDir path to the dist directory
     * @param mainClassWithPackage name of the main class
     * @param jarName name of the jar to be created
     */
    public static void setPropsForJar(String init, String buildDir, String distDir, String mainClassWithPackage, String jarName) {
        
        try {
            //String filepath = "/Users/ATTJ/Desktop/xmlPlay/build.xml";
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            
            // get property, location build
            Node prop1 = doc.getElementsByTagName("property").item(0);
            // get property, location dist
            Node prop2 = doc.getElementsByTagName("property").item(1);
            Node prop3 = doc.getElementsByTagName("property").item(2);
            
            // get target, jar
            Node jarTarget = doc.getElementsByTagName("attribute").item(0);
            // get main class attribute
            //Node mainClass = jarTarget.getFirstChild();
            Node mainClass = jarTarget;
            
            NamedNodeMap buildAttr = prop1.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            buildName.setTextContent(buildDir);
            
            NamedNodeMap distAttr = prop2.getAttributes();
            Node distName = distAttr.getNamedItem("location");
            distName.setTextContent(distDir);
            
            NamedNodeMap mainAttr = mainClass.getAttributes();
            Node mainName = mainAttr.getNamedItem("value");
            mainName.setTextContent(mainClassWithPackage);
            
            NamedNodeMap jarAttr = prop3.getAttributes();
            Node jarNameNode = jarAttr.getNamedItem("value");
            jarNameNode.setTextContent(jarName);
            
            updateBuildFile( filepath, doc);
            
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            pce.printStackTrace();
        }
    }
    
    /**
     * Description: Editing the xml file for building javadoc files.
     * @param init path to the xml file
     * @param srcDir path to the source directory
     * @param docsDir path to the docs directory
     */
    public static void setPropsForJavaDoc(String init, String srcDir, String docsDir) {
        
        try {
            //String filepath = "/Users/ATTJ/Desktop/xmlPlay/build.xml";
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get property, location src
            Node prop1 = doc.getElementsByTagName("property").item(0);
            // get property, location docs
            Node prop2 = doc.getElementsByTagName("property").item(1);
            
            NamedNodeMap buildAttr = prop1.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            buildName.setTextContent(srcDir);
            
            NamedNodeMap distAttr = prop2.getAttributes();
            Node distName = distAttr.getNamedItem("location");
            distName.setTextContent(docsDir);
            
            updateBuildFile( filepath, doc);
            
        } catch (Exception e) {}
    }
    
    /**
     * Description: Updates the edited xml file
     * @param filepath path of the xml file
     * @param doc edited Document objects
     */
    public static void updateBuildFile(String filepath, Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
        } catch (Exception exc) {} 
        
    }
    
    /**
     * Description: compile method that maintains the i/o streams after the task is done.
     * @param file path of the build xml to be executed
     * @param out current output stream
     * @param err current input stream
     */
    public static void compile( String file, PrintStream out, PrintStream err) {
        
        // File buildFile = new File("build.xml");
        File buildFile = new File(file);
        Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());     
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        
        p.addBuildListener(consoleLogger);
        
        try {
            p.fireBuildStarted();
            p.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            p.addReference("ant.projectHelper", helper);
            helper.parse(p, buildFile);
            p.executeTarget(p.getDefaultTarget());
            p.fireBuildFinished(null);
            System.setErr(err);
            System.setOut(out);
            
            
        } catch (Exception e) {}
    }
    
    /**
     * Description: compile method that does not maintains the i/o streams after the task is done.
     * This is an alternative for the current compile method for future uses.
     * @param file path of the build xml to be executed
     */
    public static void altCompile( String file) {
        
        // File buildFile = new File("build.xml");
        File buildFile = new File(file);
        Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());     
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        
        try {
            p.fireBuildStarted();
            p.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            p.addReference("ant.projectHelper", helper);
            helper.parse(p, buildFile);
            p.executeTarget(p.getDefaultTarget());
            p.fireBuildFinished(null);
          
        } catch (Exception e) {}
    }
    
}
