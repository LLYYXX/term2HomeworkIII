package com.example.demo;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;

public class wordLadderTest {

@Test
public void getDicNormal(){
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    try {
        System.setIn(new ByteArrayInputStream(fileName.getBytes()));
        Assert.assertTrue(wordLadder.getDic());
    } finally {
        System.setIn(stdin);
    }
}


@Rule
public ExpectedException expectedEx = ExpectedException.none();
@Test
public void testNotAWord() throws Exception {
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("Invalid input.");
    wordLadder.isValid("q2w");
} 

@Test
public void testCheckHaveWord() throws Exception {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(wordLadder.getDic());
    System.setIn(stdin);
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("Invalid input.");
    wordLadder.isValid("q");
}

@Test
public void testLengthCheck() throws Exception { 
    wordLadder.word1="fragile";
    wordLadder.word2="break";
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("No ladder.");
    wordLadder.checkWords();
} 

@Test
public void testSameCheck() throws Exception {
    wordLadder.word1="paradox";
    wordLadder.word2="paradox";
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("Same word.");
    wordLadder.checkWords();
} 

/** 
* 
* Method: wordLadder() 
* 
*/ 
@Test
public void noLadder() {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(wordLadder.getDic());
    System.setIn(stdin);
    wordLadder.word1="sepia";
    wordLadder.word2="azure";
    Assert.assertFalse(wordLadder.ladder());
}

@Test
public void haveLadder() {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(wordLadder.getDic());
    System.setIn(stdin);
    wordLadder.word1="angel";
    wordLadder.word2="demon";
    Assert.assertTrue(wordLadder.ladder());
    }
} 
