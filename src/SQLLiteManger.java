import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SQLLiteManger {
	private Connection conn;
	private Statement stat;
	private CallBack Back;

	public SQLLiteManger(CallBack InterfaceBack) {
		this.Back = InterfaceBack;
	}

	public void SetupDb() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Data.db");
			stat = conn.createStatement();
			DatabaseMetaData meta = conn.getMetaData();
			if (!meta.getTables(null, null, "User_Data", null).next()) {
				Init_DB();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String SelectAllItemWanted() throws SQLException {
		ResultSet res = null;
		res = stat.executeQuery("Select * from Gear_Data where State = 0");

		JSONObject jo = new JSONObject();
		JSONArray Gear = new JSONArray();
		while (res.next()) {
			JSONArray ja = new JSONArray();
			ja.add(res.getString(1));
			ja.add(res.getString(2));
			ja.add(res.getString(3));
			ja.add(res.getString(4));
			ja.add(res.getString(5));
			ja.add(res.getString(6));
			ja.add(res.getString(7));
			ja.add(res.getString(8));
			ja.add(res.getString(9));
			ja.add(res.getString(10));
			ja.add(res.getString(11));
			ja.add(res.getString(12));
			ja.add(res.getString(13));
			Gear.add(ja);
		}
		jo.put("Gear", Gear);
		res = stat.executeQuery("Select * from Weapon_Data where State = 0");
		JSONArray Weapon = new JSONArray();
		while (res.next()) {
			JSONArray ja = new JSONArray();
			ja.add(res.getString(1));
			ja.add(res.getString(2));
			ja.add(res.getString(3));
			ja.add(res.getString(4));
			ja.add(res.getString(5));
			ja.add(res.getString(6));
			ja.add(res.getString(7));
			Weapon.add(ja);
		}
		jo.put("Weapon", Weapon);
		return jo.toString();

	}

	public String CheckUser(String Json) throws SQLException {
		JSONObject object = JSONObject.fromObject(Json);
		String UUID  = CreateUUID().toString();
		if (object.getString("state").equals("Login")) {
			ResultSet set = stat.executeQuery(
					"select PassWord from User_Data where UserName ='" + object.getString("UserName") + "'");
			if (object.getString("PassWord").equals(set.getObject(0))) {
			}
		} else if (object.getString("state").equals("Register")) {
			stat.executeUpdate("INSERT INTO User_Data VALUES('" + object.getString("UserName") + "','"
					+ object.getString("PassWord") + "','"+UUID+"')");
		}
		return UUID;
	}
	private UUID CreateUUID() {
		return UUID.randomUUID();
	}
	public void addGear(Gear gear) throws SQLException {
		stat.executeUpdate(
				"Insert into Gear_Data(ItemNumber,Grand,Fire,Health,Electric,Arm,State,Mainproperty1,Mainproperty2,secondaryproperty1,secondaryproperty2,"
						+ "secondaryproperty3,Needed) values (" + gear.GetItemNum() + "," + gear.GetGrand() + ","
						+ gear.GetFire() + "," + gear.GetHealth() + "," + gear.GetElectric() + "," + gear.GetArm()
						+ ",0,'" + gear.GetMain1() + "','" + gear.GetMain2() + "','" + gear.GetSec1() + "','"
						+ gear.GetSec2() + "','" + gear.GetSec3() + "','" + gear.GetNeeded() + "')");
	}

	public void addWeapon(Weapon weapon) throws SQLException {
		stat.execute("Insert into Weapon_Data(ItemNumber,Grand,Talent1,Talent2,Talent3,State,Needed) values("
				+ weapon.GetItemNum() + "," + weapon.GetGrand() + "," + weapon.GetTalent1() + "," + weapon.GetTalent2()
				+ "," + weapon.GetTalent3() + ",0,'" + weapon.GetNeeded() + "')");
	}

	private void Init_DB() throws SQLException {
		stat.executeUpdate("Create Table User_Data(UserName varchar(30) not null,PassWord varchar(30) not null,UUID varchar(30) not null)");
		stat.executeUpdate(
				"Create Table Gear_Data(Number INTEGER   PRIMARY KEY AUTOINCREMENT,Grand int not null,ItemNumber int not null,"
						+ "Fire int not null," + "Health int not null," + "Electric int not null," + "Arm int not null,"
						+ "State boolean not null," + "Mainproperty1 varchar(8) not null,"
						+ "Mainproperty2 varchar(8) not null," + "secondaryproperty1 varchar(8) not null,"
						+ "secondaryproperty2 varchar(8) not null," + "secondaryproperty3 varchar(8) not null,"
						+ "Needed varchar(30) not null)");
		stat.executeUpdate(
				"Create Table Weapon_Data(Number INTEGER   PRIMARY KEY AUTOINCREMENT,ItemNumber int not null,Grand int not null,State boolean not null,"
						+ "Talent1 int not null,Talent2 int not null,Talent3 int Not Null,Needed varchar(30) not null)");
	}
}
