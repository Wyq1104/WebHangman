package Games;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    String phrase;
    StringBuilder hiddenPhrase=new StringBuilder();
    ArrayList<Character> previousGuesses=new ArrayList<>();
    ArrayList<Character> preMisses=new ArrayList<>();
    ArrayList<Character> preCorrect=new ArrayList<>();

    public StringBuilder getHiddenPhrase() {
        return hiddenPhrase;
    }

    public String getPhrase() {
        return phrase;
    }

    //get a random Phrase
    public void randomPhrase(String filename){
        List<String> phraseList=null;
        try{
            phraseList= Files.readAllLines(Paths.get(filename));
        }catch (IOException e){
            System.out.println(e);
        }
        Random random=new Random();
        int i1=random.nextInt(phraseList.size());
        this.phrase=phraseList.get(i1);
    }

    //return initial hiddenPhrase
    public void generateHiddenPhrase(){
        for(int j=0;j<this.phrase.length();j++){
            if(Character.isLetter(this.phrase.charAt(j))==true){
                this.hiddenPhrase.append('*');
            }else{
                this.hiddenPhrase.append(this.phrase.charAt(j));
            }
        }
    }

    //get player's input

    /**
     *
     * @param scanner
     * @return
     */
    char playerGuess(Scanner scanner){
        // ask player to only input one letter
        String str1="";
        while(str1.length()!=1){
            System.out.println("Please guess a letter: ");
            str1=scanner.next();
        }
        char guess=str1.charAt(0);
        return guess;
    }

    //returns whether a letter matches and modifies the partially hidden phrase, and modifies the hidden phrase if there is a match.
    boolean processGuess(String lowPhrase, char lowGuess){
        this.previousGuesses.add(lowGuess);
        if(lowPhrase.indexOf(lowGuess)!=-1){
            System.out.println("Yes, you are right.");
            this.preCorrect.add(lowGuess);
            for(int i=0;i<lowPhrase.length();i++){
                if(lowGuess==lowPhrase.charAt(i)) {
                    this.hiddenPhrase.setCharAt(i, this.phrase.charAt(i));
                }
            }
            return true;
        }else{
            this.preMisses.add(lowGuess);
            System.out.println("Sorry, you missed");
            return false;
        }
    }

    //make every letter in phrase to lower case
    String makeLow(){
        String lowPhrase="";
        for(int i=0;i<this.phrase.length();i++){
            lowPhrase+=Character.toLowerCase(phrase.charAt(i));
        }
        return lowPhrase;
    }

    public static void main(String[] args) {
        Hangman hangman=new Hangman();
        hangman.randomPhrase("phrases.txt");
//        System.out.println(hangmanV3.phrase);
        String lowPhrase=hangman.makeLow();
//        System.out.println(lowPhrase);
        hangman.generateHiddenPhrase();
        System.out.println(hangman.hiddenPhrase);
        int misses;
        int TOTALCHANCES=2;
        int chancesLeft=TOTALCHANCES;
        Scanner scanner;
        scanner=new Scanner(System.in);
        while(chancesLeft>0){
            char guess=hangman.playerGuess(scanner);
            char lowGuess=Character.toLowerCase(guess);
            if(!Character.isLetter(guess)){
                System.out.println("You don't input a letter");
                continue;
            }
            if(hangman.preMisses.contains(lowGuess)){
                System.out.println("Please do not enter the wrong letter repeatedly!!!");
                continue;
            }
            if(hangman.preCorrect.contains(lowGuess)) {
                System.out.println("Please do not enter the correct letter repeatedly!!!");
                continue;
            }
            hangman.processGuess(lowPhrase,lowGuess);
            misses=hangman.preMisses.size();
            chancesLeft=TOTALCHANCES-misses;
            System.out.println("Misses: "+misses);
            System.out.println("Incorrect letters: "+hangman.preMisses);
            System.out.println("Chances left: "+chancesLeft);
            System.out.println("previous guesses"+hangman.previousGuesses);
            System.out.println(hangman.hiddenPhrase);
            if(hangman.hiddenPhrase.indexOf("*")==-1){
                break;
            }
        }
        if(chancesLeft==0){
            System.out.println("Sorry, You lose!!!");
            System.out.println("The correct answer is: "+hangman.phrase);
        }else{
            System.out.println("Congratulation, you win!!!");
        }
    }
}
