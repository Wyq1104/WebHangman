package com.example.appengine.java8;

import Games.Hangman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "GameServlet", value="/game")
public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();

        if(session.getAttribute("name")==null){
            String name=request.getParameter("name");
            session.setAttribute("name",name);
            request.setAttribute("name",name);
        }else{
            String name=session.getAttribute("name").toString();
            request.setAttribute("name",name);
        }
        if(session.getAttribute("phrase")==null){
            Hangman hangman=new Hangman();
            hangman.randomPhrase("test.txt");
            hangman.generateHiddenPhrase();
            String phrase=hangman.getPhrase();
            String hiddenphrase=hangman.getHiddenPhrase().toString();
            session.setAttribute("phrase",phrase);
            request.setAttribute("phrase",phrase);
            System.out.println(hiddenphrase);
            session.setAttribute("hiddenphrase",hiddenphrase);
            request.setAttribute("hiddenphrase",hiddenphrase);
            request.getRequestDispatcher("/game.jsp").forward(request,response);
        }else{
            String guess=request.getParameter("guess");
            char guessChar=guess.charAt(0);
            String phrase=session.getAttribute("phrase").toString();
            String hiddenphrase=session.getAttribute("hiddenphrase").toString();
            System.out.println(hiddenphrase);
            StringBuilder hiddenphraseSB=new StringBuilder(hiddenphrase);
            if(phrase.indexOf(guessChar)!=-1){
                for(int i=0;i<phrase.length();i++){
                    if(guessChar==phrase.charAt(i)) {
                        hiddenphraseSB.setCharAt(i, phrase.charAt(i));
                    }
                }
                if(hiddenphraseSB.toString().indexOf('*')==-1){
                    request.setAttribute("result", "Great, you win");
                    request.setAttribute("phrase", session.getAttribute("phrase"));
                    request.getRequestDispatcher("/result.jsp").forward(request,response);
                }
                request.setAttribute("feedback","Yes, you are right.");
                session.setAttribute("hiddenphrase",hiddenphraseSB.toString());
                request.setAttribute("hiddenphrase",hiddenphraseSB.toString());
            }else{
                // TODO: 10/24/19 premisses
                int chances=Integer.parseInt(session.getAttribute("chances").toString());
                chances--;
                if(chances==0){
                    request.setAttribute("result", "Sorry, you lose");
                    request.setAttribute("phrase", session.getAttribute("phrase"));
                    request.getRequestDispatcher("/result.jsp").forward(request,response);
                }
                session.setAttribute("chances",chances);
                request.setAttribute("feedback","Sorry, you missed.");
                request.setAttribute("hiddenphrase",hiddenphraseSB.toString());
                request.setAttribute("chances",chances);
            }
            request.getRequestDispatcher("/game.jsp").forward(request,response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
