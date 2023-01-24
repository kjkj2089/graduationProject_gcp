package com.google.cloud.examples.storage.snippets;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
//import static java.nio.charset.StandardCharsets.UTF_8;

import com.example.speech.SpeechToText;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


public class CreateBlob {
		public static void  Upload(File f) throws Exception {
		
		
		String bucketName = "stt-test-bucket3";
		String blobName = f.getName();

		Path path = Paths.get(f.getPath().toString());
		byte[] data = Files.readAllBytes(path);
		
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(bucketName, blobName);
		 BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("audio/wav").build();
		Blob blob = storage.create(blobInfo, data); 

		System.out.println("finish");
		}

}

