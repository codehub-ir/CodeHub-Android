B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.9
@EndOfDesignText@
#Region  Service Attributes
    #StartAtBoot: False
    #ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

	Public PhoneEvent1 As PhoneEvents

End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.


	'https://www.b4x.com/android/help/phone.html#phoneevents
	PhoneEvent1.Initialize("PhoneEvent")

End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub

Sub PhoneEvent_ConnectivityChanged(NetworkType As String, State As String, Intent As Intent)
    
	'NetworkType - WIFI or MOBILE.
	'State - One of the following values: CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN.
    
	If State = "CONNECTED" Then
	Else
		ToastMessageShow("Keine Netzwerkverbindung",True)
		Log(NetworkType)
		Log(State)
	End If
    
End Sub