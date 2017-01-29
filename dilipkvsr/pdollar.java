import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class pdollar {
	
	private void initGestures(PDollarRecognizer pobj) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Gestures.txt"));
		String str, name = "";
		ArrayList<Point> l = new ArrayList<>();
		
		while((str = br.readLine()) != null) {
			if (str.equalsIgnoreCase("NEW")) {
				name = br.readLine();
				continue;
			}
			else if (str.equalsIgnoreCase("END")){
				pobj.addGesture(name, l);
				l.clear();
			}
			else {
				String[] t = str.split(",");
				l.add(new Point(Double.parseDouble(t[0]), Double.parseDouble(t[1]), Integer.parseInt(t[2])));
			}
		}
		br.close();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String s1, str;
		BufferedReader br;
		BufferedWriter bw;
		ArrayList<Point> l;
		pdollar pdobj = new pdollar();
		PDollarRecognizer pobj = new PDollarRecognizer();
		while (true) {
			if (args.length == 0) {
				System.out.println("pdollar -t <gesturefile> ");
				System.out.println("Adds the gesture file to the list of gesture templates ");
				System.out.println("");
				System.out.println("pdollar ‐r ");
				System.out.println("Clears the templates ");
				System.out.println("");
				System.out.println("pdollar <eventstream> ");
				System.out.println("Prints the name of gestures as they are recognized from the event stream.");
				break;
			
			} else {
				// Add gesture
				if (args[0].equalsIgnoreCase("-t")) {
					int strokeId = 1;
					br = new BufferedReader(new FileReader(args[1]));
					bw = new BufferedWriter(new FileWriter("Gestures.txt", true));
					l = new ArrayList<>();
					LinkedList<String> pList = new LinkedList<>();
					bw.write("NEW");
					bw.newLine();
					String name = br.readLine();
					pList.add(name);
					
					while ((s1 = br.readLine()) != null) {
						
						if (s1.equalsIgnoreCase("BEGIN"))
							continue;
						else if (s1.equalsIgnoreCase("END")) {
							strokeId++;
							continue;
						} else {
							//String[] t = s1.split(",");
							//l.add(new Point(Double.parseDouble(t[0]), Double.parseDouble(t[1]), strokeId));
							
							pList.add(s1 + ","+ Integer.toString(strokeId));
						}
					}
					br.close();
					
					for (String st : pList){
						bw.write(st);
						bw.newLine();
					}
					
					bw.write("END");
					bw.newLine();
					
					bw.flush();
					bw.close();
					pList.clear();
					//pobj.addGesture(name, l);
					//System.out.println();
					break;
				}

				/* remove a gesture */
				else if (args[0].equalsIgnoreCase("-r")) {
					File file = new File ("Gestures.txt");
					if (file.exists()) file.delete();
					//PDollarRecognizer.mPntClouds.clear();remove(PDollarRecognizer.mPntClouds.size() - 1);
					break;
				}
				/* Process the EventStream */
				else if (!(args[0].length() == 0)) {
					pdobj.initGestures(pobj);
					int strokeId = 1;
					br = new BufferedReader(new FileReader(args[0]));
					l = new ArrayList<>();

					while ((str = br.readLine()) != null) {
						if (str.equalsIgnoreCase("MOUSEDOWN"))
							continue;

						else if (str.equalsIgnoreCase("MOUSEUP")) {
							strokeId++;
							continue;
						}
						
						else if (str.equalsIgnoreCase("RECOGNIZE")) {
						RecognizerResults obj = pobj.Recognize(l);
						System.out.println(obj.mName);
						//System.out.println(obj.mScore);
						//System.out.println(obj.mOtherInfo);
						}

						else {
							String[] t = str.split(",");
							l.add(new Point(Double.parseDouble(t[0]), Double.parseDouble(t[1]), strokeId));
						}
					}
					break;
				}
				else {
					System.out.println("type java pdollar for help");
					break;
				}
			}
		}
	}
}