package com.earasoft.orientdb.learning1

import java.nio.charset.Charset
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.List;

import org.apache.commons.io.FileUtils
import org.apache.commons.io.LineIterator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.earasoft.orientdb.learning1.MetaClassesBase

/**
 * General Utilities Methods
 * @author riverema
 *
 */
class GeneralUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class)
    
    static{
        MetaClassesBase.load()
    }
    
    public static List<String> toStringList(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
        } catch (final RuntimeException ex) {
            // Ignore any exceptions.
        }
        pw.flush();
        final List<String> lines = new ArrayList<>();
        final LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (final IOException ex) {
            if (ex instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            lines.add(ex.toString());
        } finally {
            reader.close()
            //Closer.closeSilently(reader);
        }
        return lines;
    }
    
    static String uniqueKeyGen1(){
        println new Date().hashCode()
        
        
    }
    
    static main(args) {
        
        uniqueKeyGen1()
        return
        
        
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        final Date d = new Date()
        Date date = d
        try {
            date = dateFormat.parse(dateFormat.format(d))
        } catch (ParseException ex) {
            System.out.println(ex.getMessage())
        }
        
        println date.getTime()
        println System.currentTimeMillis()
        
        
        println getCurrentTimeString()
        
        println checkMapRequiredKeys(["k":""],['k','g'])
    }
    
    /**
     * Generic Try Catch using Closures
     * @param code
     * @param exception
     */
    static void tryExceptionCatch(Closure code, Closure exception){
        try{
            code()
        }catch(Exception e){
            exception(e)
        }
    }
    
    /**
     * Used to check required keys for a map
     * @param map
     * @param keys
     * @return
     */
    public static Map checkMapRequiredKeys(Object input, List keys, String extraInfo = ''){
        if(!(input instanceof Map))
            throw new Exception("The input object is not a [Map] type it is a [" + input.class.getSimpleName()+'] type')
        
        Map map = (Map) input
        
        List missingKeys = []
        
        for (key in keys){
            if(!map.containsKey(key))
                missingKeys << key
        }
        
        if(missingKeys)
            throw new Exception(extraInfo + " - Map is missing keys:" + missingKeys)
        
        return map
    }
    
    /**
     * Used to get current time as formatted string
     * @return Formatted String
     */
    public static String getCurrentTimeString(Long mills = System.currentTimeMillis(), String format = "yyyy-MM-dd HH:mm:ss"){
        DateFormat dateFormat = new SimpleDateFormat(format)
        return dateFormat.format(mills).toString()
    }
    
    public static String getCurrentTimeString2(String format = "yyyy-MM-dd HH:mm:ss",Long mills = System.currentTimeMillis()){
        DateFormat dateFormat = new SimpleDateFormat(format)
        return dateFormat.format(mills).toString()
    }
    
    /**
     * Used to read a CSV file
     * @param inputFile  Accepts File and InputStream Types
     * @param processLine
     */
    static void readCSVWithHeaderHelper(Object inputFile, Closure processLine){
        BufferedReader br
        
        if(inputFile instanceof File){
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputFile), Charset.forName("UTF-8")));
        }else if(inputFile instanceof InputStream){
            br = new BufferedReader(
                    new InputStreamReader(inputFile, Charset.forName("UTF-8")));
        }else
            throw new RuntimeException("Input Type is not a File or a InputStream")
        
        
        boolean firstLine = true
        
        String line = null;
        def header = []
        while ((line = br.readLine()) != null) {
            //System.out.println(line.split("\t"));
            if(firstLine){
                header = line.split("\t")
                firstLine = false
            }else{
                def parts = line.split("\t")
                def props = [header, parts].transpose().collectEntries()
                processLine(props)
            }
        }
        br.close();
    }
    
    /**
     * Helper function used to iterate lines of a file 
     * @param inputFile
     * @param processLineClosure
     */
    static void lineIteratorHelper(File inputFile, Closure processLineClosure, Closure errorClosure=null){
        try{
            LineIterator it = FileUtils.lineIterator(inputFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    processLineClosure(line)
                }
            } finally {
                it.close();
            }
        }catch(Exception e){
            if(errorClosure!=null){
                errorClosure(e)
            }else{
                logger.error("Error", e)
            }
        }
    }
    
    /**
     * Used to process files in a Directory
     * @param strDirectory
     * @throws IOException
     */
    public static void processFilesInDirectory(String strDirectory,
            Closure filter=null,
            Closure fileAction=null,
            Closure output=null) throws IOException{
        Queue<String> q = new LinkedList<String>();
        q.add(strDirectory.toFile().toString());
        while(!q.isEmpty()){
            try{
                File dir = (q.poll()).toFile();
                def listDir = dir.listFiles();
                
                listDir.each{ filedir ->
                    if (filedir.isDirectory() && (!FileUtils.isSymlink(filedir))) {
                        //println "Dir:\t" + filedir.getAbsolutePath()
                        q.add(filedir.getAbsolutePath())
                    }
                    
                    if (filedir.isFile()) {
                        //println "File:\t" + filedir.getAbsolutePath()
                        // this.fileDescSet.add(filedir.getAbsolutePath());
                        if(fileAction!=null && output!=null){
                            try{
                                boolean execute
                                
                                if(filter==null){
                                    execute = true
                                }else{
                                    if(filter(filedir) == true)
                                        execute = true
                                    else
                                        execute = false
                                }
                                
                                if(execute)
                                    fileAction(filedir, output)
                            }catch(Exception e){
                                logger.error("File Error:" + filedir, e)
                            }
                        }
                    }
                }
            }catch(Exception e){
                logger.error("Error", e)
            }
        }
    }
    
    /**
     * Helper Function to get currentTimeMillis in seconds
     */
    static long getSeconds(){
        return System.currentTimeMillis() / 1000L
    }
    
    /**
     * Helper Function to get currentTimeMillis in seconds
     * @return currentTimeMillis
     */
    static long getMillisSeconds(){
        return System.currentTimeMillis()
    }
    
    /**
     * Helper Function to format a number
     * @param input
     * @return Formatted Number
     */
    static String formatNumber(Number input, String format = '%1$,.5f'){
        return String.format(format, input)
    }
    
    /**
     * Helper function to format a number to Locale
     * @param input
     * @return
     */
    static String formatNumberWithThousand(Number input, Locale locale = Locale.US){
        return NumberFormat.getNumberInstance(locale).format(input)
    }
    
    /**
     * Checks to see if a specific port is available.
     * http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
     * @param port the port to check for availability
     */
    public static boolean checkAvailablePort(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }
        
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }
            
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }
        
        return false;
    }
}
