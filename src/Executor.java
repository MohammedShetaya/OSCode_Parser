import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Executor {

    private Hashtable<String , OSType> var ;
    private Scanner scanner ;
    private final String functions [] = {"input" , "print" , "assign" , "readFile" , "writeFile" , "add"};
    public static final int inputP = 0 , printP = 1 , writeFileP = 2 , readFileP = 1, assignP = 2 , addP = 2 ;
    public Executor() {
        var = new Hashtable<String,OSType>() ;
        scanner = new Scanner(System.in);
    }

    public Hashtable<String, OSType> getVar() {
        return var;
    }

    public OSType variableValue (String variable) {
        return var.get(variable) ;
    }

    public boolean isVariable(String variable) {
        return var.containsKey(variable) ;
    }

    public boolean isFunction (String function) {
        for (String f : functions){
            if (f.equals(function))
                return true;
        }
        return false;
    }

    public OSType input() {
        return new OSType(scanner.next());
    }

    public void print (OSType output) {
        System.out.println(output.getData());
    }

    public void writeFile (OSType file,OSType data  ) throws IOException {
        File fileToWrite = new File("Data/"+file.getData()) ;
        fileToWrite.createNewFile() ;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite, true));
        writer.append("\n");
        writer.append(data.getData().toString()) ;
        writer.close();
    }
    public OSType readFile (OSType file ) throws IOException {
        File fileToRead = new File("Data/"+file.getData()) ;
        BufferedReader reader = new BufferedReader(new FileReader(fileToRead) );
        StringBuilder fileData = new StringBuilder();
        String line ;
        while ((line = reader.readLine())!=null){
            if(fileData.length()!=0) fileData.append("\n");
            fileData.append(line);
        }
        return new OSType(fileData.toString()) ;
    }

    public void assign (OSType variable , OSType value ) throws InvalidTypeException, ParsingException {
        if (var.containsKey(variable.getData()))
            throw new ParsingException("variable does not exist") ;
        if (var.containsKey(variable.getData()) && !value.equalType(var.get(variable.getData()))) {
            throw new InvalidTypeException("old and new value not equal type");
        }
        var.put(variable.getData().toString(),value) ;
    }

    public OSType add (OSType variable ,OSType toAdd ) throws InvalidTypeException, ParsingException {
         if (!var.containsKey(variable.getData().toString()))
            throw new ParsingException("variable does not exist") ;
        if (!var.get(variable.getData()).equalType(toAdd)) {
            throw new InvalidTypeException("Not equal type");
        }
        OSType newVal = var.get(variable.getData()).add(toAdd) ;
        var.put((String) variable.getData(),newVal);
        return newVal;

    }

}
