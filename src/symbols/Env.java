package symbols;
import java.util.*;

import code_generation.AssemblyFile;
import information.Printer;
import lexer.*; import inter.*;

/**
 * Class used to store the variables of a natural program
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Env {

	private Hashtable table;
	protected Env prev;
	private ArrayList<Token> mKeys;		//ArrayList of keys for generating global Assembly vars

	public Env(Env n) {
		table = new Hashtable();
		prev = n;
		Printer.writeEnvInfo();
		mKeys = new ArrayList<>();
	}

	public void put(Token w, Id i) {
		table.put(w, i);
		mKeys.add(w);
	}

	public void generateAsmData(){
		try{
			for(Token token: mKeys){
				Id id = (Id) table.get(token);			//Get the identifier corresponding to the token
				AssemblyFile.addDataToFront(id.toAsmData());	//Add the identifier to the data section
			}
		}catch (Exception e){
			System.out.printf("ERROR GENERATING .data in Env.java");
		}
	}

	public Id get(Token w) {
		for( Env e = this; e != null; e = e.prev ) {
			Id found = (Id)(e.table.get(w));
			if( found != null ) return found;
		}
		return null;
	}
}
