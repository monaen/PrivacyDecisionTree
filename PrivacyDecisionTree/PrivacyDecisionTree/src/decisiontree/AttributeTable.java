package decisiontree;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;  
import java.util.concurrent.ExecutionException;  
import java.math.BigDecimal;  
import static java.lang.Math.*;  
  
/**
 *
 * @author Nan Meng
 */
public class AttributeTable{
    private ArrayList<ArrayList<String>> D = null; // training set
    private ArrayList<String> attrList = null; // candidate attributes set
    private ArrayList<String> cancelAttr;
    
    public AttributeTable(ArrayList<ArrayList<String>> datas, ArrayList<String> attrList) {
        this.D = datas;
        this.attrList = attrList;
    }
    
    
    public AttributeTable(ArrayList<ArrayList<String>> datas, ArrayList<String> attrList, 
            ArrayList<String> cancelAttr) {
        
        this.D = datas;
        this.attrList = attrList;
        if(cancelAttr != null)
            this.cancelAttr = cancelAttr;

        if(cancelAttr != null){
//            int attrIndex = 0;
//            for(int i = 0; i < cancelAttr.size(); i++){
//                attrIndex = attrList.indexOf(cancelAttr.get(i));
//                this.D.get(i).remove(attrIndex);
//            }
        
            for(int i = 0; i < cancelAttr.size(); i++){
                this.attrList.remove(cancelAttr.get(i));
            }
        
        }

        
        
    }
    
    public Map<String, Map<String, Integer>> getIndexTable(int Index){
        Map<String, Map<String, Integer>> AttrTable = new HashMap<String, Map<String, Integer>>();
        String attribute = attrList.get(Index);
        ArrayList<String> values = getValues(D, Index);
        
        
        for(int j = 0; j < values.size(); j++){
            Map<String, Integer> subtable = new HashMap<String, Integer>();
            ArrayList<ArrayList<String>> dv = datasOfValue(Index, values.get(j));
            String C = ""; // store the class
            for(int i = 0; i < dv.size(); i++){
                ArrayList<String> temp1 = dv.get(i);
                C = temp1.get(temp1.size()-1);
                if (subtable.containsKey(C)) {  
                    subtable.put(C, subtable.get(C) + 1);  
                }else{  
                    subtable.put(C, 1);  
                }
            }

            AttrTable.put(values.get(j), subtable);  

        
        }
        
        return AttrTable;
    }
    
//    
//        public Map<String, Integer> getTable(int Index){
//            
//            return AttrTable;
//    }
//    
//    
    /** 
     * Get the column range of best candidate attribute(assume all the 
     * attributes of the column is finite and nominal type) 
     *  
     * @param attrIndex  Index of a specific attribute column
     * 
     * @return  Range set
     */  
    public ArrayList<String> getValues(ArrayList<ArrayList<String>> datas,  
            int attrIndex) {  
        ArrayList<String> values = new ArrayList<String>();  
        String r = "";  
        for (int i = 0; i < datas.size(); i++) {  
            r = datas.get(i).get(attrIndex);  
            if (!values.contains(r)) {  
                values.add(r);  
            }  
        }  
        return values;  
    }
    
    /** 
     * Get all of the tuples in specific attribute column within specific range.
     * 
     * @param attrIndex  Index of a specific attribute column
     * @param value  Specific range of a specific attribute column
     * 
     * @return  All of the tuples in specific attribute column within specific range.
     */  
    public ArrayList<ArrayList<String>> datasOfValue(int attrIndex, String value) {  
        ArrayList<ArrayList<String>> Di = new ArrayList<ArrayList<String>>();  
        ArrayList<String> t = null;  
        for (int i = 0; i < D.size(); i++) {  
            t = D.get(i);  
            if (t.get(attrIndex).equals(value)) {  
                Di.add(t);  
            }  
        }  
        return Di;  
    }
    
    
    /** 
     * 
     * Get the range and count number of specific attribute of data
     * 
     * @param d  Specific data set
     * @param attrIndex  Index of a specific attribute column
     * @return  Map of class and the count of this class
     */  
    public Map<String, Integer> valueCounts(ArrayList<ArrayList<String>> datas, int attrIndex) {  
        Map<String, Integer> valueCount = new HashMap<String, Integer>();  
        String c = "";  
        ArrayList<String> tuple = null;  
        for (int i = 0; i < datas.size(); i++) {  
            tuple = datas.get(i);  
            c = tuple.get(attrIndex);  
            if (valueCount.containsKey(c)) {  
                valueCount.put(c, valueCount.get(c) + 1);  
            } else {  
                valueCount.put(c, 1);  
            }  
        }  
        return valueCount;  
    }
}
