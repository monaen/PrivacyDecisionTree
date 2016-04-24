package decisiontree;  
  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;  
import java.util.concurrent.ExecutionException;  
import java.math.BigDecimal;  
import static java.lang.Math.*;  
import java.util.HashSet;
import java.util.Set;
  
/** 
 * Choose the Best Partition Attribute
 *  
 */  
public class Gain {  
    private ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>(); // training set
    private ArrayList<String> attrList = null; // candidate attributes set
  
    public Gain(ArrayList<ArrayList<String>> datas, ArrayList<String> attrList) {  
        this.D = datas;
        this.attrList = new ArrayList<String>(attrList);  
    }
  
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
     * 
     * Get the range and count number of specific attribute of data
     * 
     * @param d  Specific data set
     * @param attrIndex  Index of a specific attribute column
     * @return  Map of class and the count of this class
     */  
    public Map<String, Integer> valueCounts(ArrayList<ArrayList<String>> datas,  
            int attrIndex) {  
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
     * Get the expected number of partitioned data set based on the specific attribute
     *  
     * @param attrIndex  Index of a specific attribute
     * 
     * @return  The expected number of data set partitioned by specific attribute.
     */  
    public double infoAttr(int attrIndex) {  
        double info = 0.000;  
        ArrayList<String> values = getValues(D, attrIndex);  
        DecisionTree dt = new DecisionTree();  
        Map<String, Integer> classes; // Get a value of candidate attributes
        double n1 = 0.000;
        
        
        for (int i = 0; i < values.size(); i++) {  
            double e = 0.0, f = 0.0;  
            ArrayList<ArrayList<String>> dv = datasOfValue(attrIndex, values.get(i));  
            classes = dt.classOfDatas(dv);
            
            ArrayList<String> claset = new ArrayList<String>();
            for(String key : classes.keySet()){
                claset.add(key);
            }
            n1 = ((double) dv.size()) / ((double) D.size());  
            try {
                e = (double) classes.get(claset.get(0));  
                f = (double) classes.get(claset.get(1));  
            } catch (Exception exce) {
  
            }  
  
            info += n1 * gerException(e, f);  
        }  
        return info;  
    }
    
    /** 
     * 
     * Get the expected number of partitioned data set based on the specific attribute
     *  
     * @param attrIndex  Index of a specific attribute
     * 
     * @return  The expected number of data set partitioned by specific attribute.
     */  
    public double infoAttrCombined(int attrIndex, 
            Map<String,Map<String, Map<String, Integer>>> TransInfo) {
        
        double info = 0.000;  
        ArrayList<String> values = getValues(D, attrIndex);
        for(String key : TransInfo.get(attrList.get(attrIndex)).keySet()){
            if(!values.contains(key)){
                values.add(key);
            }
        }
        
        
        DecisionTree dt = new DecisionTree();  
        Map<String, Integer> classes; // Get a value of candidate attributes
        double n1 = 0.000;
        
        for (int i = 0; i < values.size(); i++) {  
            double e = 0.0, f = 0.0;  
            ArrayList<ArrayList<String>> dv = datasOfValue(attrIndex, values.get(i));  
            classes = dt.classOfDatas(dv);
            
            
            // here just only two classes considered ......
            ArrayList<String> claset = new ArrayList<String>();
            for(String key : classes.keySet()){
                claset.add(key);
            }
            int itemnum = 0;
            
            if(TransInfo.get(attrList.get(attrIndex)).get(values.get(i)) != null){
            for(String key : TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).keySet()){
                if(!claset.contains(key)){
                    claset.add(key);
                    classes.put(key,0);
                }
                itemnum += TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(key);
            }
            }else{
                ;
            }
            ///////////////////////////////////////////////
            
            int transnum = 0;
            for(String k : TransInfo.get(attrList.get(attrList.size()-1)).keySet()){
                transnum += TransInfo.get(attrList.get(attrList.size()-1)).get(k).get(k);
            }
            n1 = ((double) dv.size() + (double)itemnum) / ((double) D.size() + (double)transnum);  
            

            try {
                if(TransInfo.get(attrList.get(attrIndex)).get(values.get(i)) == null){
                    f = f + (double) classes.get(claset.get(0)) + 0.0;
                    e = e + (double) classes.get(claset.get(1)) + 0.0;
                }
                else if(TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(0)) == null)
                {
                    f = f + (double) classes.get(claset.get(0)) + 0.0;
                    if(TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(1)) == null){
                        e = e + (double) classes.get(claset.get(1)) + 0.0;
                    }else{
                        e = (double) classes.get(claset.get(1)) + (double)TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(1));
                    }
                }
                
                else{
                    f = (double) classes.get(claset.get(0)) + (double)TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(0));
                    if(TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(1)) == null){
                        e = e + (double) classes.get(claset.get(1)) + 0.0;
                    }else{
                        e = (double) classes.get(claset.get(1)) + (double)TransInfo.get(attrList.get(attrIndex)).get(values.get(i)).get(claset.get(1));
                    }
                }
            } catch (Exception exce) {
  
            }  
  
            info += n1 * gerException(e, f);  
        }  
        return info;  
    }
  
    /** 
     * Get the index of the best partition attribute
     *  
     * @return  Index of the best partition attribute
     */  
    public int bestGainAttrIndex(double styWhoEx) {  
        int index = -1;  
        double gain = 0.000;  
        double tempGain = 0.000;  
        for (int i = 0; i < attrList.size()-1; i++) {  
            tempGain = styWhoEx - infoAttr(i);  
            if (tempGain > gain) {
                gain = tempGain;  
                index = i;  
            }  
        }  
        return index;  
    }
    
    
    /** 
     * Get the index of the best partition attribute
     *  
     * @return  Index of the best partition attribute
     */  
    public int bestGainAttrIndexCombined(double styWhoEx, 
            Map<String,Map<String, Map<String, Integer>>> TransInfo){
        
        int index = -1;  
        double gain = 0.000;  
        double tempGain = 0.000;  
        for (int i = 0; i < attrList.size()-1; i++) {  
            tempGain = styWhoEx - infoAttrCombined(i,TransInfo);  
            if (tempGain > gain) {
                gain = tempGain;  
                index = i;  
            }  
        }  
        return index;  
    }
  
    /** 
     * Get the entire expected number of samples
     * 
     * @return  The entire expected number of samples
     */  
    public double getStylebookWholeExpection(Map<String, Integer> classes, int n) {  
        double styWhoEx = 0.0;  
        Iterator iter = classes.entrySet().iterator();  
        for (int i = 0; iter.hasNext(); i++) {  
            Map.Entry entry = (Map.Entry) iter.next();  
            Integer val = (Integer) entry.getValue();  
            double vn = (double) val / (double) n;  
            styWhoEx += -(vn) * ((log((double) vn) / (log((double) 2))));  
        }  
        return styWhoEx;  
    }  
  
    /** 
     * Calculate the expected number of attributes
     *  
     * @return  Index of the best partition attribute
     */  
    private double gerException(double e, double f) {  
        double info = 0.0000;  
        if (e == 0.0 || f == 0.0) {  
            info = 0.0;  
            return info;  
        } else if (e == f) {  
            info = 1.0;  
            return info;  
        } else {  
            double sum = e + f;  
            info = -(e / sum) * ((log((double) (e / sum)) / (log((double) 2))))  
                    - (f / sum)  
                    * ((log((double) (f / sum)) / (log((double) 2))));  
        }  
        return info;  
    }  
  
}  
