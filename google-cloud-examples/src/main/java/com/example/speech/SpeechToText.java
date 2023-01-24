package com.example.speech;
/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// [START speech_quickstart]
// Imports the Google Cloud client library
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.WordInfo;

public class SpeechToText {	
	public static String address= System.getProperty("user.home") + "\\Documents\\VideoSearch\\";
	public static String filename;
	
	/**
	 * Performs non-blocking speech recognition on remote FLAC file and prints the transcription as
	 * well as word time offsets.
	 *
	 * @param gcsUri the path to the remote LINEAR16 audio file to transcribe.
	 */
	public static void asyncRecognizeWords(File f) throws Exception {
		filename=f.getName();
		String gcsUri = "gs://stt-test-bucket3/" + filename;
	  // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
	  try (SpeechClient speech = SpeechClient.create()) {
		/*String filename = f.getName();
		String gcsUri = "gs://stt-test-bucket/" + filename;
		String address = System.getProperty("user.home") + "\\Documents\\VideoSearch\\";  //���옣�븷 二쇱냼 吏��젙 */
		
		JSONObject objMain = new JSONObject();	
		JSONArray list = new JSONArray();
		JSONArray Senlist = new JSONArray();
		int i = 0;
		int j = 0;
		
		File jsonfile = new File(address + filename + ".json");
		File textfile = new File(address + filename +".txt");
		if (jsonfile.exists() && textfile.exists()) {
			System.out.println("파일이 이미 존재합니다");
			return;
		}
		
		FileWriter fw = new FileWriter(address + filename + ".json");
		BufferedWriter wr = new BufferedWriter(fw);
		
		FileWriter fw2 = new FileWriter(address + filename +".txt");
		BufferedWriter wr2= new BufferedWriter(fw2);
	    // Configure remote file request for Linear16
		
	    RecognitionConfig config =
	        RecognitionConfig.newBuilder()
	            .setEncoding(AudioEncoding.LINEAR16)
	            .setLanguageCode("en-US")
	            .setSampleRateHertz(16000)
	            .setModel("video")
	            .setAudioChannelCount(1)
	            .setEnableAutomaticPunctuation(true)
	            .setEnableWordTimeOffsets(true)
	            .build();
	    RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

	    // Use non-blocking call for getting file transcription
	    OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
	        speech.longRunningRecognizeAsync(config, audio);
	    while (!response.isDone()) {
	      System.out.println("Waiting for response...");
	      Thread.sleep(10000);
	    }
	    
	    
	    List<SpeechRecognitionResult> results = response.get().getResultsList();

	    for (SpeechRecognitionResult result : results) {

	      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	      System.out.printf("Transcription: %s\n", alternative.getTranscript());
	      wr2.write(alternative.getTranscript());
	      
	      String sentence = "";
	      int cnt = 0;
	      double startTimeSen[] = new double[50];
	      for (WordInfo senInfo : alternative.getWordsList()) {
	    	  JSONObject objSubSen = new JSONObject();  //臾몄옣 異붽��븯�옄
	    	  String word = senInfo.getWord();
	    	  if (word.contains(".") || word.contains("?") || word.contains("!") ) {
	    		sentence = sentence + word;
	    		  
	    		objSubSen.put("Sentence", sentence);

		        objSubSen.put("StartTime", startTimeSen[0]);
		        
		        Senlist.add(j++,objSubSen);
		        
		        sentence = "";
		        cnt = 0;
	    	  }
	    	  else {
	    		  sentence = sentence + word + " ";
	    		  
	    		  startTimeSen[cnt] = (double)senInfo.getStartTime().getSeconds();
	    		  startTimeSen[cnt++] += (double)senInfo.getStartTime().getNanos() / (double)1000000000;
	    	  }		  
	      }
	      
	      for (WordInfo wordInfo : alternative.getWordsList()) {
	    	JSONObject objSub = new JSONObject();
	    	String word = wordInfo.getWord();  //異붽��맂 臾몄옄 �젣嫄고븯湲�
	    	word = word.replace(",", "");
	    	word = word.replace(".", "");
	    	word = word.replace("?", "");
	        objSub.put("Word", word);
	        
	        double startTime = (double)wordInfo.getStartTime().getSeconds();
	        startTime += (double)wordInfo.getStartTime().getNanos() / (double)1000000000;
	        objSub.put("StartTime", startTime);
	        
	        double endTime = (double)wordInfo.getEndTime().getSeconds();
	        endTime += (double)wordInfo.getEndTime().getNanos() / (double)1000000000;
	        objSub.put("EndTime", endTime);
	        
	        list.add(i++, objSub);
	      }
	    }
        objMain.put("text", list);
        objMain.put("sentext",Senlist);
        wr.write(objMain.toJSONString());
        wr.flush();

	    wr.close();
	    wr2.close();
	  }
	  System.out.println("끝");
	}
}
