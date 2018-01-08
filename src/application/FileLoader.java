package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import application.dialogs.DialogsUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.util.regex.*;

public class FileLoader implements Initializable {

	private Stage stage;
	public ArrayList<String> odczyt = new ArrayList<String>();
	public ArrayList<String> title = new ArrayList<String>();
	public ArrayList<String> tags = new ArrayList<String>();
	public static int newLineInd;
	RabinKarp_Algorithm rabinKarp_Algorithm = new RabinKarp_Algorithm();
	KnuthMorrisPrattAalgorithm knuthMorrisPrattAalgorithm = new KnuthMorrisPrattAalgorithm();
	Method3 method3 = new Method3(); 
	public int arrayStartLine[] = new int[20];
public ArrayList<Integer> TagsId= new ArrayList<Integer>();
	public int arrayLenghtTitle[] = new int[21];
	//public int TagsId[] = new int[21];
	
	public File plik;
	@FXML
	private Button btn;
	@FXML
	private MenuItem menu_zamknij;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField wzor;
	@FXML
	private RadioButton RKrb;
	@FXML
	private RadioButton KNPrb;
	@FXML
	private TextArea titleArea;

	@FXML
	public void selectAlgo() {
		
		titleArea.clear();
		tag("([\\:])", wzor.getText());
		
		
		// int x = TagsId.length;
		//tags.add(wzor.getText(TagsId[0],TagsId[1]));
		
		int p = 0;
//todo
//wzor to tags<1,2,3....> wywolane tags.size razy
		
//jesli n tagow jest w tej samej lini to zwracam film
		// 
		//System.out.println("rozmiar tags "+tags.size());
		//for(int j=0;j<tags.size();j++) {
		
		if (RKrb.isSelected()) {
			int z =TagsId.size();
			if(z>1) {
			tags.add(wzor.getText(TagsId.get(0), TagsId.get(1)));
			
			for(int k =1 ;k<TagsId.size()-1;k++) {
				if(TagsId.get(k+1)!=0) {
			tags.add(wzor.getText(TagsId.get(k)+1, TagsId.get(k+1)));}
				
			}
			}
			
			tags.add(wzor.getText(TagsId.get(z-1)+1, wzor.getLength()));
			System.out.println("RK");
			title.clear();
			for(int j =0;j<tags.size();j++) {
			rabinKarp_Algorithm.RK_algo(odczyt.toString(), tags.get(j));//tags.get(j)

			// KNPrb.setSelected(false);
			for (int i = 0; i <= (rabinKarp_Algorithm.getPocz().size() - 1); i++) {

				p = (int) rabinKarp_Algorithm.getPocz().get(i);
				highlightText(p);
			//	System.out.println(p+"nis");
				

			}
			System.out.println(tags);
			//tags.clear();
			
			System.out.println("po "+tags);
			
			rabinKarp_Algorithm.getPocz().clear();}
		} else if(KNPrb.isSelected()){
			System.out.println("KNP");
			knuthMorrisPrattAalgorithm.search(wzor.getText().toString(), odczyt.toString());//tags.get(j)
			for (int i = 0; i <= (knuthMorrisPrattAalgorithm.getPocz().size() - 1); i++) {

				p = (int) knuthMorrisPrattAalgorithm.getPocz().get(i);
				highlightText(p);
			}
			knuthMorrisPrattAalgorithm.getPocz().clear();
		}
		else {
			titleArea.clear();
			System.out.println("metoda3");
			method3.szukaj(wzor.getText().toString(),odczyt.toString() );
			for (int i = 0; i <= (method3.getPocz().size() - 1); i++) {

				p = (int) method3.getPocz().get(i);
				System.out.println(p);
				highlightText(p+1);
			}
		}
		
		//}
		
		loadTitle();
		tags.clear();
		TagsId.clear();
	}

	

	@FXML
	private void otworzPlikAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Otwórz Plik");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki TXT", "*.txt"));
		plik = fileChooser.showOpenDialog(stage);

		if (plik != null) {
			// Wyswietlenie ścieżki do pliku.
			System.out.println("Plik: " + plik.getAbsolutePath());

		}
		read(plik);

	}

	public void read(File file) {
		BufferedReader br = null;
		Path sciezkaDoPliku = Paths.get(file.getAbsolutePath());

		try {
			// br = new BufferedReader(new
			// FileReader("E:\\projInz2\\StringSearching-application\\new2.txt"));
			String line;

			odczyt = (ArrayList) Files.readAllLines(sciezkaDoPliku);

			// System.out.println("indeks" + rabinKarp_Algorithm.getI());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	@FXML
	public void setText(ActionEvent actionEvent) {

		System.out.println(odczyt);

		String kaczka = odczyt.toString();
		String text = textArea.getText();

		textArea.setText(kaczka);
		regexChecker("([\\,])", textArea.getText());

		textArea.setWrapText(true);

	}

	// @FXML
	public void highlightText(int pocz) {
		// if dosn't exist key-word
		// knuthMorrisPrattAalgorithm.search(wzor.getText().toString(),odczyt.toString());
		
		
		int start, stop;
		// start = (int)rabinKarp_Algorithm.getPocz().get(0);
		start = pocz;

		stop = wzor.getText().length();
		stop = start + stop;
		//
		if (odczyt.toString().length() < start) {
			System.out.println("nie ma");
		} else {
			// for(int k=0;k<arrayStartLine.length;k++)
			//textArea.selectRange(start, stop);

					/*
			 * 
			 * pobrany tag zawierający się miedzy poczatkiem na koncem lini(indeks poczatku<
			 * tag<indeks konca) indeksy w tablicy
			 * 
			 * daje indeks miedzy którymi ma być zwrócony tytuł filmu (getMovie)
			 */
			lenghtTitle("([\\:])", textArea.getText());
			

			// TODO only line with key-word
			// petla do pobierania tytułu filmu w przedziale w którym jest tag
			int i = 0;
			int k = 0;//
			String film;
			int end;
			while (i < arrayStartLine.length - 1) {

				if (arrayStartLine[i + 1] == 0)
					end = textArea.getText().length();
				else {
					end = arrayStartLine[i + 1] - 1;
				}
				if (arrayStartLine[i] < start && start < end && arrayLenghtTitle[k + 1] > 2) {
					// System.out.println(arrayStartLine[i]+" "+(arrayStartLine[i+1]-1)+" out");
					film = textArea.getText(arrayStartLine[i], arrayLenghtTitle[k + 1]);

					title.add(film);
				
				} else if (k == 0 && arrayStartLine[0] > start) {
					film = textArea.getText(1, arrayLenghtTitle[k]);
				
					title.add(film);
					
				}

				i++;
				k++;

			}
			Collections.sort(title);
			System.out.println();
			// System.out.println(rabinKarp_Algorithm.getPocz());
		}

	}

	/*
	 * metoda ładujaca posortowane tyttuły
	 */
	public void loadTitle() {

		int i;
		for (i = 0; i < title.size(); i++) {
			// titleArea.insertText(i, title.get(i)+"\n");
			titleArea.appendText(title.get(i) + "\n");
			System.out.println(title);

		}

		title.clear();
	}

	// @FXML
	// public void highlightText2() {
	// // if dosn't exist key-word
	// textArea.deselect();
	// //knuthMorrisPrattAalgorithm.search(wzor.getText().toString(),
	// odczyt.toString());
	//
	// int start = 1, stop = 0;
	// start = knuthMorrisPrattAalgorithm.getInd();
	// //System.out.println(start+" <- klucz");
	// stop = wzor.getText().length();
	// stop = start + stop;
	// // i fink dziaaa=> copy^
	//
	// if (odczyt.toString().length() < start) {
	// System.out.println("nie ma");
	// } else {
	// textArea.selectRange(start, stop);
	// /*
	// *
	// * pobrany tag zawierający się miedzy poczatkiem na koncem lini(indeks
	// poczatku<
	// * tag<indeks konca) indeksy w tablicy
	// *
	// * daje indeks miedzy którymi ma być zwrócony tytuł filmu (getMovie)
	// */
	// lenghtTitle("([\\:])", textArea.getText());
	//
	//
	////
	// // TODO only line with key-word
	// // petla do pobierania tytułu filmu w przedziale w którym jest tag
	// int i = 0;
	// int k =0 ;
	// int end;
	// while (i < arrayStartLine.length - 1) {
	//
	// if (arrayStartLine[i+1]==0 )
	// end =textArea.getText().length();
	// else {
	// end = arrayStartLine[i+1]-1;
	// }
	// if (arrayStartLine[i] < start && start < end ) {
	// // System.out.println(arrayStartLine[i]+" "+(arrayStartLine[i+1]-1)+" out");
	// String film = textArea.getText(arrayStartLine[i], arrayLenghtTitle[k+1]);
	// System.out.print(film);
	// //System.out.println(arrayStartLine[i]+" " +arrayLenghtTitle[k]);
	// }
	// else if(k==0 && arrayStartLine[0]>start ) {
	// String film1 = textArea.getText(1, arrayLenghtTitle[k]);
	// System.out.print(film1);
	//
	// }
	//
	// i++;
	// k++;
	//
	//
	// }
	// System.out.println();
	//
	// }
	// }

	@FXML
	private void zamknijAplikacje(ActionEvent event) {
		Optional<ButtonType> result = DialogsUtils.confirmationDialog();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	public void regexChecker(String theRegex, String str2Check) {
		Pattern pattern = Pattern.compile(theRegex);
		Matcher regexMatcher = pattern.matcher(str2Check);
		newLineInd = 0;
		int x = 0;
		int index = 0;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {

				newLineInd = regexMatcher.end();
				newLineInd = newLineInd - x;
				x--;
				textArea.insertText(newLineInd, "\n");

				arrayStartLine[index] = newLineInd;
				// początek każdej lini
				
				index++;

			}
		}
	}

	public int startLine;
	public int endLine;
	

	public void tag(String thRege, String str3) {
		int nextTag;
		Pattern pattern = Pattern.compile(thRege);
		Matcher regexMatcher = pattern.matcher(str3);
		nextTag = 0;
		int x = 0;
		TagsId.add(0,0);
		int i = 1;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {

				nextTag = regexMatcher.start();
				TagsId.add(i, nextTag);
				System.out.println(nextTag);
				i++;

			}
		}
		
		 

		
	}
	public void getMovie(String thReg, String str2) {
		Pattern pattern = Pattern.compile(thReg);
		Matcher regexMatcher = pattern.matcher(str2);
		startLine = 0;
		int x = 1;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {

				startLine = regexMatcher.start();

				// tutaj pobieramy tytul filmu tj od pierwszego znaku do ostatniego
				// System.out.println(textArea.getText(startLine+1,arrayLenghtTitle[x]));
				x++;

			}

		}

	}

	// metoda do zapisu indeksu konca tytułu
	public void lenghtTitle(String thReg2, String str3) {
		Pattern pattern = Pattern.compile(thReg2);
		Matcher regexMatcher = pattern.matcher(str3);
		endLine = 0;
		int x = 0;
		int i = 0;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {

				endLine = regexMatcher.end();

				arrayLenghtTitle[i] = endLine;
				// System.out.println(arrayLenghtTitle[i]);
				i++;

			}
		}

	}

}
