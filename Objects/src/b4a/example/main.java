package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edt_detail = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edt_script = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edt_title = null;
public anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper _ac_language = null;
public b4a.example.starter _starter = null;
public b4a.example.httputils2service _httputils2service = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
String[] _languages = null;
anywheresoftware.b4a.phone.Phone _orientation = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"light_layout_v05\")";
mostCurrent._activity.LoadLayout("light_layout_v05",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="Dim languages () As String";
_languages = new String[(int) (0)];
java.util.Arrays.fill(_languages,"");
 //BA.debugLineNum = 33;BA.debugLine="languages = Array As String (\"python\", \"php\", \"go";
_languages = new String[]{"python","php","go","html","java"};
 //BA.debugLineNum = 34;BA.debugLine="ac_language.SetItems(languages)";
mostCurrent._ac_language.SetItems(processBA,anywheresoftware.b4a.keywords.Common.ArrayToList(_languages));
 //BA.debugLineNum = 36;BA.debugLine="Dim orientation As Phone";
_orientation = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 37;BA.debugLine="orientation.SetScreenOrientation(1)";
_orientation.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _btn_clear_click() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub btn_clear_Click";
 //BA.debugLineNum = 88;BA.debugLine="Clearify";
_clearify();
 //BA.debugLineNum = 89;BA.debugLine="ToastMessageShow(\"فیلد ها خالی شد\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("فیلد ها خالی شد"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _btn_github_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _phone = null;
 //BA.debugLineNum = 92;BA.debugLine="Sub btn_github_Click";
 //BA.debugLineNum = 93;BA.debugLine="Dim phone As PhoneIntents";
_phone = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 94;BA.debugLine="StartActivity(phone.OpenBrowser(\"https://github.c";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_phone.OpenBrowser("https://github.com/lnxpy/codehub-android")));
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static void  _btn_submit_click() throws Exception{
ResumableSub_btn_submit_Click rsub = new ResumableSub_btn_submit_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btn_submit_Click extends BA.ResumableSub {
public ResumableSub_btn_submit_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;
b4a.util.BClipboard _snippet_clip = null;
anywheresoftware.b4a.objects.collections.Map _data = null;
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _json = null;
b4a.example.httpjob _connection = null;
String _response = "";
anywheresoftware.b4a.objects.collections.JSONParser _jsn_response = null;
anywheresoftware.b4a.objects.collections.Map _dta = null;
String _link = "";
String _sid = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 41;BA.debugLine="If edt_title.Text == \"\" Or edt_script.Text == \"\"";
if (true) break;

case 1:
//if
this.state = 12;
if ((parent.mostCurrent._edt_title.getText()).equals("") || (parent.mostCurrent._edt_script.getText()).equals("") || (parent.mostCurrent._ac_language.getText()).equals("")) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 42;BA.debugLine="ToastMessageShow(\"تمام فیلد ها را پر کنید\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تمام فیلد ها را پر کنید"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 44;BA.debugLine="ToastMessageShow(\"درخواست شما در حال انجام است\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("درخواست شما در حال انجام است"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 45;BA.debugLine="Dim snippet_clip As BClipboard";
_snippet_clip = new b4a.util.BClipboard();
 //BA.debugLineNum = 51;BA.debugLine="Dim data As Map = CreateMap(\"title\": edt_title.T";
_data = new anywheresoftware.b4a.objects.collections.Map();
_data = anywheresoftware.b4a.keywords.Common.createMap(new Object[] {(Object)("title"),(Object)(parent.mostCurrent._edt_title.getText()),(Object)("detail"),(Object)(parent.mostCurrent._edt_detail.getText()),(Object)("script"),(Object)(parent.mostCurrent._edt_script.getText()),(Object)("language"),(Object)(parent.mostCurrent._ac_language.getText())});
 //BA.debugLineNum = 53;BA.debugLine="Dim json As JSONGenerator";
_json = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 54;BA.debugLine="json.Initialize(data)";
_json.Initialize(_data);
 //BA.debugLineNum = 56;BA.debugLine="Dim connection As HttpJob";
_connection = new b4a.example.httpjob();
 //BA.debugLineNum = 57;BA.debugLine="connection.Initialize(\"Connection\", Me)";
_connection._initialize /*String*/ (processBA,"Connection",main.getObject());
 //BA.debugLineNum = 58;BA.debugLine="connection.PostString(\"http://codehub.pythonanyw";
_connection._poststring /*String*/ ("http://codehub.pythonanywhere.com/api/v1/snippet/",_json.ToString());
 //BA.debugLineNum = 59;BA.debugLine="connection.GetRequest.SetContentType(\"applicatio";
_connection._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/json");
 //BA.debugLineNum = 60;BA.debugLine="Wait For (connection) JobDone(connection As Http";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_connection));
this.state = 13;
return;
case 13:
//C
this.state = 6;
_connection = (b4a.example.httpjob) result[0];
;
 //BA.debugLineNum = 61;BA.debugLine="If connection.Success Then";
if (true) break;

case 6:
//if
this.state = 11;
if (_connection._success /*boolean*/ ) { 
this.state = 8;
}else {
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 11;
 //BA.debugLineNum = 62;BA.debugLine="Dim response As String = connection.GetString";
_response = _connection._getstring /*String*/ ();
 //BA.debugLineNum = 63;BA.debugLine="Dim jsn_response As JSONParser";
_jsn_response = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 64;BA.debugLine="jsn_response.Initialize(response)";
_jsn_response.Initialize(_response);
 //BA.debugLineNum = 66;BA.debugLine="Dim dta As Map";
_dta = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 67;BA.debugLine="dta = jsn_response.NextObject";
_dta = _jsn_response.NextObject();
 //BA.debugLineNum = 69;BA.debugLine="Dim link As String = dta.Get(\"link\")";
_link = BA.ObjectToString(_dta.Get((Object)("link")));
 //BA.debugLineNum = 70;BA.debugLine="Dim SID As String = dta.Get(\"SID\")";
_sid = BA.ObjectToString(_dta.Get((Object)("SID")));
 //BA.debugLineNum = 72;BA.debugLine="snippet_clip.setText(link)";
_snippet_clip.setText(mostCurrent.activityBA,_link);
 //BA.debugLineNum = 73;BA.debugLine="ToastMessageShow(\"درخواست انجام شد. اسنیپ آیدی";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("درخواست انجام شد. اسنیپ آیدی "+_sid+" در کلیپبورد ذخیره شد"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 75;BA.debugLine="ToastMessageShow(\"خطا در برقراری ارتباط\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("خطا در برقراری ارتباط"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 11:
//C
this.state = 12;
;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(b4a.example.httpjob _connection) throws Exception{
}
public static String  _clearify() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub Clearify";
 //BA.debugLineNum = 81;BA.debugLine="edt_title.Text = \"\"";
mostCurrent._edt_title.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 82;BA.debugLine="edt_detail.Text = \"\"";
mostCurrent._edt_detail.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 83;BA.debugLine="edt_script.Text = \"\"";
mostCurrent._edt_script.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 84;BA.debugLine="ac_language.Text = \"\"";
mostCurrent._ac_language.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private edt_detail As EditText";
mostCurrent._edt_detail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private edt_script As EditText";
mostCurrent._edt_script = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private edt_title As EditText";
mostCurrent._edt_title = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private ac_language As AutoCompleteEditText";
mostCurrent._ac_language = new anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
