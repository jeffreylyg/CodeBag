package com.codebag.bag;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import dalvik.system.DexFile;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class CodeBag extends Application {

	public static final String ROOT_DIR = "com.codebag.code.mycode";
	public static final int FILE = 0;
	public static final int DIR = 1;
	private boolean mHashInit = false;
	private Node mRootNode = new Node(ROOT_DIR, Node.DIR);
	private LinkedList<MainActivity> mActContainer = new LinkedList<MainActivity>();
	
	
	@Override
	public void onCreate() {
//		Debug.startMethodTracing("mytrace"); 
	}

	public void init() {
		
		if(mHashInit) {
			return;
		}
		
		String pkgName = getPackageName();
		String apkDir = null;
		try {
			apkDir = getPackageManager().getApplicationInfo(pkgName, 0).sourceDir;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		Log.i("~peter", "apkDir = " + apkDir);
		Log.i("~peter", "pkgName = " + pkgName);

		DexFile dexFile = null;
		try {
			String prefix = ROOT_DIR;
			int startLen = prefix.length() + 1;
			dexFile = new DexFile(apkDir);
			Enumeration<String> apkClassNames = dexFile.entries();
			while (apkClassNames.hasMoreElements()) {
				String className = apkClassNames.nextElement();
				
				if (className.endsWith(".R")) {
	                continue;
	            }
				
				if(className != null && className.startsWith(prefix) & className.indexOf("$") < 0) {
					
					String fileName = className.substring(startLen);
					String[] strs = fileName.split("\\.");
					
					loadCodeBagNode(className, strs, 0, mRootNode);
					
					Log.i("~peter", "fileName = " + fileName);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//加入appDemo节点
		Node appNode = getAppDemoNode();
		if(appNode != null) {
			mRootNode.mSubNodeList.add(appNode);
		}
		
		printNode(mRootNode);
		
		mHashInit = true;
	}
	
	public void addActivity(MainActivity act) {
		mActContainer.add(act);
	}
	
	public void removeActivity(MainActivity act) {
		mActContainer.remove(act);
	}
	
	public void exit() {
		for(MainActivity act : mActContainer) {
			act.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public Node getRootNode() {
		return mRootNode;
	}
	
	public Node getAppDemoNode() {
		Node appNode = new Node();
		appNode.mType = Node.APP;
		appNode.mName = "other app demo";
		appNode.mSubNodeList = new ArrayList<CodeBag.Node>();		
		
    	PackageManager pm = getPackageManager();
		List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		for(ApplicationInfo info : appList) {
		    try {
				ApplicationInfo appInfo = pm.getApplicationInfo(info.packageName, PackageManager.GET_META_DATA);
				if(appInfo.metaData != null) {
					
					String type = appInfo.metaData.getString("appType");
					
					if("codebag_appdemo".equals(type)) {
						Node node = new Node();
						node.mFullName = appInfo.packageName;
						appNode.mSubNodeList.add(node);
						Log.i("packageName", info.packageName );
					}
				}
				
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(appNode.mSubNodeList.size() == 0) {
			return null;
		}
		return appNode;
	}
	
	private void printNode(Node node) {
		Log.i("!peter", "name=" + node.mName);
		Log.i("!peter", "fullname=" + node.mFullName);
		
		if(node.mSubNodeList != null) {
	
			for(int i = 0; i < node.mSubNodeList.size(); i++) {
				printNode(node.mSubNodeList.get(i));
			}
		}
	}
	
	/**
	 * @param className     class全路径名
	 * @param strs          class全路径名除去根路径，剩下的字符以“.”为划分记号，划分成的数组
	 * @param index		       游标在strs数组中的位置
	 * @param currentNode   当前节点（作为父节点）
	 */
	private void loadCodeBagNode(String className, String[] strs, int index, Node currentNode) {
		
		if(index > strs.length - 1) {
			return;
		}
		String nodeName = strs[index];
		if(index == strs.length - 1) {//数组的最后一个元素为class
			getSubNode(nodeName, Node.CLASS, className, currentNode);
		}else {//数组中其他元素为目录
			Node subNode = getSubNode(nodeName, Node.DIR, null, currentNode);
			index++;
			loadCodeBagNode(className, strs, index, subNode);
		}
		
	}
	
	/**
	 * @param nodeName    子节点名字（游标所在数组的元素名字）--- 是区分各个子节点的关键字
	 * @param type        子节点类型（目录/类）
	 * @param fullName    子节点全名（目录一般为null，类才有）
	 * @param currentNode 父节点
	 * @return
	 */
	private Node getSubNode(String nodeName, int type, String fullName, Node currentNode) {
		if(currentNode.mSubNodeList == null) {//创建子节点列表
			currentNode.mSubNodeList = new ArrayList<Node>();
		}else {
			for(Node n : currentNode.mSubNodeList) {//父节点有子节点列表，则遍历一下
				if(n.mName.equals(nodeName)) {
					return n;
				}
			}
		}
		return createSubNode(nodeName, type, fullName, currentNode);
	}
	
	private Node createSubNode(String nodeName, int type, String fullName, Node currentNode) {
		Node node = new Node();
		node.mName = nodeName;
		node.mFullName = fullName;
		node.mType = type;
		currentNode.mSubNodeList.add(node);
		return node;
	}
	
	public static class Node implements Serializable{
		private static final long serialVersionUID = 7667945539869687742L;
		public static final int DIR = 0;
		public static final int CLASS = 1;
		public static final int APP = 3;
		
		public int mType = -1;
		public String mName;
		public String mFullName;//类节点才有
		public ArrayList<Node> mSubNodeList;
		
		public Node() {
		}
		
		public Node(String name, int type) {
			mName = name;
			mType = type;
		}
		
	}

}
