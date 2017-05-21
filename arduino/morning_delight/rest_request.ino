String team_name = "ajateam1";
String url = "http://cloud.biview.com:17002";

/**
 * Send a POST request.
 * 
 * @param String key
 * @param float val
 * @param String unit
 */
void post_request(String key, float val, String unit) {
  Process proc;
  
  // Create the curl command.
  // Specify data:
  // - team name
  // - data name + value
  // - unit
  // Add url to where to post to.
  String cmd = "curl --data \"";
  cmd = cmd + "team_name=" + team_name;
  cmd = cmd + "&data_name=" + key;
  cmd = cmd + "&value=" + val;
  cmd = cmd + "&unit=" + unit;
  cmd = cmd + "\" " + url + "/add-data/";

  // Exceute the command.
  proc.runShellCommand(cmd);
  // Close process.
  proc.close();
}

/**
 * Send a POST request.
 * 
 * @param String key
 * @param float minutes
 * @param float seconds
 * @param String desc
 */
void post_request_time(String key, float minutes, float seconds, String desc) {
  Process proc;
  
  // Create the curl command.
  // Specify data:
  // - team name
  // - data name + value
  // - unit
  // Add url to where to post to.
  String cmd = "curl --data \"";
  cmd = cmd + "team_name=" + team_name;
  cmd = cmd + "&data_name=" + key;
  cmd = cmd + "&value=" + minutes;
  cmd = cmd + "&value_1=" + seconds;
  cmd = cmd + "&unit=mix";
  cmd = cmd + "&description=" + desc;
  cmd = cmd + "\" " + url + "/add-data/";

  // Exceute the command.
  proc.runShellCommand(cmd);
  // Close process.
  proc.close();
}

/**
 * Send a get request.
 * 
 * @param String key.
 * 
 * @return int
 */
void get_request(String key) {
  Process proc;
  
  // Create the curl command.
  // Specify data:
  // - team name
  // - data name
  // Add url to where to get from
  String cmd = "curl -sb -H \"Accept: application/json\" --data \"";
  cmd = cmd + "team_name=" + team_name;
  cmd = cmd + "&data_name=" + key;
  cmd = cmd + "\" " + url + "/get-data/";

  // Exceute the command.
  Serial.print("Return from GET: ");
  Serial.println(proc.runShellCommand(cmd));
  Serial.println(proc.read());
  // Close process.
  proc.close();
}

