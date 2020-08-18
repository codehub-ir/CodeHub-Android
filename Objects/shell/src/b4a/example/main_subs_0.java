package b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,33);
if (RapidSub.canDelegate("activity_create")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_create", _firsttime);}
RemoteObject _languages = null;
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(1);
 BA.debugLineNum = 35;BA.debugLine="Activity.LoadLayout(\"light_layout_v05\")";
Debug.ShouldStop(4);
main.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("light_layout_v05")),main.mostCurrent.activityBA);
 BA.debugLineNum = 36;BA.debugLine="Dim languages () As String";
Debug.ShouldStop(8);
_languages = RemoteObject.createNewArray ("String", new int[] {0}, new Object[]{});Debug.locals.put("languages", _languages);
 BA.debugLineNum = 37;BA.debugLine="languages = Array As String (\"python\", \"php\", \"go";
Debug.ShouldStop(16);
_languages = RemoteObject.createNewArray("String",new int[] {5},new Object[] {BA.ObjectToString("python"),BA.ObjectToString("php"),BA.ObjectToString("go"),BA.ObjectToString("html"),RemoteObject.createImmutable("java")});Debug.locals.put("languages", _languages);
 BA.debugLineNum = 38;BA.debugLine="ac_language.SetItems(languages)";
Debug.ShouldStop(32);
main.mostCurrent._ac_language.runVoidMethod ("SetItems",main.processBA,(Object)(main.mostCurrent.__c.runMethod(false, "ArrayToList", (Object)(_languages))));
 BA.debugLineNum = 39;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_pause(RemoteObject _userclosed) throws Exception{
try {
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,45);
if (RapidSub.canDelegate("activity_pause")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_pause", _userclosed);}
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 45;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(4096);
 BA.debugLineNum = 47;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_resume() throws Exception{
try {
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,41);
if (RapidSub.canDelegate("activity_resume")) { return b4a.example.main.remoteMe.runUserSub(false, "main","activity_resume");}
 BA.debugLineNum = 41;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(256);
 BA.debugLineNum = 43;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _btn_submit_click() throws Exception{
try {
		Debug.PushSubsStack("btn_submit_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,57);
if (RapidSub.canDelegate("btn_submit_click")) { return b4a.example.main.remoteMe.runUserSub(false, "main","btn_submit_click");}
RemoteObject _snippet_clip = RemoteObject.declareNull("b4a.util.BClipboard");
RemoteObject _snippet_link = RemoteObject.createImmutable("");
 BA.debugLineNum = 57;BA.debugLine="Sub btn_submit_Click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 58;BA.debugLine="If edt_title.Text == \"\" Or edt_script.Text == \"\"";
Debug.ShouldStop(33554432);
if (RemoteObject.solveBoolean("=",main.mostCurrent._edt_title.runMethod(true,"getText"),BA.ObjectToString("")) || RemoteObject.solveBoolean("=",main.mostCurrent._edt_script.runMethod(true,"getText"),BA.ObjectToString(""))) { 
 BA.debugLineNum = 59;BA.debugLine="ToastMessageShow(\"لطفا هر دو فیلد عنوان و اسکریپ";
Debug.ShouldStop(67108864);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence("لطفا هر دو فیلد عنوان و اسکریپت را پر کنید")),(Object)(main.mostCurrent.__c.getField(true,"False")));
 }else {
 BA.debugLineNum = 61;BA.debugLine="ToastMessageShow(\"درخواست شما در حال انجام است\",";
Debug.ShouldStop(268435456);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence("درخواست شما در حال انجام است")),(Object)(main.mostCurrent.__c.getField(true,"False")));
 BA.debugLineNum = 62;BA.debugLine="Dim snippet_clip As BClipboard";
Debug.ShouldStop(536870912);
_snippet_clip = RemoteObject.createNew ("b4a.util.BClipboard");Debug.locals.put("snippet_clip", _snippet_clip);
 BA.debugLineNum = 63;BA.debugLine="Dim snippet_link As String";
Debug.ShouldStop(1073741824);
_snippet_link = RemoteObject.createImmutable("");Debug.locals.put("snippet_link", _snippet_link);
 BA.debugLineNum = 64;BA.debugLine="If Send_Snippet <> \"\" Then";
Debug.ShouldStop(-2147483648);
if (RemoteObject.solveBoolean("!",_send_snippet(),BA.ObjectToString(""))) { 
 BA.debugLineNum = 65;BA.debugLine="snippet_link = Send_Snippet";
Debug.ShouldStop(1);
_snippet_link = _send_snippet();Debug.locals.put("snippet_link", _snippet_link);
 BA.debugLineNum = 66;BA.debugLine="snippet_clip.setText(snippet_link)";
Debug.ShouldStop(2);
_snippet_clip.runVoidMethod ("setText",main.mostCurrent.activityBA,(Object)(_snippet_link));
 }else {
 BA.debugLineNum = 68;BA.debugLine="ToastMessageShow(\"خطا در برقراری ارتباط\", False";
Debug.ShouldStop(8);
main.mostCurrent.__c.runVoidMethod ("ToastMessageShow",(Object)(BA.ObjectToCharSequence("خطا در برقراری ارتباط")),(Object)(main.mostCurrent.__c.getField(true,"False")));
 };
 };
 BA.debugLineNum = 71;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 25;BA.debugLine="Private btn_submit As Button";
main.mostCurrent._btn_submit = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 26;BA.debugLine="Private edt_detail As EditText";
main.mostCurrent._edt_detail = RemoteObject.createNew ("anywheresoftware.b4a.objects.EditTextWrapper");
 //BA.debugLineNum = 27;BA.debugLine="Private edt_script As EditText";
main.mostCurrent._edt_script = RemoteObject.createNew ("anywheresoftware.b4a.objects.EditTextWrapper");
 //BA.debugLineNum = 28;BA.debugLine="Private edt_title As EditText";
main.mostCurrent._edt_title = RemoteObject.createNew ("anywheresoftware.b4a.objects.EditTextWrapper");
 //BA.debugLineNum = 29;BA.debugLine="Private lstv_language As ListView";
main.mostCurrent._lstv_language = RemoteObject.createNew ("anywheresoftware.b4a.objects.ListViewWrapper");
 //BA.debugLineNum = 30;BA.debugLine="Private ac_language As AutoCompleteEditText";
main.mostCurrent._ac_language = RemoteObject.createNew ("anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper");
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main_subs_0._process_globals();
starter_subs_0._process_globals();
main.myClass = BA.getDeviceClass ("b4a.example.main");
starter.myClass = BA.getDeviceClass ("b4a.example.starter");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _send_snippet() throws Exception{
try {
		Debug.PushSubsStack("Send_Snippet (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,49);
if (RapidSub.canDelegate("send_snippet")) { return b4a.example.main.remoteMe.runUserSub(false, "main","send_snippet");}
RemoteObject _test = RemoteObject.createImmutable("");
 BA.debugLineNum = 49;BA.debugLine="Sub Send_Snippet";
Debug.ShouldStop(65536);
 BA.debugLineNum = 52;BA.debugLine="Dim test As String";
Debug.ShouldStop(524288);
_test = RemoteObject.createImmutable("");Debug.locals.put("test", _test);
 BA.debugLineNum = 53;BA.debugLine="test = \"\"";
Debug.ShouldStop(1048576);
_test = BA.ObjectToString("");Debug.locals.put("test", _test);
 BA.debugLineNum = 54;BA.debugLine="Return test";
Debug.ShouldStop(2097152);
if (true) return _test;
 BA.debugLineNum = 55;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}