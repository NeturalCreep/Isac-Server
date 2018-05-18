
public class Weapon {
    private int Talent1;
    private int Talent2;
    private int Talent3;
    private int ItemNum;
    private int Grand;
    private String Needed;
    public void SetNeeded(String Needed) {
    	this.Needed = Needed;
    }
    public  void SetItemNum(int ItemNum){
        this.ItemNum = ItemNum;
    }
    public  void SetItemGrand(int Grand){
        this.Grand = Grand;
    }
    public void SetTalent1(int Talent1) {
    	this.Talent1 = Talent1;
    }
    public void SetTalent2(int Talent2) {
    	this.Talent2= Talent2;
    }
    public void SetTalent3(int Talent3) {
    	this.Talent3  = Talent3;
    }
    public String GetNeeded() {
    	return Needed;
    }
    public int GetTalent1() {
    	return Talent1;
    }
    public int GetTalent2() {
    	return Talent2;
    }
    public int GetTalent3() {
    	return Talent3;
    }
    public int GetItemNum(){
        return  ItemNum;
    }
    public int GetGrand(){
        return  Grand;
    }
}
