String msg = "";
int portIndex = 0;
int pin_green=9;
int pin_red=13;

void setup() {
  pinMode(pin_green, OUTPUT);//class 1 green
  pinMode(pin_red,OUTPUT); //class 2 red
  Serial.begin(9600);
}

void loop() {
  msg = readSerialInFromPC();
  if (msg.equals("9")) {
    Serial.println("D9 geldi");
    digitalWrite(pin_green,HIGH);
    digitalWrite(pin_red,LOW);
  }else if(msg.equals("8")){
    Serial.println("D13 geldi");
    digitalWrite(pin_red,HIGH);
    digitalWrite(pin_green,LOW);
  }else if(msg.equals("0")){
    Serial.println("stop geldi");
    digitalWrite(pin_red,LOW);
    digitalWrite(pin_green,LOW);
  }
}

String readSerialInFromPC() {
   String str = "";
   if (Serial.available() > 0) {
       char letter = Serial.read();
       str += letter;
   }
   return str;
}

