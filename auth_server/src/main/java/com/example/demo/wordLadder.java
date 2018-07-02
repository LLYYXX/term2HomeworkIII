package com.example.demo;

import java.util.*;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class wordLadder {
	static private final ArrayList<String> words = new ArrayList<>();
	static private Stack<String> answer = new Stack<>();
	static private final String dicPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
	static private final String filename = "dictionary.txt";
	static String word1;
	static String word2;
	private static StringBuffer AnswerReturn = new StringBuffer("");

	enum KEY {
		ERR, OK
	}

	private static Logger logger = LogManager.getLogger(wordLadder.class);

	wordLadder(String word1, String word2) {
		wordLadder.word1 = word1;
		wordLadder.word2 = word2;
	}

	public static StringBuffer start() {
		if (!getDic()) {
			return AnswerReturn;
		}
		KEY key = getWords();
		if (key == KEY.ERR)
			return AnswerReturn;
		if (ladder())
			return printout();
		return AnswerReturn;
	}

	static boolean getDic() {
		try {
			File file = new File(dicPath + filename);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tmpWord;
			tmpWord = reader.readLine();
			while (tmpWord != null) {
				words.add(tmpWord);
				tmpWord = reader.readLine();
			}
			reader.close();
			return true;
		} catch (Exception e) {
			AnswerReturn = new StringBuffer("No such file!");
			logger.info("No such file!");
			return false;
		}
	}

	static KEY getWords() {
		try {
			isValid(word1);
			isValid(word2);
			checkWords();
			return KEY.OK;
		} catch (Exception e) {
			AnswerReturn = new StringBuffer(e.getMessage());
			logger.info(e.getMessage());
			return KEY.ERR;
		}
	}

	static void isValid(String word) throws Exception {
		char[] cWord = word.toCharArray();
		for (char ch : cWord) {
			if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
				throw new Exception("Invalid input.");
		}
		word = word.toLowerCase();
		if (!checkHaveWord(word))
			throw new Exception("Invalid input.");
	}

	static boolean checkHaveWord(String word) {
		int low = 0;
		int high = words.size() - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			String w = words.get(mid);
			if (w.equals(word))
				return true;
			else if (word.compareTo(w) < 0)
				high = mid - 1;
			else
				low = mid + 1;
		}
		return false;
	}

	static void checkWords() throws Exception {
		if (word1.length() != word2.length() )
			throw new Exception("No ladder.");
		if (word1.equals(word2))
			throw new Exception("Same words.");
	}

	static boolean ladder() {
		Stack<String> s = new Stack<>();
		s.push(word1);
		Queue<Stack<String>> line = new LinkedList<>();
		line.offer(s);
		Set<String> wordsBeenUsed = new HashSet<>();
		wordsBeenUsed.add(word1);
		try {
			while (!line.isEmpty()) {
				String upWord = line.peek().peek();
				for (int i = 0; i < upWord.length(); i++) {
					for (char j = 'a'; j <= 'z'; j++) {
						String tmpWord = upWord.substring(0, i) + j + upWord.substring(i + 1);
						if (checkHaveWord(tmpWord)) {
							if (!wordsBeenUsed.contains(tmpWord)) {
								if (tmpWord.equals(word2)) { // get the ladder
									line.peek().push(tmpWord);
									answer = line.peek();
									return true;
								}
								wordsBeenUsed.add(tmpWord);
								// copy the stack
								Stack<String> tmp = (Stack<String>) line.peek().clone();
								tmp.push(tmpWord);
								line.add(tmp);
							}
						}
					}
				}
				line.poll();
			}
			throw new Exception("No ladder.");
		} catch (Exception e) {
			AnswerReturn = new StringBuffer(e.getMessage());
			logger.info(e.getMessage());
			return false;
		}
	}

	static StringBuffer printout() {
		StringBuffer ans = new StringBuffer("");
		while (!answer.empty()) {
			ans.append(answer.pop());
			ans.append("\t");
		}
		return ans;
	}
}
