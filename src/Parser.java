import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

public class Parser {

    static Executor executor = new Executor() ;

    public static void main(String[] args) throws IOException, ParsingException, InvalidTypeException {
        run("Program 3.txt");

    }


    public static void run (String fileName) throws IOException, ParsingException, InvalidTypeException {
        File test = new File("Tests/"+fileName) ;
        BufferedReader br=new BufferedReader(new FileReader(test));
        String line ;
        while ((line = br.readLine())!= null ) {
            String lineSplit [] = line.split(" ");
           /* for (int i = 0 ; i< lineSplit.length ; i ++ ) {
                System.out.print(lineSplit[i]+" ");
            }
            System.out.println();*/
            Stack<String> recursion = new Stack<String>();
            for (int i = 0 ; i < lineSplit.length ; i ++ ) {
                recursion.push(lineSplit[i]) ;
            }
            functionRecursion(recursion);
           /* Hashtable<String,OSType> h = executor.getVar() ;
            for (Map.Entry<String,OSType> e : h.entrySet()){
                System.out.println(e.getKey()+": "+e.getValue().getData().toString());
            }*/
        }
    }

    public static void functionRecursion (Stack<String> functions ) throws ParsingException, IOException, InvalidTypeException {

        Stack<OSType> parameters = new Stack<OSType>() ;
        Stack<String> varName = new Stack<String>() ;

        while (functions.size()>0) {
            if (executor.isFunction(functions.peek())){
                 switch (functions.peek()){
                    case "input":
                        if (parameters.size()!=executor.inputP)
                            throw new ParsingException("input takes no parameters");
                        functions.pop();
                        functions.push(executor.input().getData().toString()) ; break;

                    case "print":
                        if (parameters.size() != executor.printP)
                            throw new ParsingException("print takes one parameter") ;
                        OSType output = parameters.pop() ;
                        varName.pop();
                        functions.pop();

                        if (functions.size()!=0)
                            throw new ParsingException("print is a void method");
                        executor.print(output);break;

                    case "writeFile" :
                        if (parameters.size() != executor.writeFileP)
                            throw new ParsingException("writeFile takes two parameters");
                        OSType file = parameters.pop() ;
                        OSType data = parameters.pop() ;
                        varName.pop();varName.pop();
                        functions.pop();

                        if (functions.size()!=0)
                            throw new ParsingException("writeFile is a void method");
                        executor.writeFile(file,data);break;

                    case "readFile" :
                        if(parameters.size() != executor.readFileP)
                            throw new ParsingException("readFile takes one parameter") ;
                        OSType fileName = parameters.pop();
                        varName.pop();
                        functions.pop();
                        functions.push(executor.readFile(fileName).getData().toString()) ;break;

                    case "assign":
                        if (parameters.size() != executor.assignP)
                            throw new ParsingException("assign takes two parameters") ;
                        OSType variable = parameters.pop() ;
                        OSType value = parameters.pop() ;
                        varName.pop();varName.pop();
                        functions.pop() ;
                        executor.assign(variable,value);break ;

                    case "add" :
                        if (parameters.size() != executor.addP)
                            throw new ParsingException("add takes two parameters") ;
                        OSType var = parameters.pop() ;
                        OSType toAdd = parameters.pop() ;
                        String v =  varName.pop(); varName.pop();

                        functions.pop() ;
                        functions.push(executor.add(new OSType(v),toAdd).getData().toString());break ;
                    default: throw new ParsingException("invalid function") ;
                }
            }
            else {
                if (executor.isVariable(functions.peek())) {
                    varName.push(functions.peek());
                    parameters.push(executor.variableValue(functions.pop())) ;
                }
                else {
                    varName.push("");
                    parameters.push(new OSType(functions.pop())) ;
                }
            }
        }
    }

    /*
    if the pop value is OSType
        add it to the parameters list
     else it is a function
         if the length of the parameter list is the same as the pop function
              pass the parameters to the function and then push the
     */
}
