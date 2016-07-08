package guide_maker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String path ="src.txt";
		Scanner in = new Scanner("");
		try{
			File file = new File(path);
			in = new Scanner(file);
		}
		catch (IOException e){
			System.out.println(e);
		}
		path = path.replaceAll("\n+", "\n"); //replaces double or more spaces 
		int tline = lineCount(path);
		String fullline ="";
		String line= "";
		boolean list = false; //currenting writing an unordered list
		boolean olist = false; //currently writing an ordered list
		boolean accordion = false;
		boolean accordionFirst = false; //Has atleast one accordion been posted yet?
		 // end for html element
		for(int lines=0; lines<tline; lines++)
		{
			if(in.hasNextLine())
				line = in.nextLine();
			if(line.isEmpty()){
				if(list==true){
					list=false;
					//fullline+="&nbsp;"; extra space
					fullline+="</ul>";
				}
				else if(olist==true){
					olist=false;
					fullline+="</ol>";
				}
			}
			//line = bolder(line);
			//line = addItalics(line);
			if(isParagraph(line)){
				fullline+="<p>";
				fullline+=line;
				fullline+="</p>";
			}
			if(isList(line)){
				if (list == false){
					fullline+="<ul>";
					list=true;
					
				}
				line = line.replace("*", "");
				fullline+="<li>"+line+"</li>";
			}
			if(orderedList(line)){
				if (olist==false){
					fullline+="<ol>";
					olist=true;
				}
				line = line.replace(line.substring(0,2),"");
				fullline+="<li>"+line+"</li>";
			}
			if(isAccordion(line)){
				if(accordion==true){
					accordion=false;
					fullline+="</div></div>";
				}
				else {
					accordion=true;
					fullline+="<div id=\"accordion\">";
				}
				
			}
			if(accordionH(line)){
				if(accordionFirst == true){
					fullline+="</div>";
				}
				line = line.replace("#$accordionH#$", "");
				fullline+="<div class=\"accordion-toggle\"> <h3>"+line+"</h3></div>";
				fullline+="<div class=\"accordion-content\">";
				accordionFirst = true;
			}
			
			
			if(isEnd(line))
				break;
		}
		System.out.println(fullline);
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("output.html"), "utf-8"))) {
	   writer.write(fullline);
	}
	}

	static int lineCount(String path) throws FileNotFoundException{
		Scanner input = new Scanner(new File(path));
		int count=0;
		while(!input.nextLine().contains("$end$")){
			count++;
		}
		return count;
	}
	static boolean isEnd(String p){
		if(p.contains("$end$"))
			return true;
		return false;
	}
	static boolean isAccordion(String p){
		if(p.contains("#$accordion#$"))
			return true;
		return false;
	}
	static boolean accordionH(String p){ //accordion heading
		if(p.contains("#$accordionH#$"))
			return true;
		return false;
	}
	/*static String bolder(String p){
		String result =p;
		boolean cbold = false; //currently bolding a word.
		if (result.contains("*")){
			for(int i=0; i<=result.length()-1; i++){
				if(result.charAt(i)=='*'){
					if (cbold ==false){
						result = result.substring(0,i) + "<strong>" + result.substring(i+1,result.length());
						cbold=true;
					}else{
						result = result.substring(0,i) + "</strong>" + result.substring(i+1,result.length());
						cbold=false;
					}
				}
			}
		}
		return result;
	}*/
	/*static String addItalics(String p){
		String result =p;
		boolean cbold = false; //currently bolding a word.
		if (result.contains("#")){
			for(int i=0; i<=result.length()-1; i++){
				if(result.charAt(i)=='#'){
					if (cbold ==false){
						result = result.substring(0,i) + "<i>" + result.substring(i+1,result.length());
						cbold=true;
					}else{
						result = result.substring(0,i) + "</i>" + result.substring(i+1,result.length());
						cbold=false;
					}
				}
			}
		}
		return result;
	}*/
	static boolean orderedList(String p){
		if(p.contains(".") ){ //checks for ordered list
			for (int i=0; i<p.length(); i++){
				if(Character.isDigit(p.charAt(i))&&p.charAt(i+1)=='.'){
						return true;
				}	
			}
		}
		return false;
	}
	static boolean isParagraph(String p){
		if(p.isEmpty())
			return false;
		else if(p.contains("$end$"))
			return false;
		else if(p.contains("*"))
			return false;
		else if(p.contains("#"))
			return false;
		else if(p.contains("#$accordion#$"))
			return false;
		else if(p.contains("#$accordionH#$"))
			return false;
		else if(p.contains(".") ){ //checks for ordered list
			for (int i=0; i<p.length(); i++){
				if(Character.isDigit(p.charAt(i))&&p.charAt(i+1)=='.'){
						return false;
				}	
			}
		}
		return true;
	}
	static boolean isList(String p){
		if(p.contains("*"))
			return true;
		return false;
	}
}

