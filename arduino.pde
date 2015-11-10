#include<MeetAndroid.h>
#include<SoftwareSerial.h>

MeetAndroid bluetooth;

int led1 = 13;
int led2 = 5;
int led3 = 6;


int led1_status = 0; 
int led2_status = 0;
int led3_status = 0;
int led_status[2];

SoftwareSerial mySerial(10, 11);

void setup() {
  Serial.begin(9600);

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);


  bluetooth.registerFunction(Device1, 'A');
  bluetooth.registerFunction(Device2, 'B');
  bluetooth.registerFunction(Device3, 'C');
  bluetooth.registerFunction(DeviceStatus, 'D');
//  bluetooth.registerFunction(DeviceStatusArray, 'E');
}

void loop() {
  bluetooth.receive();

}

//Echoes the data
void Device1(byte flag, byte numOfValues) {
led1_status=bluetooth.getInt();
  digitalWrite(led1, led1_status);
  // Serial.print(bluetooth.getInt());
 bluetooth.send(led1_status);

}

void Device2(byte flag, byte numOfValues) {
 led2_status=bluetooth.getInt();
  digitalWrite(led1, led2_status);
}

void Device3(byte flag, byte numOfValues) {
  led3_status=bluetooth.getInt();
  digitalWrite(led1, led3_status);
}

//sends status back to android
void DeviceStatus(byte flag, byte numOfValues) {
  bluetooth.send(led1_status);
  bluetooth.send(led2_status);
  bluetooth.send(led3_status);
}
//  void DeviceStatusArray(byte flag, byte numOfValues) {
//  led_status[0] = led1_status;
//  led_status[1] = led2_status;
//  led_status[2] = led3_status;
//  bluetooth.send(led_status);
//  }
