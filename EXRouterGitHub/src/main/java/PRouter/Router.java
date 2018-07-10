package PRouter;

import java.util.Date;

public class Router {

	private String RouterName;
	private String IP;
	private String InterfaceIP;
	private String Version;
	private String InstallVersion;
	private Date date;
	private String ConfigRunning;

	public Router(String routerName, String iP, String interfaceIP, String version, String installVersion, Date date,
			String configRunning) {
		RouterName = routerName;
		IP = iP;
		InterfaceIP = interfaceIP;
		Version = version;
		InstallVersion = installVersion;
		this.date = date;
		ConfigRunning = configRunning;
	}

	public String getRouterName() {
		return RouterName;
	}

	public void setRouterName(String routerName) {
		RouterName = routerName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getInterfaceIP() {
		return InterfaceIP;
	}

	public void setInterfaceIP(String interfaceIP) {
		InterfaceIP = interfaceIP;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getInstallVersion() {
		return InstallVersion;
	}

	public void setInstallVersion(String installVersion) {
		InstallVersion = installVersion;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConfigRunning() {
		return ConfigRunning;
	}

	public void setConfigRunning(String configRunning) {
		ConfigRunning = configRunning;
	}

}
