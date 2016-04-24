/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;    
import java.util.ArrayList;    
/**  
 * Decision Tree Node Class
 * 
 */    
public class TreeNode {    
    private String name; // Node name(name of the partition attribute)
    private ArrayList<String> rule; // The rules for partitioning data
    ArrayList<TreeNode> child; // The set of sub nodes
    private ArrayList<ArrayList<String>> datas; // Training set partitioned into this node
    private ArrayList<String> candAttr; // Candidate attributes partitioned into this node
    public TreeNode() {    
        this.name = "";    
        this.rule = new ArrayList<String>();    
        this.child = new ArrayList<TreeNode>();    
        this.datas = null;    
        this.candAttr = null;    
    }    
    public ArrayList<TreeNode> getChild() {    
        return child;    
    }    
    public void setChild(ArrayList<TreeNode> child) {    
        this.child = child;    
    }    
    public ArrayList<String> getRule() {    
        return rule;    
    }    
    public void setRule(ArrayList<String> rule) {    
        this.rule = rule;    
    }    
    public String getName() {    
        return name;    
    }    
    public void setName(String name) {    
        this.name = name;    
    }    
    public ArrayList<ArrayList<String>> getDatas() {    
        return datas;    
    }    
    public void setDatas(ArrayList<ArrayList<String>> datas) {    
        this.datas = datas;    
    }    
    public ArrayList<String> getCandAttr() {    
        return candAttr;    
    }    
    public void setCandAttr(ArrayList<String> candAttr) {    
        this.candAttr = candAttr;    
    }    
}    