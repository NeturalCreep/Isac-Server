import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Cmd   {
	private static DataCan Can ;
	
	public static void main(String[] args) throws IOException {
		Can = new DataCan();
	}
}

class DataCan implements CallBack{

	private static httpServer_Server server;
	private static String ISAC_LOGO = 
			  "┌───────┐ ┌──────┐        /\\        ┌──────┐ \n"
			+ "└──┐  ┌──┘ │┌─────┘       /  \\       │┌─────┘  \n"
			+ "      │  │       │└─────┐      / /\\ \\      ││\n"
			+ "      │  │       └─────┐│     / /  \\ \\     ││\n" 
			+ "      │  │                   ││    / /──\\ \\    ││\n"
			+ "┌──┘  └──┐ ┌─────┘│   / /───\\ \\   │└──────┐\n" 
			+ "└───────┘ └──────┘  └┘      └┘  └───────┘";
	private static String Server_state = "未启动";
	private static int Vist_Number = 0;
	private static int Wanted_Item = 0;
	private static String ShowTable ="";
	private static clearScreen cs;
	private static Scanner sc ;
	private static Timer timer;
	private static String tmpstr="";
	 public DataCan() throws IOException {
		// TODO Auto-generated constructor stub
		 init();
		 Refresh();
		 MainMenu();
	}

	private  void init() throws IOException   {
		System.out.println("系统初始化中.....");
		System.out.println("本地动态链接库 加载中.....");
		cs = new clearScreen();
		System.out.println("输入检测系统 加载中.....");
		sc = new Scanner(System.in);
			System.out.println("时钟系统加载中.....");
		 timer=new Timer();
			System.out.println("HTTP服务器初始化中.....");
			 server = new httpServer_Server(this);
	}
	private  void Refresh() {
		 cs.clearScreen();
		 ShowTable = "*ISAC 服务器后台运行状态: "+Server_state
					+"\n   今天访问人数:"+Vist_Number;
		 System.out.println(ISAC_LOGO);
		 System.out.println(ShowTable);
	}
	private  void MainMenu() {
		tmpstr = sc.nextLine();
		String temp = tmpstr;
		String[] command = temp.split(" ");
		if(tmpstr.equals("1")&&Server_state.equals("未启动")) {
			Server_state = "运行中";
			server.Start();
			
		}else if(tmpstr.equals("1")&&Server_state.equals("运行中")) {
			System.out.println("[警告]服务器已经开启，无法再次开启!");
		}else if(tmpstr.equals("0")&&Server_state.equals("运行中")) {
			Server_state = "未启动";
			server.Stop();
		}else if(tmpstr.equals("0")&&Server_state.equals("未启动")) {
			System.out.println("[警告]服务器尚未开启，无法进行关闭!");
		}else if(command[0].equals("addgear")) {
			if(!server.AddGearByCommand(tmpstr)) {
				System.out.println("正确格式为: addgear [装备种类(数字编号)] [评分] [火力值] [体力值] [电力值] [装甲值] [主要属性一] [主要属性二] [次要属性一] [次要属性二] [次要属性三] [老板] ");
			    System.out.println("如有空属性请 输入0");
			}
		}else if(command[0].equals("addweapon")) {
			if(!server.AddWeaponByCommand (tmpstr)) {
				System.out.println("正确格式为: addgear [装备种类(数字编号)] [评分]  [天赋一] [天赋二] [天赋三]  [老板] ");
			    System.out.println("如有空属性请 输入0");
			}
		}
		TimerTask(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Refresh();
				MainMenu();
				this.cancel();
			}
		},1000);
	}
	private static void TimerTask(TimerTask task,int delay) {
		timer.schedule(task,delay);//五百毫秒  
	}

	@Override
	public void DataPutout(String Table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void FlagPutOut(DataFlag Table) {
		// TODO Auto-generated method stub
		switch(Table) {
		case visitorsPlus:
			Vist_Number++;
			Refresh();
			break;
		}
	}
}