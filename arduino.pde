#include<MeetAndroid.h>
#include<SoftwareSerial.h>

MeetAndroid bluetooth;

int led1 = 13;
int led2 = 5;

SoftwareSerial mySerial(10, 11);

void setup() {
  Serial.begin(9600);

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);


  bluetooth.registerFunction(ControlLed1, 'A');
  bluetooth.registerFunction(ControlLed2, 'B');

}

void loop() {
  bluetooth.receive();

}

void ControlLed1(byte flag, byte numOfValues) {
  digitalWrite(led1, bluetooth.getInt());
  // Serial.print(bluetooth.getInt());
 bluetooth.send(bluetooth.getInt());

}

void ControlLed2(byte flag, byte numOfValues) {
  digitalWrite(led2, bluetooth.getInt());

}
