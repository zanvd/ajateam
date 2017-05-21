#include <TinkerKit.h>

TKJoystick joystick(I0, I1);

int x = 0;
int y = 0;

void setup() {
  Serial.begin(9600);
}

void loop() {
  x = joystick.readX();
  y = joystick.readY();
  // print the results to the serial monitor:
  Serial.print("Joystick X = " );                       
  Serial.print(x);   
  Serial.print("\t Joystick Y = " );                       
  Serial.println(y);     

  // wait 10 milliseconds before the next loop
  delay(10);
}
