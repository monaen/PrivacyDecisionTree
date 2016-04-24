package decisiontree;  

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import static java.nio.file.Files.find;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;  
  
/** 
 * Decision Tree for Prediction
 * 
 */  
public class TestDecisionTree {  
  
    /** 
     * Read Candidate Attributes
     * 
     * @return Candidate attributes set
     * @throws IOException 
     */  
    // Record the data read from file（redFileRecord[0][] is the candidate attributes）  
    String redFileRecord[][] = new String[100][];  
    int length = 0; // The number of record
    FileInputStream file1;  
    
    
    
    public ArrayList<String> readCandAttr(String filepath) throws IOException {  
        ArrayList<String> candAttr = new ArrayList<String>();  
  
        try {  
            file1 = new FileInputStream(filepath);  
            InputStreamReader isr = new InputStreamReader(file1);  
            BufferedReader bfr = new BufferedReader(isr);  
  
            String s = ""; // store the record read from file
            String sSplit[] = new String[1000]; // store the divided data
            while ((s = bfr.readLine()) != null){
                sSplit = s.toString().trim().split(",");  
                for (int j = 0; j < sSplit.length; j++){
                    candAttr.add(sSplit[j]);
                }
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return candAttr;
    }
  
    /** 
     * read the training tuple
     *  
     * @return Train the tuple set 
     * @throws IOException 
     */  
    public ArrayList<ArrayList<String>> readData(String filepath) throws IOException {  
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();  
        try {
            file1 = new FileInputStream(filepath);  
            InputStreamReader isr = new InputStreamReader(file1);  
            BufferedReader bfr = new BufferedReader(isr);  
              
            String s = bfr.readLine(); // Store the record read from file
            String sSplit[] = new String[1000]; // Store the divided data
            while ((s = bfr.readLine()) != null) {  
                sSplit = s.toString().trim().split(",");  
                ArrayList<String> sA = new ArrayList<String>();  
                for (int j = 0; j < sSplit.length; j++) {  
                    sA.add(sSplit[j]);  
                }  
                datas.add(sA);  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return datas;  
    }  
  
    /** 
     * print tree structure
     *  
     * @param root Current root that user want to print
     * 
     */  
    public void printTree(TreeNode root, int level) {
        System.out.println(root.getName());  
        ArrayList<String> rules = root.getRule();  
  
        ArrayList<TreeNode> children = root.getChild();  
        for (int i = 0; i < rules.size(); i++) {  
            for (int j = 0; j <= level; j++)  
                System.out.print("         ");  
            System.out.print(rules.get(i) + "--> ");  
            printTree(children.get(i), (level + 1));  
        }  
  
    }
    
    /** 
     * print tree structure
     *  
     * @param root Current root that user want to print
     * 
     */  
    public ArrayList<String> printTreeString(TreeNode root, int level) {
        
        ArrayList<String> information = new ArrayList<String>();
        
//        System.out.println(root.getName());
        information.add(root.getName() + "\n");
        
        ArrayList<String> rules = root.getRule();  
  
        ArrayList<TreeNode> children = root.getChild();  
        for (int i = 0; i < rules.size(); i++) {  
            for (int j = 0; j <= level; j++){
//                System.out.print("         ");
                information.add("         ");
            }

//            System.out.print(rules.get(i) + "--> ");
            information.add(rules.get(i) + "--> ");
            
            ArrayList<String> infotemp = printTreeString(children.get(i),(level + 1));
            for (int j = 0; j < infotemp.size(); j++){
                information.add(infotemp.get(j).toString());
            }
//            information.add(infotemp.get(0).toString());
            
        }  
        return information;
    }
    
    /** 
     * print tree structure
     *  
     * @param datas Current root that user want to print
     * 
     */  
    public Map<String,Map<String, Map<String, Integer>>> TransInfo(ArrayList<ArrayList<String>> Data, ArrayList<String> attrList){
//        List<List<Integer>> transInfo = null;
        Map<String, Integer> classes = classOfDatas(Data); // Get the classes and their counts of specific data set.
        Map<String,Map<String, Map<String, Integer>>> transInfo = 
                new HashMap<String,Map<String, Map<String,Integer>>>();
        Map<String,Map<String, Integer>> transInfoSingle = 
                new HashMap<String,Map<String, Integer>>();
        
        AttributeTable attrTable = new AttributeTable(Data, attrList);
        
        
        
//        transInfoSingle = attrTable.getIndexTable(0);
        
        
        for(int i = 0; i < attrList.size(); i++){
            transInfoSingle = attrTable.getIndexTable(i);
            transInfo.put(attrList.get(i), transInfoSingle);
        }
        
        
        
        return transInfo;
    }
    
    
    /**
     *  
     * Get the class and count of the class of the specified data set.
     * 
     * @param datas  the specified data set.
     * 
     * @return  Map of the class and the count of this class.
     */  
    public Map<String, Integer> classOfDatas(ArrayList<ArrayList<String>> datas) {  
        Map<String, Integer> classes = new HashMap<String, Integer>();  
        String c = "";  
        ArrayList<String> tuple = null;  
        for (int i = 0; i < datas.size(); i++) {  
            tuple = datas.get(i);  
            c = tuple.get(tuple.size() - 1);  
  
            if (classes.containsKey(c)) { // If the attribute has been exist, the corresponding 
            							  //attribute +1 or generate the attribute.
                classes.put(c, classes.get(c) + 1);  
            } else {  
                classes.put(c, 1);  
            }  
        }  
        return classes;  
    }
    
    
    /** 
     * main function
     *  
     * @param args 
     */  
    public static void main(String[] args) {  
        TestDecisionTree testdetree = new TestDecisionTree();
        ArrayList<String> candAttr = null; // Store the candidate attributes
        ArrayList<ArrayList<String>> datas = null;
        ArrayList<ArrayList<String>> dataA = null;
        ArrayList<ArrayList<String>> dataB = null;
        ArrayList<String> Information = new ArrayList<String>();
        
        Map<String,Map<String, Map<String, Integer>>> transInfo = null;

//        String currentDir = System.getProperty("user.dir");
//        System.out.println("Current dir using System:" +currentDir);
        try {
            candAttr = testdetree.readCandAttr("./dataset/wisconsin.csv");  
            datas = testdetree.readData("./dataset/adult.csv");  
            dataA = testdetree.readData("./dataset/wisconsin_Partition1.csv");
            dataB = testdetree.readData("./dataset/wisconsin_Partition2.csv");
        }catch (IOException e){
            e.printStackTrace();
        }
//        DecisionTree tree = new DecisionTree();
//        TreeNode root = tree.buildTree(datas, candAttr);
//        testdetree.printTree(root, 0);

        
        
        
//        
        DecisionTree combinedtree = new DecisionTree();        
        TreeNode combinedroot = combinedtree.CombinedTreeConstruction(dataB, dataA ,candAttr);
        ArrayList<String> stringinfo = combinedtree.getInfo();
//        
//        
        System.out.println("Decision Tree Constructed Already: \n");
        testdetree.printTree(combinedroot, 0);//print tree
//        
        
    }  
  
}  