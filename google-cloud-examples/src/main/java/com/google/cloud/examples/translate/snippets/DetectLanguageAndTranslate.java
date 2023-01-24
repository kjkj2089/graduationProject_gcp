package com.google.cloud.examples.translate.snippets;
/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * EDITING INSTRUCTIONS
 * This file is referenced in READMEs and javadoc. Any change to this file should be reflected in
 * the project's READMEs and package-info.java.
 */


import com.example.speech.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * A snippet for Google Translation showing how to detect the language of some text and translate
 * some other text.
 */
public class DetectLanguageAndTranslate {


public static void Translate(String SelectLanguage,File f) throws IOException {
	String address= System.getProperty("user.home") + "\\Documents\\VideoSearch\\";
	String filename = f.getName();
    // Create a service object
    //
    // If no explicit credentials or API key are set, requests are authenticated using Application
    // Default Credentials if available; otherwise, using an API key from the GOOGLE_API_KEY
    // environment variable
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    // Text of an "unknown" language to detect and then translate into English

    	FileWriter fw=new FileWriter(address + filename +"translate"+ ".txt");
    	BufferedWriter wr=new BufferedWriter(fw);
    	FileWriter fw2 = new FileWriter(address + filename +"translate"+".json");
		BufferedWriter wr2= new BufferedWriter(fw2);
    	try {
        FileReader reader = new FileReader(address + filename + ".txt");
        BufferedReader br = new BufferedReader(reader);
        while(true) {
        final String mysteriousText =br.readLine();
        if (mysteriousText == null) 
            break;
        // Detect the language of the mysterious text
        Detection detection = translate.detect(mysteriousText);
        String detectedLanguage = detection.getLanguage();
        
        // Translate the mysterious text to English
        Translation translation =
            translate.translate(
                mysteriousText,
                TranslateOption.sourceLanguage(detectedLanguage),
                TranslateOption.targetLanguage(SelectLanguage)); //일본어 : ja , 영어 : en , 중국어 : zh-CN , 한국어 : ko
        wr.write(translation.getTranslatedText());  
        }
        br.close();
        wr.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    	try {
            FileReader reader2 = new FileReader(address + filename + ".json");
            BufferedReader br2 = new BufferedReader(reader2);
            while(true) {
            final String mysteriousText =br2.readLine();
            if (mysteriousText == null) 
                break;
            // Detect the language of the mysterious text
            Detection detection = translate.detect(mysteriousText);
            String detectedLanguage = detection.getLanguage();
            
            // Translate the mysterious text to English
            Translation translation =
                translate.translate(
                    mysteriousText,
                    TranslateOption.sourceLanguage(detectedLanguage),
                    TranslateOption.targetLanguage(SelectLanguage)); //일본어 : ja , 영어 : en , 중국어 : zh-CN , 한국어 : ko
            wr2.write(translation.getTranslatedText());  
            }
            br2.close();
            wr2.close();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

  }
}
