# Star-LM200-Bluetooth-Printer-Cordova-worklight-plugin
Start LM200 bluetooth printer plugin
Description
  This plugin is compatabil with Start LM200 bluetooth printer device
  
Required Library/SDK for android (android 2.1 above)
  1. StarIOPort3.1.jar
  2. Put this jar into your lib folder
  3. Download it from  
  
      http://www.starmicronics.com/support/sdkdocumentation.aspx

Step to implement

1. main.js
   
          function printBt(){
      	
      	cordova.exec(fnSuccess, fnError, "StarBTPrint", "searchBT", ["Hi Roney .... Success Printing"]);
      }
      
      function prints(){
      	cordova.exec(fnSuccess, fnError, "StarBTPrint", "startPrnt", ["hhhhhhhhhhhhhhhhh"]);
      }
      
      function fnSuccess(result){
      	alert("Success= "+result);
      }
      function fnError(){
      	alert("Failure= "+result);
      }
     

2. config.xml
  
     ```xml
       <feature name="StarBTPrint">
            <param name="android-package" value="org.phonegap.plugin.StarBTPrint"/>
     </feature>'''
     
3.  
