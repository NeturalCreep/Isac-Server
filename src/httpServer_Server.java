import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.SQLException;

import com.sun.net.httpserver.*;
import com.sun.net.httpserver.spi.HttpServerProvider;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class httpServer_Server implements CallBack {
	private HttpServer httpserver;
	private SQLLiteManger Manger;
	private CallBack MenutoRefresh;

	public httpServer_Server(CallBack Menu) {
		this.MenutoRefresh = Menu;
		HttpServerProvider provider = HttpServerProvider.provider();
		try {
			httpserver = provider.createHttpServer(new InetSocketAddress(19017), 100);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 监听端口19017,能同时接受100个请求
		httpserver.createContext("/", new MyResponseHandler());
		httpserver.setExecutor(null);
		System.out.println("数据库加载中.....");
		Manger = new SQLLiteManger(this);
		System.out.println("尝试链接并检查数据库中.....");
		Manger.SetupDb();
	}

	public void Stop() {
		if (httpserver != null) {
			httpserver.stop(0);
		}
	}
	public boolean AddGearByCommand(String string) {
		Gear gear = new Gear();
		try {
		gear.SetItemNum(Integer.parseInt(string.split(" ")[1]));
		gear.SetGrand(Integer.parseInt(string.split(" ")[2]));
		gear.SetItemFire(Integer.parseInt(string.split(" ")[3]));
		gear.SetItemHealth(Integer.parseInt(string.split(" ")[4]));
		gear.SetItemElectric(Integer.parseInt(string.split(" ")[5]));
		gear.SetArm(Integer.parseInt(string.split(" ")[6]));
		gear.SetMain1(string.split(" ")[7]);
		gear.SetMain2(string.split(" ")[8]);
		gear.SetSec1(string.split(" ")[9]);
		gear.SetSec2(string.split(" ")[10]);
		gear.SetSec3(string.split(" ")[11]);
		gear.SetNeeded(string.split(" ")[12]);
		}catch(Exception e) {
			return false;
		}

		try {
			Manger.addGear(gear);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean AddWeaponByCommand(String str) {
		Weapon weapon = new Weapon();
		try {
			weapon.SetItemNum(Integer.parseInt(str.split(" ")[1]));
			weapon.SetItemGrand(Integer.parseInt(str.split(" ")[2]));
			weapon.SetTalent1(Integer.parseInt(str.split(" ")[3]));
			weapon.SetTalent2(Integer.parseInt(str.split(" ")[4]));
			weapon.SetTalent3(Integer.parseInt(str.split(" ")[5]));
			weapon.SetNeeded(str.split(" ")[6]);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		try {
			Manger.addWeapon(weapon);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public void Start() {
		if (httpserver != null) {
			httpserver.start();
		}
	}

	public class MyResponseHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			String requestMethod = httpExchange.getRequestMethod();
			if (requestMethod.equalsIgnoreCase("GET")) {
				Headers responseHeaders = httpExchange.getResponseHeaders();
				responseHeaders.set("Content-Type", "text/html;charset=utf-8");
				URI url = httpExchange.getRequestURI();
				String response = url.getPath();
				try {
					response = Deal(response);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes("UTF-8").length);
				OutputStream responseBody = httpExchange.getResponseBody();
				OutputStreamWriter writer = new OutputStreamWriter(responseBody, "UTF-8");
				writer.write(response);
				writer.close();
				responseBody.close();
			} else if (requestMethod.equalsIgnoreCase("POST")) {
				Headers responseHeaders = httpExchange.getResponseHeaders();
				responseHeaders.set("Content-Type", "text/html;charset=utf-8");

				byte[] b = new byte[1024];
				httpExchange.getRequestBody().read(b);
				boolean results  = false;
				switch(DealPost(httpExchange.getRequestURI().getPath())) {
				case 0x10:
					try {
						Manger.CheckUser(new String(b));
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 0x11:
					try {
						UpdataItem(new String(b));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				String response = "200";
				if(results == false) {
					response="404";
				}
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes("UTF-8").length);
				OutputStream responseBody = httpExchange.getResponseBody();
				OutputStreamWriter writer = new OutputStreamWriter(responseBody, "UTF-8");

				writer.write(response);
				writer.close();
				responseBody.close();
			}

		}
		private void UpdataItem(String Json) throws SQLException {
			JSONObject object = JSONObject.fromObject(Json);
			if(object.getBoolean("Gear")) {
				DealGearJson(object.getJSONArray("data"));
			}else {
				DealWeaponJson(object.getJSONArray("data"));
			}
		}
		private void DealGearJson(JSONArray ja) throws SQLException {
			Gear gear = new Gear();
			gear.SetItemNum(ja.getInt(0));
			gear.SetItemGrand(ja.getInt(1));
			gear.SetItemFire(ja.getInt(2));
			gear.SetItemHealth(ja.getInt(3));
			gear.SetItemElectric(ja.getInt(4));
			gear.SetArm(ja.getInt(5));
			gear.SetMain1(ja.getString(6));
			gear.SetMain2(ja.getString(7));
			gear.SetSec1(ja.getString(8));
			gear.SetSec2(ja.getString(9));
			gear.SetSec3(ja.getString(10));
			gear.SetNeeded(ja.getString(11));
			Manger.addGear(gear);
		}
		private void DealWeaponJson(JSONArray ja) throws SQLException {
			Weapon weapon = new Weapon();
			weapon.SetItemNum(ja.getInt(0));
			weapon.SetItemGrand(ja.getInt(1));
			weapon.SetTalent1(ja.getInt(2));
			weapon.SetTalent2(ja.getInt(3));
			weapon.SetTalent3(ja.getInt(4));
			weapon.SetNeeded(ja.getString(5));
			Manger.addWeapon(weapon);
		}
		private final int DealPost(String Url) {
			String results = Url.substring(1);
			if (results.equals("user")) {
				return 0x10;
			}else if(results.equals("updateitem")) {
				return 0x11;
			}else {
				return 0x00;
			}
		}
		private String Deal(String Response) throws SQLException {
			String results = Response.substring(1);
			if (results.equals("wanteditem")) {
				results = Manger.SelectAllItemWanted();
				MenutoRefresh.FlagPutOut(DataFlag.visitorsPlus);
			}
			return results;
		}
	}

	@Override
	public void DataPutout(String Table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FlagPutOut(DataFlag Table) {
		// TODO Auto-generated method stub

	}

}
