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
			  "������������������ ����������������        /\\        ���������������� \n"
			+ "��������  �������� ����������������       /  \\       ����������������  \n"
			+ "      ��  ��       ����������������      / /\\ \\      ����\n"
			+ "      ��  ��       ����������������     / /  \\ \\     ����\n" 
			+ "      ��  ��                   ����    / /����\\ \\    ����\n"
			+ "��������  �������� ����������������   / /������\\ \\   ������������������\n" 
			+ "������������������ ����������������  ����      ����  ������������������";
	private static String Server_state = "δ����";
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
		System.out.println("ϵͳ��ʼ����.....");
		System.out.println("���ض�̬���ӿ� ������.....");
		cs = new clearScreen();
		System.out.println("������ϵͳ ������.....");
		sc = new Scanner(System.in);
			System.out.println("ʱ��ϵͳ������.....");
		 timer=new Timer();
			System.out.println("HTTP��������ʼ����.....");
			 server = new httpServer_Server(this);
	}
	private  void Refresh() {
		 cs.clearScreen();
		 ShowTable = "*ISAC ��������̨����״̬: "+Server_state
					+"\n   �����������:"+Vist_Number;
		 System.out.println(ISAC_LOGO);
		 System.out.println(ShowTable);
	}
	private  void MainMenu() {
		tmpstr = sc.nextLine();
		String temp = tmpstr;
		String[] command = temp.split(" ");
		if(tmpstr.equals("1")&&Server_state.equals("δ����")) {
			Server_state = "������";
			server.Start();
			
		}else if(tmpstr.equals("1")&&Server_state.equals("������")) {
			System.out.println("[����]�������Ѿ��������޷��ٴο���!");
		}else if(tmpstr.equals("0")&&Server_state.equals("������")) {
			Server_state = "δ����";
			server.Stop();
		}else if(tmpstr.equals("0")&&Server_state.equals("δ����")) {
			System.out.println("[����]��������δ�������޷����йر�!");
		}else if(command[0].equals("addgear")) {
			if(!server.AddGearByCommand(tmpstr)) {
				System.out.println("��ȷ��ʽΪ: addgear [װ������(���ֱ��)] [����] [����ֵ] [����ֵ] [����ֵ] [װ��ֵ] [��Ҫ����һ] [��Ҫ���Զ�] [��Ҫ����һ] [��Ҫ���Զ�] [��Ҫ������] [�ϰ�] ");
			    System.out.println("���п������� ����0");
			}
		}else if(command[0].equals("addweapon")) {
			if(!server.AddWeaponByCommand (tmpstr)) {
				System.out.println("��ȷ��ʽΪ: addgear [װ������(���ֱ��)] [����]  [�츳һ] [�츳��] [�츳��]  [�ϰ�] ");
			    System.out.println("���п������� ����0");
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
		timer.schedule(task,delay);//��ٺ���  
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