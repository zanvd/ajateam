#include <TinkerKit.h>

TKPotentiometer pot(I1);
TKLed led(O0);

int val;

void setup() {
  Serial.begin(9600);
}

void loop() {  
  val = pot.read();
  led.brightness(val);
  delay(20);
}
