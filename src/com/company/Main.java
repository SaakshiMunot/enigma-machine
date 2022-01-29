package com.company;

import java.util.*;
import java.util.Scanner;

public class Main {

    // --------- Enigma Settings ------
    public static ArrayList<String> rotors = new ArrayList<>();
    public static String reflector = "UKW-B";
    public static String ringSettings = "AAA";
    public static String ringPositions = "AAA";
    public static String plugboard = "QK AY";

    // AT BS DE FM IR KN LZ OW PV XY
    // --------- Enigma Settings ------

    public static void main(String[] args) {
	// write your code here
        Scanner in = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // --------- Enigma Settings ------
        System.out.println("\n -------- ENIGMA ENCODER -------\n");

        System.out.println("REFLECTOR SETTINGS: ");
        System.out.print("Please input the reflector Type (UKW-B or UKW-C): ");
        reflector = in.nextLine();
        reflector = reflector.toUpperCase();

        System.out.println("\nPLUGBOARD SETTINGS: ");
        System.out.println("Ex. AT. Example: BD, ES, RT...");
        System.out.println("Any letter pairs divided by a space");
        System.out.print("Please input the plugboard settings in pairs: ");
        plugboard = in.nextLine();
        plugboard = plugboard.toUpperCase();

        System.out.println("\nRING SETTINGS: ");
        System.out.println("Ex. AAA - ZZZ. Example: CAD, GYE, ZAD...");
        System.out.println("Any three letter combination");
        System.out.print("Please input the ring settings: ");
        ringSettings = in.nextLine();
        ringSettings = ringSettings.toUpperCase();

        System.out.println("\nINITIAL POSITION OF ROTORS SETTINGS: ");
        System.out.println("Ex. AAA - ZZZ. Example: CAD, GYE, ZAD...");
        System.out.println("Any three letter combination");
        System.out.print("Please input the initial position settings: ");
        ringPositions = in.nextLine();
        ringPositions = ringPositions.toUpperCase();

        System.out.println("\nROTOR SETTINGS: ");
        System.out.println("Input: I, II, III, IV or V");
        System.out.print("Left Rotor: ");
        String leftRotor = in.nextLine();
        System.out.print("Middle Rotor: ");
        String midRotor = in.nextLine();
        System.out.print("Right Rotor: ");
        String rightRotor = in.nextLine();
        leftRotor = leftRotor.toUpperCase();
        midRotor = midRotor.toUpperCase();
        rightRotor = rightRotor.toUpperCase();

        switch (leftRotor) {
            case "I" -> rotors.add("I");
            case "II" -> rotors.add("II");
            case "III" -> rotors.add("III");
            case "IV" -> rotors.add("IV");
            case "V" -> rotors.add("V");
            default -> rotors.add("III");
        }
        switch (midRotor) {
            case "I" -> rotors.add("I");
            case "II" -> rotors.add("II");
            case "III" -> rotors.add("III");
            case "IV" -> rotors.add("IV");
            case "V" -> rotors.add("V");
            default -> rotors.add("II");
        }
        switch (rightRotor) {
            case "I" -> rotors.add("I");
            case "II" -> rotors.add("II");
            case "III" -> rotors.add("III");
            case "IV" -> rotors.add("IV");
            case "V" -> rotors.add("V");
            default -> rotors.add("I");
        }

        // --------- Enigma Settings ------
        System.out.println("\nINPUT MESSAGE: ");
        System.out.print("Enter text to encode or decode: ");
        String plainText = in.nextLine();

        String cipherText = encode(plainText);

        System.out.println("ENCRYPTED MESSAGE: ");
        System.out.println("Encoded Text: " + cipherText);
    }
    public static String caesarShift(String str, int amount){
        String output = "";
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            int code = c;
            if(code >= 65 && code <= 90){
                c = (char)(((code - 65 + amount) % 26) + 65);
                output = output + c;
            }
            if(c == ' '){
                output = output + " ";
            }
        }
        return output;
    }

    public static String encode(String plaintext){

        String rotor1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
        String rotor1Notch = "Q";
        String rotor2 = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
        String rotor2Notch = "E";
        String rotor3 = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
        String rotor3Notch = "V";
        String rotor4 = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
        String rotor4Notch = "J";
        String rotor5 = "VZBRGITYUPSDNHLXAWMJQOFECK";
        String rotor5Notch = "Z";

        HashMap<String, String> rotorDict = new HashMap<String, String>();
        rotorDict.put("I",rotor1);
        rotorDict.put("II",rotor2);
        rotorDict.put("III",rotor3);
        rotorDict.put("IV",rotor4);
        rotorDict.put("V",rotor5);

        HashMap<String, String> rotorNotchDict = new HashMap<String, String>();
        rotorNotchDict.put("I",rotor1Notch);
        rotorNotchDict.put("II",rotor2Notch);
        rotorNotchDict.put("III",rotor3Notch);
        rotorNotchDict.put("IV",rotor4Notch);
        rotorNotchDict.put("V",rotor5Notch);

        HashMap<String, String> reflectorB = new HashMap<String, String>();
        String refB = "A:Y, Y:A, B:R, R:B, C:U, U:C, D:H, H:D, E:Q, Q:E, F:S, S:F, G:L, L:G, I:P, P:I, J:X, X:J, K:N, N:K, M:O, O:M, T:Z, Z:T, V:W, W:V";
        reflectorB = stringtoMap(refB);
        HashMap<String, String> reflectorC = new HashMap<String, String>();
        String refC = "A:F, F:A, B:V, V:B, C:P, P:C, D:J, J:D, E:I, I:E, G:O, O:G, H:Y, Y:H, K:R, R:K, L:Z, Z:L, M:X, X:M, N:W, W:N, Q:T, T:Q, S:U, U:S";
        reflectorC = stringtoMap(refC);
//        System.out.println(reflectorC.get("A"));

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String rotorANotch = null;
        String rotorBNotch = null;
        String rotorCNotch = null;

        HashMap<String, String> reflectorDict = new HashMap<String, String>();
        if(reflector.equals("UKW-B")){
            reflectorDict = reflectorB;
        }else{
            reflectorDict = reflectorC;
        }

//        System.out.println(reflectorDict);

        // A = Left,  B = Mid,  C=Right

        String rotorA = rotorDict.get(rotors.get(0));
        String rotorB = rotorDict.get(rotors.get(1));
        String rotorC = rotorDict.get(rotors.get(2));
//        System.out.println(rotorA);
//        System.out.println(rotorB);
//        System.out.println(rotorC);
        rotorANotch = rotorNotchDict.get(rotors.get(0));
        rotorBNotch = rotorNotchDict.get(rotors.get(1));
        rotorCNotch = rotorNotchDict.get(rotors.get(2));
//        System.out.println(rotorANotch);
//        System.out.println(rotorBNotch);
//        System.out.println(rotorCNotch);
        String rotorALetter = Character.toString(ringPositions.charAt(0));
        String rotorBLetter = Character.toString(ringPositions.charAt(1));
        String rotorCLetter = Character.toString(ringPositions.charAt(2));

        char rotorASetting = ringSettings.charAt(0);
        int offsetASetting = alphabet.indexOf(rotorASetting);
        char rotorBSetting = ringSettings.charAt(1);
        int offsetBSetting = alphabet.indexOf(rotorBSetting);
        char rotorCSetting = ringSettings.charAt(2);
        int offsetCSetting = alphabet.indexOf(rotorCSetting);

        rotorA = caesarShift(rotorA, offsetASetting);
        rotorB = caesarShift(rotorB, offsetBSetting);
        rotorC = caesarShift(rotorC, offsetCSetting);
//        System.out.println(rotorA);
//        System.out.println(rotorB);
//        System.out.println(rotorC);

        if(offsetASetting > 0){
            rotorA = rotorA.substring(26-offsetASetting) + rotorA.substring(0,26-offsetASetting);
        }
        if(offsetBSetting > 0){
            rotorB = rotorB.substring(26-offsetBSetting) + rotorB.substring(0,26-offsetBSetting);
        }
        if(offsetCSetting > 0){
            rotorC = rotorC.substring(26-offsetCSetting) + rotorC.substring(0,26-offsetCSetting);
        }
//        System.out.println(rotorA);
//        System.out.println(rotorB);
//        System.out.println(rotorC);

        String ciphertext = "";

        //convertplugboard settings into a dictionary
        ArrayList<String> plugboardConnections = new ArrayList<String>();
        String[] connections = plugboard.split(" ");
        Collections.addAll(plugboardConnections,connections);
//        System.out.println(plugboardConnections);

        HashMap<String, String> plugboardDict = new HashMap<String, String>();
        for(String pair : plugboardConnections){
            if(pair.length() == 2){
                plugboardDict.put(Character.toString(pair.charAt(0)),Character.toString(pair.charAt(1)));
                plugboardDict.put(Character.toString(pair.charAt(1)),Character.toString(pair.charAt(0)));
            }
        }

//        System.out.println(plugboardDict);

        plaintext = plaintext.toUpperCase();
        for(int i = 0; i < plaintext.length(); i++){
            String letter = Character.toString(plaintext.charAt(i));
            String encryptedLetter = letter;

            if(alphabet.contains(letter)){
                 // Rotate Rotors - This happens as soon as a key is pressed, before encrypting the letter!
                boolean rotorTrigger = false;
                 // Third rotor rotates by 1 for every key being pressed
                if(rotorCLetter.equals(rotorCNotch)){
                    rotorTrigger = true;
                }
                rotorCLetter = Character.toString(alphabet.charAt((alphabet.indexOf(rotorCLetter) + 1) % 26));
//                System.out.println(rotorCLetter);

                //Check if rotorB needs to rotoate
                if(rotorTrigger){
                    rotorTrigger = false;
                    if(rotorBLetter.equals(rotorBNotch)){
                        rotorTrigger = true;
                    }
                    rotorBLetter = Character.toString(alphabet.charAt((alphabet.indexOf(rotorBLetter) + 1) % 26));

                    //Check if rotorA needs to rotate
                    if(rotorTrigger){
                        rotorTrigger = false;
                        rotorALetter = Character.toString(alphabet.charAt((alphabet.indexOf(rotorALetter) + 1) % 26));
                    }
                }else{
                    // check for double set sequence!
                    if(rotorBLetter.equals(rotorBNotch)){
                        rotorBLetter = Character.toString(alphabet.charAt((alphabet.indexOf(rotorBLetter) + 1) % 26));
                        rotorALetter = Character.toString(alphabet.charAt((alphabet.indexOf(rotorALetter) + 1) % 26));
                    }
                }

                // Implement plugboard encryption
                if(plugboardDict.containsKey(letter)){
                    if(plugboardDict.get(letter)!=""){
                        encryptedLetter = plugboardDict.get(letter);
                    }
                }

                //Rotors & Reflector Encryption
                int offsetA = alphabet.indexOf(rotorALetter);
                int offsetB = alphabet.indexOf(rotorBLetter);
                int offsetC = alphabet.indexOf(rotorCLetter);

                //Wheel 3 Encryption
                int pos = alphabet.indexOf(encryptedLetter);
//                System.out.println(pos);
                String let = Character.toString(rotorC.charAt((pos + offsetC) % 26));
//                System.out.println(let);
                pos = alphabet.indexOf(let);
//                System.out.println(pos);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetC + 26) % 26));
//                System.out.println(encryptedLetter);

                //Wheel 2 Encryption
                pos = alphabet.indexOf(encryptedLetter);
                let = Character.toString(rotorB.charAt((pos + offsetB) % 26));
                pos = alphabet.indexOf(let);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetB + 26) % 26));

                //Wheel 1 Encryption
                pos = alphabet.indexOf(encryptedLetter);
                let = Character.toString(rotorA.charAt((pos + offsetA) % 26));
                pos = alphabet.indexOf(let);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetA + 26) % 26));

                //Reflector Encryption
                if(reflectorDict.containsKey(encryptedLetter)){
                    if(reflectorDict.get(encryptedLetter)!=""){
                        encryptedLetter = reflectorDict.get(encryptedLetter);
//                        System.out.println(encryptedLetter);
                    }
                }

                //Back Through the Rotors
                //Wheel 1 Encryption
                pos = alphabet.indexOf(encryptedLetter);
                let = Character.toString(alphabet.charAt((pos + offsetA) % 26));
                pos = rotorA.indexOf(let);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetA + 26) % 26));

                // Wheel 2 Encryption
                pos = alphabet.indexOf(encryptedLetter);
                let = Character.toString(alphabet.charAt((pos + offsetB) % 26));
                pos = rotorB.indexOf(let);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetB + 26) % 26));

                // Wheel 3 Encryption
                pos = alphabet.indexOf(encryptedLetter);
                let = Character.toString(alphabet.charAt((pos + offsetC) % 26));
                pos = rotorC.indexOf(let);
                encryptedLetter = Character.toString(alphabet.charAt((pos - offsetC + 26) % 26));

                // Implement plugboard encryption!
                if(plugboardDict.containsKey(encryptedLetter)){
                    if(plugboardDict.get(encryptedLetter)!=""){
                        encryptedLetter = plugboardDict.get(encryptedLetter);
                    }
                }
            }

            ciphertext = ciphertext + encryptedLetter;
        }
        return  ciphertext;
    }

    public static HashMap<String,String> stringtoMap(String str){
        Map<String, String> hashMap = new HashMap<String, String>();
        String parts[] = str.split(",");
        for(String part : parts){
            String mapData[] = part.split(":");
            String pairA = mapData[0].trim();
            String pairB = mapData[1].trim();
            hashMap.put(pairA,pairB);
        }
        return (HashMap<String, String>) hashMap;
    }
}
