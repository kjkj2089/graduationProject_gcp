package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.example.speech.SpeechToText;
import com.google.cloud.examples.storage.snippets.CreateBlob;
import com.json.ReadJSONFile;
import com.json.TranslateReadJSONFile;
import com.google.cloud.examples.translate.snippets.*;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class MainController implements Initializable{
	
	public static File selectedFile;
	public static List<File> selectedFiles;
	public static File encodingFile;
	public static List<File> encodingFiles = new ArrayList();
	
	int fileFlag = 1;  // 영상 한개 선택 = 1, 여러개 선택 = 2
	
	String address = System.getProperty("user.home") + "\\Documents\\VideoSearch\\";
	String word="";
	double time;
	List<Object> timeStamp = new ArrayList<Object>();
	@FXML 
	private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	private Media media;
	@FXML
	private Label timeLabel;
	@FXML
	private StackPane stack;
	@FXML
	private Slider vol;
	@FXML
	private Slider seeSlider;
	@FXML
	private TextArea textarea;
	@FXML
	private TextArea transtextarea;
	@FXML
	private ComboBox<String> comboBox;
	ObservableList<String> list = FXCollections.observableArrayList("German", "Japanese","Chinese", "Korean");
	@FXML
	private ListView wordList;
	@FXML
	private ListView timeList;
	@FXML
	private TextField textfield;
	@FXML
	private ToggleButton toggle;
	@FXML
	private Popup popup;
	@Override
	public void initialize(URL location, ResourceBundle resources) {  // UI 초기화
		popup=new Popup();
		textarea=new TextArea("");
		transtextarea=new TextArea("");
		String path = new File("resources/BlackScreen.mp4").getAbsolutePath();
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		//DoubleProperty width=mediaView.fitWidthProperty();
		//DoubleProperty height=mediaView.fitHeightProperty();
		mediaView.fitWidthProperty().bind(stack.widthProperty()); 
		mediaView.fitHeightProperty().bind(stack.heightProperty());
		//볼륨 슬라이더
		vol.setValue(mediaPlayer.getVolume()*100);
		vol.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observalbe) {
				mediaPlayer.setVolume(vol.getValue()/100);
			}
		});
		
		textarea.setWrapText(true);  //텍스트창 자동 줄바꿈
		transtextarea.setWrapText(true);
		

		comboBox.setItems(list);
		wordList.setOnMouseClicked(new EventHandler<MouseEvent>() {  //단어 선택시 타임스탬프 출력
			@Override
			public void handle(MouseEvent event) {
				word = wordList.getItems().get(wordList.getSelectionModel().getSelectedIndex()).toString();
				//System.out.println(word);
				if (fileFlag == 1) {
					timeStamp = ReadJSONFile.getTimeStamp(encodingFile,word);
				}
				else if (fileFlag == 2) {
					int num = 0;
					for(File file : encodingFiles) {
						selectedFile = selectedFiles.get(num++);
						encodingFile = file;
						timeStamp = ReadJSONFile.getTimeStamp(file,word);
						if(timeStamp.size() > 1)
							break;
					}
				}
				showTimeStamp();
			}
		});
		
		timeList.setOnMouseClicked(new EventHandler<MouseEvent>() {  //시간 선택시 동영상 이동
			@Override
			public void handle(MouseEvent event) {
				if (fileFlag == 1) {
					time = (double) timeStamp.get(timeList.getSelectionModel().getSelectedIndex());
					seeSlider.setValue(time);
					mediaPlayer.seek(Duration.seconds(seeSlider.getValue()));
				}
				else if (fileFlag == 2) {
					fileload();
					time = (double) timeStamp.get(timeList.getSelectionModel().getSelectedIndex());
					seeSlider.setValue(time);
					mediaPlayer.seek(Duration.seconds(seeSlider.getValue()));
				}
			}
		});
	}
	
	 public void handlePopup(ActionEvent event){
	        if(popup.isShowing()){
	            popup.hide();
	        }
	        else {
	            final Window window = toggle.getScene().getWindow();
	            popup.setWidth(100);
	            popup.setHeight(300);
	 
	            final double x = window.getX()
	                    + toggle.localToScene(0, 0).getX()
	                    + toggle.getScene().getX()
	                    ;
	            final double y = window.getY()
	                    + toggle.localToScene(0, 0).getY()
	                    + toggle.getScene().getY()
	                    + toggle.getHeight();
	 
	            popup.getContent().clear();
	            popup.getContent().addAll(textarea);
	            popup.show(window, x, y);
	        }
	 }
	 public void handlePopup2(ActionEvent event){
	        if(popup.isShowing()){
	            popup.hide();
	        }
	        else {
	            final Window window = comboBox.getScene().getWindow();
	            popup.setWidth(100);
	            popup.setHeight(300);
	 
	            final double x = window.getX()
	                    + comboBox.localToScene(0, 0).getX()
	                    + comboBox.getScene().getX()
	                    ;
	            final double y = window.getY()
	                    + comboBox.localToScene(0, 0).getY()
	                    + comboBox.getScene().getY()
	                    + comboBox.getHeight();
	 
	            popup.getContent().clear();
	            popup.getContent().addAll(transtextarea);
	            popup.show(window, x, y);
	        }
	 }
	 
		public void fileload() {
			String path = new File(selectedFile.getPath().toString()).getAbsolutePath();
			media = new Media(new File(path).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			/*
			DoubleProperty width = mediaView.fitWidthProperty();
			DoubleProperty height = mediaView.fitHeightProperty();
			width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
			*/
			
			// 재생 슬라이더
			mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
				@Override
				public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
					seeSlider.setValue(newValue.toSeconds());
				}
			});
			seeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					mediaPlayer.seek(Duration.seconds(seeSlider.getValue()));
				}
			});
		} 
	 
	public void fileopen() throws Exception {  //fileopen 버튼		
		mediaPlayer.stop();
		fileFlag = 1;  // 영상 한개 선택 = 1
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("동영상 파일", "*.wav" , "*.mp4"));  //확장자 필터 설정하기 
		selectedFile = fc.showOpenDialog(null);
		convert(selectedFile);  //확장자 변경
		CreateBlob.Upload(encodingFile);  //파일 업로드
		SpeechToText.asyncRecognizeWords(encodingFile);  //STT실행 
		fileload();

		mediaPlayer.setOnReady(() -> {
		  
			timeLabel.textProperty().bind(
		        Bindings.createStringBinding(() -> {
		        		Duration totaltime=mediaPlayer.getTotalDuration();
		                Duration time = mediaPlayer.getCurrentTime();
		                return String.format("%3d:%02d:%04.1f"+"/"+"%4d:%02d:%04.1f",
		                    (int) time.toHours(),
		                    (int) time.toMinutes() % 60,
		                    time.toSeconds() % 60,
		                		(int) totaltime.toHours(),
			                    (int) totaltime.toMinutes() % 60,
			                    totaltime.toSeconds() % 60);
		            },
		            mediaPlayer.currentTimeProperty()));
		    
				seeSlider.maxProperty().bind(
			    Bindings.createDoubleBinding(
			        () -> mediaPlayer.getTotalDuration().toSeconds(),
			        mediaPlayer.totalDurationProperty()));

		mediaPlayer.setAutoPlay(true);
		
		});
	}
	
	public void multiplefileopen()throws Exception {  //여러개의 파일 열기
		fileFlag = 2;  // 영상 여러개 선택 = 2
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("동영상 파일", "*.mp4"));  //확장자 필터 설정하기
		selectedFiles = fc.showOpenMultipleDialog(null);
		
		int m = 0;
		for(File file : selectedFiles) {
			selectedFile=file;
			convert(selectedFile);
			CreateBlob.Upload(encodingFile);  //파일 업로드
			SpeechToText.asyncRecognizeWords(encodingFile);  //STT실행 
			encodingFiles.add(encodingFile);
		}
	}
	
	public void mediaplay(ActionEvent event) {  //동영상 실행
		mediaPlayer.play();
	}
	
	public void mediapause(ActionEvent event) {  //동영상 일시정지
		mediaPlayer.pause();
	}
	
	public void mediastop(ActionEvent event) {  //동영상 중지
		mediaPlayer.seek(mediaPlayer.getStopTime());
		mediaPlayer.stop();
	}
	
	
	public void textload() throws Exception {  //텍스트 불러오기
		if(toggle.isSelected()==false) {
		textarea.setOpacity(1);
		File f = new File(address + encodingFile.getName() + ".txt");
		FileReader fileReader = new FileReader(f);
		BufferedReader reader = new BufferedReader(fileReader);
		
		String line = null;
		while((line = reader.readLine()) != null) {
			System.out.println(line);
			textarea.setText(line);
		}
		reader.close();
		handlePopup(null);
		}
		/*if(toggle.isSelected()==true) {
			textarea.setText(null);
			textarea.setOpacity(0);
		} */
	}
	public void translate(ActionEvent event) throws IOException {  //번역
		transtextarea.setOpacity(1);
		switch(comboBox.getValue()) {
		case "German":
			DetectLanguageAndTranslate.Translate("de",encodingFile);
			break;
		case "Japanese":
			DetectLanguageAndTranslate.Translate("ja",encodingFile);
			break;
		case "Chinese" :
			DetectLanguageAndTranslate.Translate("zh-CN",encodingFile);
			break;
		case "Korean" :
			DetectLanguageAndTranslate.Translate("ko",encodingFile);	
			break;
		}
		File f = new File(address + encodingFile.getName() + "translate" + ".txt");
		FileReader fileReader = new FileReader(f);
		BufferedReader reader = new BufferedReader(fileReader);
		
		String line = null;
		while((line = reader.readLine()) != null) {
			System.out.println(line);
			transtextarea.setText(line);
		}
		reader.close();
		handlePopup2(null);
	}
	public void addword(ActionEvent event) {  //단어 추가
		String word = textfield.getText();
		wordList.getItems().addAll(word);
	}
	
	public void delword(ActionEvent event) {  //단어 삭제		
		wordList.getItems().remove(wordList.getSelectionModel().getSelectedIndex());
	}
	
	public void showTimeStamp() {  //타임스탬프 출력하기
		timeList.getItems().clear();  //리스트 초기화
		for(int i=0; i<timeStamp.size() ; i++) {  //타임스탬프 출력
			timeList.getItems().addAll(timeStamp.get(i));
		}
	}
	public static String convert(File f) throws IOException { // 인코딩 
		String ffmpegPath = "C:\\Program Files (x86)\\ffmpeg-4.1.3-win64-static\\bin/ffmpeg";    //예) /work/ffmpeg
		String fOriginal = f.getPath().toString();  //실시간으로 업로드되는 파일 
		String encoded = fOriginal.replace("mp4", "wav"); // mp4 확장자를 wav로 인코딩
		encodingFile = new File(encoded);
		String fResult = encodingFile.getPath().toString();      // 인코딩하고 저장 할 파일위치
		String[] cmdLine = new String[]{ffmpegPath,
														"-i",                           // 변환시킬 파일위치

		                                               fOriginal,    
		                                               "-ac", //  채널
		                                               
		                                               "1",

		                                               "-ar",

		                                               "16000",                

		                                               "-ab",

		                                               "32",                      

		                                               "-s",

		                                               "1280x720",     //화면 사이즈

		                                               "-b",

		                                               "768k",          //비트레이트

		                                               "-r",

		                                               "24",           //영상 프레임

		                                               "-y",

		                                               "-f",

		                                               "wav",            // wav파일 형태로 출력

		                                               fResult};  // 저장하는 위치
		 
		// 프로세스 속성을 관리하는 ProcessBuilder 생성.
		ProcessBuilder pb = new ProcessBuilder(cmdLine);
		pb.redirectErrorStream(true);
		Process p = null;
		 try { 
		        // 프로세스 작업을 실행
		        p = pb.start();
		} catch (Exception e) {         
		     e.printStackTrace();

		     p.destroy();
		     return null;
		}

		exhaustInputStream(p.getInputStream());   // 자식 프로세스에서 발생되는 inputstrem를 소비시켜야합니다.

		  try {

		        // p의 자식 프로세스의 작업이 완료될 동안 p를 대기시킴
		        p.waitFor();

		 } catch (InterruptedException e) {

		         p.destroy();

		 }


		// 정상 종료가 되지 않았을 경우

		 if (p.exitValue() != 0) {

		       System.out.println("변환 중 에러 발생");

		       return null;

		 }

		   // 변환을 하는 중 에러가 발생하여 파일의 크기가 0일 경우

		  if (fResult.length() == 0) {

		        System.out.println("변환된 파일의 사이즈가 0임");

		         return null;

		  }

		  p.destroy();
		System.out.println("인코딩 끝");
		return null;

		}

		private static void exhaustInputStream(final InputStream is) {
		    // InputStream.read() 에서 블럭상태에 빠지기 때문에 따로 쓰레드를 구현하여 스트림을 소비한다
		         try {

		                BufferedReader br = new BufferedReader(new InputStreamReader(is));

		                String cmd = null;

		                while((cmd = br.readLine()) != null) { // 읽어들일 라인이 없을때까지 계속 반복

		                   //System.out.println(cmd);

		                }

		                br.close();

		             } catch(IOException e) {

		                e.printStackTrace();

		             }

		   }
	
	
}