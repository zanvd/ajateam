#include <Bridge.h>
#include <Console.h>
#include <Process.h>

String team_name = "ajateam";
String url = "http://cloud.biview.com:17002";

int i = 1;

void setup() {
  // Set serial connection to 9600 bps
  Serial.begin(9600);
  // Initialize bridge.
  Bridge.begin();
  // Initialize console.
  Console.begin();
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LED_BUILTIN, OUTPUT);
}

/**
 * Send a POST request.
 */
void postRequest(int val) {
  Process proc;
  
  // Create the curl command.
  // Specify data:
  // - team name
  // - data name + value
  // Add url to where to post to.
  String cmd = "curl --data \"";
  cmd = cmd + "team_name=" + team_name;
  cmd = cmd + "&data_name=test_data";
  cmd = cmd + "&value=" + val;
  cmd = cmd + "&unit=test";
  cmd = cmd + "\" " + url + "/add-data/";

  // Exceute the command.
  proc.runShellCommand(cmd);
  // Close process.
  proc.close();
}

void loop() {
  for(;i < 5;i++) {
    postRequest(i);
  }
}
