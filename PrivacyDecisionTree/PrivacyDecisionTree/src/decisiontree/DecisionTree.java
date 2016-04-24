/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;  
  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.HashSet;
import java.util.Iterator;  
import java.util.List;
import java.util.Map;  
import java.util.Set;
  
/** 
 * DecisionTree Construction Class
 *  
 */  
public class DecisionTree {  
    private Integer attrSelMode; // Best Partition Attribute Model Choice: 
    							 //     1: for Information Gain measurement
    							 //     2: for Information Gain-Ratio measurement(haven't implemented)
    private static final ArrayList<String> Infomation = new ArrayList<String>();
    
    public ArrayList<String> getInfo(){
        return Infomation;
    }
    
    
    public DecisionTree() {  
        this.attrSelMode = Integer.valueOf(1);  
    }  
  
    public DecisionTree(int attrSelMode) {  
        this.attrSelMode = Integer.valueOf(attrSelMode);  
    }  
  
    public void setAttrSelMode(Integer attrSelMode) {  
        this.attrSelMode = attrSelMode;  
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
     * 
     *  Get the name of class whose count number is the largest, i.e. get the major class.
     *  
     * @param classes  The key value set of classes.
     * 
     * @return  The name of the major class
     */  
    public String maxClass(Map<String, Integer> classes) {  
        String maxC = "";  
        int max = -1;  
        Iterator iter = classes.entrySet().iterator();  
        for (int i = 0; iter.hasNext(); i++) {  
            Map.Entry entry = (Map.Entry) iter.next();  
            String key = (String) entry.getKey();  
            Integer val = (Integer) entry.getValue();  
            if (val > max) {  
                max = val;  
                maxC = key;  
            }  
        }  
        return maxC;  
    }  
  
    /** 
     * Build the decision tree.
     *  
     * @param datas  Training data set
     * @param attrList  Candidate attributes set
     * 
     * @return  Root of the decision tree
     */  
    public TreeNode buildTree(ArrayList<ArrayList<String>> Datas,  ArrayList<String> attrList) {  
        TreeNode node = new TreeNode();
        ArrayList<String> AttrList = new ArrayList<String>(attrList);
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
        for(int k = 0; k < Datas.size(); k++){
            ArrayList<String> tempstring = new ArrayList<String>(Datas.get(k));
            datas.add(tempstring);
        }

        node.setDatas(datas);
        node.setCandAttr(AttrList);
        Map<String, Integer> classes = classOfDatas(datas); // Get the classes and their counts of specific data set.  
        if (classes.size() < 2) {  
            Iterator iter = classes.entrySet().iterator();  
            Map.Entry entry = (Map.Entry) iter.next();  
            String name = entry.getKey().toString();  
            node.setName(name);  
            return node;  
        }  
        Gain gain = new Gain(datas, AttrList);  
        double styWhoEx = gain  
                .getStylebookWholeExpection(classes, datas.size()); // The expect value of samples
        int bestAttrIndex = gain.bestGainAttrIndex(styWhoEx); // Get the "best" attribute to partition data
        if(bestAttrIndex == -1){
            Iterator iter = classes.entrySet().iterator();  
            Map.Entry entry = (Map.Entry) iter.next();  
            String name = entry.getKey().toString();  
            node.setName(name);  
            return node;  
        }
        
        
        ArrayList<String> rules = gain.getValues(datas, bestAttrIndex); // Get the column range of "best" candidate attribute
        node.setRule(rules); // Set the partition rules of nodes
        node.setName(AttrList.get(bestAttrIndex)); // Set the name of "best" partition attribute
//        if (rules.size() > 2) { // ? need consideration
            AttrList.remove(bestAttrIndex);  
//        }
        // According to the partitioned subset, repeat the Entropy calculation and data set partition, 
        // until to the leaf node or some prescriptive level.
        for (int i = 0; i < rules.size(); i++) {  
            String rule = rules.get(i);
            ArrayList<ArrayList<String>> di1 = gain.datasOfValue(bestAttrIndex, rule);
            
            // di Assignment
            ArrayList<ArrayList<String>> di = new ArrayList<ArrayList<String>>();
            for(int k = 0; k < di1.size(); k++){
                ArrayList<String> tempstring = new ArrayList<String>(di1.get(k));
                di.add(tempstring);
            }
                    
            for (int j = 0; j < di.size(); j++) {  
                di.get(j).remove(bestAttrIndex);  
            }  
            if (di.size() == 0) {  
                TreeNode leafNode = new TreeNode();  
                // leafNode.setName(maxC);  
                leafNode.setDatas(di);  
                leafNode.setCandAttr(AttrList);  
                node.getChild().add(leafNode);  
            } else {  
                TreeNode newNode = buildTree(di, AttrList);  
                node.getChild().add(newNode);  
            }  
        }  
        return node;
    }
    
    
    /** 
     * print tree structure
     *  
     * @param datas Current root that user want to print
     * 
     */  
    public Map<String,Map<String, Map<String, Integer>>> TransInfo(ArrayList<ArrayList<String>> Data, 
            ArrayList<String> attrList){
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
     * Build the decision tree.
     *  
     * @param datas  Training data set
     * @param attrList  Candidate attributes set
     * 
     * @return  Root of the decision tree
     */
    
    // 过渡方法
    public TreeNode CombinedTreeConstruction(ArrayList<ArrayList<String>> DataA, 
            ArrayList<ArrayList<String>> DataB, ArrayList<String> candAttr){

        Map<String,Map<String, Map<String, Integer>>> transInfo = null;
        
        ////////////////////////////////////////////////////////////////////////
        /////////////////////////// copy data //////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        ArrayList<ArrayList<String>> dataA = new ArrayList<ArrayList<String>>();
        for(int k = 0; k < DataA.size(); k++){
            ArrayList<String> tempstring = new ArrayList<String>(DataA.get(k));
            dataA.add(tempstring);
        }
        
        ArrayList<ArrayList<String>> dataB = new ArrayList<ArrayList<String>>();
        for(int k = 0; k < DataB.size(); k++){
            ArrayList<String> tempstring = new ArrayList<String>(DataB.get(k));
            dataB.add(tempstring);
        }
        
        ////////////////////////////////////////////////////////////////////////
        /////////////////////////// copy data //////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        DecisionTree testdetree = new DecisionTree();
        DecisionTree combinedtree = new DecisionTree();
        transInfo = testdetree.TransInfo(dataB,candAttr);
//        System.out.println("Information Extracted: ");
        TreeNode combinedroot = combinedtree.buildcombinedTree(dataA, dataB, transInfo ,candAttr);

        return combinedroot;
    }
    
    
    /** 
     * Build the decision tree.
     *  
     * @param datas  Training data set
     * @param attrList  Candidate attributes set
     * 
     * @return  Root of the decision tree
     */ 
    
    public TreeNode buildcombinedTree(ArrayList<ArrayList<String>> Datas, ArrayList<ArrayList<String>> Datas_, 
            Map<String,Map<String, Map<String, Integer>>> transInfo, ArrayList<String> attrList) {  
        
        ArrayList<String> AttrList = new ArrayList<String>(attrList);
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
        for(int k = 0; k < Datas.size(); k++){
            ArrayList<String> tempstring = new ArrayList<String>(Datas.get(k));
            datas.add(tempstring);
        }
        
        ArrayList<ArrayList<String>> datas_ = new ArrayList<ArrayList<String>>();
        for(int k = 0; k < Datas_.size(); k++){
            ArrayList<String> tempstring = new ArrayList<String>(Datas_.get(k));
            datas_.add(tempstring);
        }
        
        
        
        
        
        
        String Label = AttrList.get(AttrList.size()-1);
        
        
        if(transInfo != null){
            System.out.println("Information Received ......");  Infomation.add("Information Received ......" + "\n");
            for(String Key : transInfo.keySet()){
                if(Key != Label){
                    System.out.println(Key);  Infomation.add(Key + "\n");
                    for(String Key1 : transInfo.get(Key).keySet()){
                        System.out.println("  " + Key1);  Infomation.add("  " + Key1 + "\n");
                        for(String Key2 : transInfo.get(Key).get(Key1).keySet()){
                            System.out.print("    " + Key2 + " => ");  Infomation.add("    " + Key2 + " => ");
                            System.out.println(transInfo.get(Key).get(Key1).get(Key2).toString() + " ");  Infomation.add(transInfo.get(Key).get(Key1).get(Key2).toString() + " " + "\n");
//                            System.out.println("");
                        }
                    }
                }
            }
        }
        
        System.out.print("\n\n\n");  Infomation.add("\n\n\n");
        System.out.println("Decision Tree Constructing ...");  Infomation.add("Decision Tree Constructing ..." + "\n");
        
        

        
        TreeNode node = new TreeNode();  
        node.setDatas(datas);  
        node.setCandAttr(AttrList);  
        Map<String, Integer> classes = classOfDatas(datas); // Get the classes and their counts of specific data set.  
        
        
        //Union of key sets of two dataset
        
        Set<String> UnionClasses = new HashSet<String>(classes.keySet());
        UnionClasses.addAll(transInfo.get(Label).keySet());
        int itemnum = 0;
        
        for(String key : UnionClasses){
            int classcount = 0;
            if(transInfo.get(Label).get(key) != null){
                classcount = transInfo.get(Label).get(key).get(key);
            }
            
            if (classes.containsKey(key)) { // If the attribute has been exist, the corresponding 
                                          //attribute +1 or generate the attribute.
                classes.put(key, classes.get(key) + classcount);  
            } else {  
                classes.put(key, classcount);  
            }
            itemnum += classcount;
        }
        
        if (classes.size() < 2) {  
            Iterator iter = classes.entrySet().iterator();  
            Map.Entry entry = (Map.Entry) iter.next();  
            String name = entry.getKey().toString();  
            node.setName(name);  
            return node;  
        }  
        Gain gain = new Gain(datas, AttrList);  
        double styWhoEx = gain.getStylebookWholeExpection(classes, datas.size()+itemnum); // The expect value of samples
        int bestAttrIndex = gain.bestGainAttrIndexCombined(styWhoEx, transInfo); // Get the "best" attribute to partition data
        ArrayList<String> rules = gain.getValues(datas, bestAttrIndex); // Get the column range of "best" candidate attribute
        
        if(bestAttrIndex == -1){//for the case: only two samples left and their attributes are all equal but they belong to different classes
            Iterator iter = classes.entrySet().iterator();  
            Map.Entry entry = (Map.Entry) iter.next();  
            String name = entry.getKey().toString();  
            node.setName(name);  
            return node;  
        }
        //////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////
        
//        ArrayList<String> rules = new ArrayList<String>();  
        String r = "";  
        for (int i = 0; i < datas_.size(); i++) {  
            r = datas_.get(i).get(bestAttrIndex);  
            if (!rules.contains(r)) {  
                rules.add(r);  
            }  
        }
        //////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////
        node.setRule(rules); // Set the partition rules of nodes
        node.setName(AttrList.get(bestAttrIndex)); // Set the name of "best" partition attribute
        
//        if (rules.size() > 2) { // ? need consideration
            AttrList.remove(bestAttrIndex);  
//        }  
  
        // According to the partitioned subset, repeat the Entropy calculation and data set partition, 
        // until to the leaf node or some prescriptive level.
        for (int i = 0; i < rules.size(); i++) {  
            String rule = rules.get(i);
            
            
            // 
            ArrayList<ArrayList<String>> di1 = gain.datasOfValue(bestAttrIndex, rule);
            
            // di Assignment
            ArrayList<ArrayList<String>> di = new ArrayList<ArrayList<String>>();
            for(int k = 0; k < di1.size(); k++){
                ArrayList<String> tempstring = new ArrayList<String>(di1.get(k));
                di.add(tempstring);
            }
            ////////////////////////////////////////////////////////////////////
            //////////////////////////// self add //////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ArrayList<ArrayList<String>> di_ = new ArrayList<ArrayList<String>>();  
            
            for (int j = 0; j < datas_.size(); j++) {
                ArrayList<String> t = new ArrayList<String>(datas_.get(j));
//                t = datas_.get(j);  
                if (t.get(bestAttrIndex).equals(rule)) {  
                    di_.add(t);  
                }  
            }
            // remove the best attribute column
            for (int j = 0; j < di_.size(); j++) {  
                di_.get(j).remove(bestAttrIndex);  
            }
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            for (int j = 0; j < di.size(); j++) {  
                di.get(j).remove(bestAttrIndex);  
            }  
            if (di.size() == 0) {  
                if(di_.size() == 0){
                    TreeNode leafNode = new TreeNode();  
                    // leafNode.setName(maxC);  
                    leafNode.setDatas(di);  
                    leafNode.setCandAttr(AttrList);  
                    node.getChild().add(leafNode);
                }else{
                    TreeNode newNode = buildTree(di_, AttrList);  
                    node.getChild().add(newNode);
                }
            } else {  
                TreeNode newNode = CombinedTreeConstruction(di, di_, AttrList);  
                node.getChild().add(newNode);  
            }  
        }  
        return node;  
    }  
}  