
public class Gear {
    private String Main1;
    private String Main2;
    private String Sec1;
    private String Sec2;
    private String Sec3;
    private int ItemNum;
    private int Fire;
    private int Health;
    private int Electric;
    private int Grand;
    private int arm ;
    private String Needed;
    public void SetNeeded(String Needed) {
    	this.Needed = Needed;
    }
    public  void SetItemNum(int ItemNum){
        this.ItemNum = ItemNum;
    }
    public void SetArm(int num) {
    	this.arm = num;
    }
    public  void SetItemFire(int Fire){
        this.Fire = Fire;
    }
    public  void SetItemHealth(int Health){
        this.Health = Health;
    }
    public  void SetItemElectric(int Electric){
        this.Electric = Electric;
    }
    public  void SetItemGrand(int Grand){
        this.Grand = Grand;
    }
    public void SetMain1(String data){
        this.Main1 = data;
    }
    public void SetMain2(String data){
        this.Main2 = data;
    }
    public void SetSec1(String data){
        this.Sec1 = data;
    }
    public void SetSec2(String data){
        this.Sec2 = data;
    }
    public void SetSec3(String data){
        this.Sec3 = data;
    }
    public void SetGrand(int Grand) {
    	this.Grand = Grand;
    }
    public String GetMain1(){
        return  Main1;
    }
    public String GetMain2(){
        return  Main2;
    }
    public String GetSec1(){
        return  Sec1;
    }
    public String GetSec2(){
        return  Sec2;
    }
    public String GetNeeded() {
    	return Needed;
    }
    public String GetSec3(){
        return  Sec3;
    }
    public int GetItemNum(){
        return  ItemNum;
    }
    public int GetFire(){
        return  Fire;
    }
    public int GetHealth(){
        return  Health;
    }
    public int GetElectric(){
        return  Electric;
    }
    public int GetGrand(){
        return  Grand;
    }
    public int GetArm() {
    	return arm;
    }
}
