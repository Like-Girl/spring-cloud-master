package cn.likegirl.rt.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc IP操作工具类
 * 
 * @author LikeGirl
 * @since 6/20/2017 16:37 PM
 */
public class IpUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);

	private static final String IP_PATTERN = "^(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\b";

	private static final String UNKNOWN = "unknown";

	private static final String LOOPBACK_ADDRESS = "127.0.0.1";

	private static final String UNKNOWN_ADDRESS = "0:0:0:0:0:0:0:1";
	private static final String LOCALHOST = "localhost";

	/**
	 * @Description: 获取请求中的ip地址：过了多级反向代理，获取的ip不是唯一的，二是包含中间代理层ip。
	 * 
	 * @return 可能有多个，例如：192.168.1.110， 192.168.1.120
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = "127.0.0.1";
		if (request != null) {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}

			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}

			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;

	}

	/**
	 * 
	 * @Description: 获取客户端请求中的真实的ip地址
	 * 
	 * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
	 * 但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址。而且，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，
	 * 而是一串ip值，例如：192.168.1.110， 192.168.1.120， 192.168.1.130， 192.168.1.100。其中第一个192.168.1.110才是用户真实的ip
	 */
	public static String getRealIp(HttpServletRequest request) {
		String ip = LOOPBACK_ADDRESS;
		if (request == null) {
			return ip;
		}

		ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (LOOPBACK_ADDRESS.equals(ip) || UNKNOWN_ADDRESS.equals(ip)) {
				// 根据网卡取本机配置的IP
				try {
					InetAddress inet = InetAddress.getLocalHost();
					ip = inet.getHostAddress();
				} catch (UnknownHostException e) {
					LOGGER.error("getRealIp occurs error, caused by: ", e);
				}
			}
		}

		//"***.***.***.***".length() = 15
		int ipLength = 15;
		String separator = ",";
		if (ip != null && ip.length() > ipLength) {
			if (ip.indexOf(separator) > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	public static String getServiceIp() {
		Enumeration<NetworkInterface> netInterfaces = null;
		String ipsStr = "";

		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				Pattern pattern = Pattern.compile(IP_PATTERN);
				while (ips.hasMoreElements()) {
					String ip = ips.nextElement().getHostAddress();
					Matcher matcher = pattern.matcher(ip);
					if (matcher.matches() && !"127.0.0.1".equals(ip)) {
						ipsStr = ip;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getServiceIp occurs error, caused by: ", e);
		}

		return ipsStr;
	}


	/***
	 * 获取客户端ip地址(可以穿透代理)
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getIpAddr1(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			// get first ip from proxy ip
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	/***
	 * 服务器是否是本机
	 * @param request
	 * @return
	 */
	public static boolean isLocalIp(HttpServletRequest request){
		String ip= getClientIpAddr(request);
		return ip.equals(LOOPBACK_ADDRESS)||ip.equals(LOCALHOST)||ip.equals(UNKNOWN_ADDRESS);
	}

}
