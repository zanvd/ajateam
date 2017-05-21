#include <TinkerKit.h>
#include <Bridge.h>
#include <Console.h>
#include <Process.h>

// Temperature sensor data.
#define THERM_SURR    "therm_surr"
#define THERM_PERS    "therm_pers"

// Light sensor data.
#define LIGHT_SURR    "light_surr"

// Light switch data.
#define LIGHT_SWITCH  "light_switch"

// Time data.
#define TIME_SLEEPY   "time_sleepy"
#define TIME_READY      "time_ready"

// LED's.
TKLed led_green(O0);
TKLed led_yellow(O1);
TKLed led_red(O2);

// Touch sensor
TKTouchSensor touch(I0);

// Button
TKButton button(I1);

// Light sensor.
TKLightSensor light(I2);

// Thermal sensor
TKThermistor thermal(I3);

// Potentiometers
TKPotentiometer pot_rot(I4);

// Tilt sensor
TKTiltSensor tilt(I5);

// Control variable for timing.
unsigned long timer;
unsigned long snooze;
unsigned long stopwatch;
boolean start_stopwatch;

// LED control.
int snooze_num = 0;

void setup() {
  // Set serial connection to 9600 bps
  Serial.begin(9600);
  // Initialize bridge.
  Bridge.begin();
  // Initialize console.
  Console.begin();

  led_green.brightness(0);
  led_yellow.brightness(0);
  led_red.brightness(0);

  timer = millis();
  snooze = millis();
  stopwatch = millis();
  start_stopwatch = false;
}

void loop() {
  // Retrieve mode.
  touch.readSwitch();
  button.readSwitch();
  // Wait for timer to finisih.
  if (millis() - timer > 5000) {
    timer = millis();
    // Check in which mode the device is running:
    // - sensors in surroundings
    // - sensors on person
    if (touch.readSwitch() == 0) {
      Serial.print("Surroundings: \n");
      // Read current temperature.
      float temperature = thermal.readCelsius();
      Serial.print("- temperature: ");
      Serial.println(temperature);
      post_request(THERM_SURR, temperature, "C");

      // Read current lighting.
      float lighting = light.read();
      Serial.print("- lighting: ");
      Serial.println(lighting);
      post_request(LIGHT_SURR, lighting, "");

      // Read rotation potentiometer.
      float light_switch = pot_rot.read();
      Serial.print("- light switch: ");
      Serial.println(light_switch);
      post_request(LIGHT_SWITCH, light_switch, "");

      // Turn on the alarm.
      if (button.readSwitch() == 1) {
        // Check snooze number.
        if (snooze_num == 0) {
          // Set starting snooze time.
          snooze = millis();
          // Turn on the green LED.
          led_green.brightness(1023);
          snooze_num++;
        }

        // Snooze for 10s.
        if (millis() - snooze > 10000 && snooze_num < 2) {
          // Switch to yellow light.
          led_green.brightness(0);
          led_yellow.brightness(1023);
          
          snooze_num++;
          // Start turning on the light switch.
          // ...
        } else if (millis() - snooze > 20000) {
          // Switch to red light.
          led_yellow.brightness(0);
          led_red.brightness(1023);

          snooze_num++;
        }
      } else if (snooze_num > 0) {
        // Start the morning routine stopwatch.
        if(!start_stopwatch) {
          snooze = millis() - snooze;
          // Format snooze time to minutes and seconds.
          float slp_min= (snooze / 1000) / 60;
          float slp_sec = (snooze / 1000) % 60;
          Serial.print("- snooze: ");
          Serial.println(snooze);
          Serial.print("- minutes: ");
          Serial.println(slp_min);
          Serial.print("- seconds: ");
          Serial.println(slp_sec);
          // Send data about snooze length.
          post_request_time(TIME_SLEEPY, slp_min, slp_sec, "Time user spends laying in bed after alarm");
          stopwatch = millis();
          led_green.brightness(0);
          led_yellow.brightness(0);
          led_red.brightness(0);
          start_stopwatch = true;
        }

        // Check for tilting the door handle.
        if (tilt.read() == 0) {
          stopwatch = millis() - stopwatch;
          // Format it to minutes and seconds.
          float minutes = (stopwatch / 1000) / 60;
          float seconds = (stopwatch / 1000) % 60;
          Serial.print("- stopwatch: ");
          Serial.println(stopwatch);
          Serial.print("- minutes: ");
          Serial.println(minutes);
          Serial.print("- seconds: ");
          Serial.println(seconds);
          post_request_time(TIME_READY, minutes, seconds, "Time neccessary for the user to get ready");
          snooze_num = 0;
          start_stopwatch = false;
        }
      }
    } else {
      Serial.print("Person: \n");
      // Read current temperature.
      float temperature = thermal.readCelsius();
      Serial.print("- temperature: ");
      Serial.println(temperature);
      post_request(THERM_PERS, temperature, "C");

      // Read heart rate.
      //Serial.print("Heart Rate: ");
      //Serial.println(light.read());

      // Read breathing rate.
      //Serial.print("Breathing Rate: ");
      //Serial.println(pot_lin.read());
    }
  }
}
