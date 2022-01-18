/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.riddlerclassic.wordle;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author julis
 */
public class Wordle {
    public static void main(String[] args)
    {
        ArrayList<String> answer_list = new ArrayList<String>();
        try {
            Scanner s = new Scanner(new File("C:\\Users\\julis\\OneDrive\\Documents\\NetBeansProjects\\riddlerclassic-wordle\\src\\main\\java\\com\\mycompany\\riddlerclassic\\wordle\\answers.txt"));
            while (s.hasNext())
            {
                answer_list.add(s.next());
            }
            s.close();
            
        } catch (FileNotFoundException e) {
            
        }
        ArrayList<String> guess_list = new ArrayList<String>();
        try {
            Scanner s = new Scanner(new File("C:\\Users\\julis\\OneDrive\\Documents\\NetBeansProjects\\riddlerclassic-wordle\\src\\main\\java\\com\\mycompany\\riddlerclassic\\wordle\\guesses.txt"));
            while (s.hasNext())
            {
                guess_list.add(s.next());
            }
            s.close();
            
        } catch (FileNotFoundException e) {
            
        }
        
        path(guess_list, answer_list, "leash", "proxy");
        
        
        
        
        
    }
    
    public static String solve_wordle(List<String> guess_list, List<String> answer_list)
    {
        File all_weights = new File("weights.txt");
        try {
            all_weights.createNewFile();                       
        } catch(IOException e) {
            
        }
        
        ArrayList<String> weights_list = new ArrayList<String>();
        
        
        
        int max_weight = 0;
        String s = "";
        for(String guess : guess_list)
        {
            int weight = first_word_probability(guess_list, answer_list, guess);
            if(weight > max_weight) { max_weight = weight; s = guess; }
            if(weight == max_weight) { s += " " + guess; }
            weights_list.add(guess + "," + String.valueOf(weight) + "\n");
            System.out.println(guess + "," + weight);
        }
        try {
                FileWriter all_weight = new FileWriter("weights.txt");
                for(String w : weights_list)
                {
                    all_weight.write(w);
                }
                all_weight.close();               
            } catch(IOException e) {
                
            }
        return s;
    }
    
    //Returns total weight of all possiblities given a first word
    //Probability of success is equal to weight/total answer list size
    public static int first_word_probability(List<String> guess_list, List<String> answer_list, String first_word)
    {
        int total_weight = 0;
        for(int i = 0; i < 243; i++)
        {
            total_weight+= solve_2nd_word(guess_list, answer_list, first_word, i);
        }
        return total_weight;
    }
    
    
    //Prints the path to a given word and returns the path length
    public static int path(List<String> guess_list, List<String> answer_list, String first_word, String answer)
    {
        String path = first_word;
        if(first_word.equals(answer)) { System.out.println(path); return 1; }
        int tern1 = to_tern(first_word, answer);
        String second_word = solve_2nd_word_str(guess_list, answer_list, first_word, tern1);
        int tern2 = to_tern(second_word, answer);
        
        //Valid Answer List
        ArrayList<String> val2 = new ArrayList<String>();
        for(String s : answer_list)
        {
            if(is_valid(first_word, tern1, s))
            {
                if(is_valid(second_word, tern2, s))
                {
                    val2.add(s);
                }
            }
        }
        
        path += " " + second_word;
        
        if(second_word.equals(answer)) { System.out.println (path); return 2; }
        
        int max = 0;
        String third_word = "";
        for (String curr_g : val2)
        {
            
            boolean[] q = new boolean[243];
            int sets = 0;
            for (String ans : val2)
            {
                int x = to_tern(curr_g, ans);
                
                if(!q[x])
                {
                    sets++;
                    q[x] = true;
                }
            }
            if(sets > max)
                {
                    max = sets;
                    third_word = curr_g;
                }
            
        }
        
        path += " " + third_word;
        
        if(third_word.equals(answer)) {System.out.println (path); return 3; }
        int tern3 = to_tern(third_word, answer);
        
        ArrayList<String> val3 = new ArrayList<String>();
        for(String s : val2)
        {
            
            
                if(is_valid(third_word, tern3, s))
                {
                    val3.add(s);
                }
            
        }
        
        max = 0;
        String fourth_word = "";
        for (String curr_g : val3)
        {
            
            boolean[] q = new boolean[243];
            int sets = 0;
            for (String ans : val3)
            {
                int x = to_tern(curr_g, ans);
                
                if(!q[x])
                {
                    sets++;
                    q[x] = true;
                }
            }
            if(sets > max)
                {
                    max = sets;
                    fourth_word = curr_g;
                }
            
        }
        
        path += " " + fourth_word;
        
        if(fourth_word.equals(answer)) {System.out.println (path); return 4; }
        int tern4 = to_tern(fourth_word, answer);
        
        ArrayList<String> val4 = new ArrayList<String>();
        for(String s : val3)
        {
            
                if(is_valid(fourth_word, tern4, s))
                {
                    val4.add(s);
                }
            
        }
        
        max = 0;
        String fifth_word = "";
        for (String curr_g : val4)
        {
            boolean[] q = new boolean[243];
            int sets = 0;
            for (String ans : val4)
            {
                int x = to_tern(curr_g, ans);
                
                if(!q[x])
                {
                    sets++;
                    q[x] = true;
                }
            }
            if(sets > max)
                {
                    max = sets;
                    fifth_word = curr_g;
                }
            
        }
        
        path += " " + fifth_word;
        
        if(fifth_word.equals(answer)) {System.out.println (path); return 5; }
        String sixth_word = "";
        for (String curr_g : val4)
        {
            if(is_valid(fifth_word, to_tern(fifth_word, answer), curr_g))
            {
                sixth_word = curr_g;
            }
        }
        
        path += " " + sixth_word;
        if(sixth_word == null ? answer == null : sixth_word.equals(answer)) {System.out.println (path); return 6; }
        
        path += " ???? " + answer;
        System.out.println(path);
        return 7;
        
    }
    
    //tern-results is the returned colors from the first word
    //0 is 
    //convert from reverse-order ternary
    //so 21010 is 2 + 1 * 3 + 0 * 9 + 1 * 27 + 0 * 81 = 32
    //Returns weight (probability of success * total size of valid answer list)
    public static int solve_2nd_word(List<String> guess_list, List<String> answer_list, String first_word, int tern)
    {
        //replace answer_list with valid_answer_list
        ArrayList<String> valid_answer_list = new ArrayList<String>();
        for(String s : answer_list)
        {
            if(is_valid(first_word, tern, s))
            {
                valid_answer_list.add(s);
            }
        }
        
        //Trivially 100% probability for sets of size 1 or 2
        if(valid_answer_list.size() < 3)
        {
            return valid_answer_list.size();
        }
        
        int max = 0;
        String curr_best = "";
        //for every guess in guess list:
        for (String guess : guess_list)
        {
            //create an array with size 243 and all values false
            boolean[] q = new boolean[243];
            //store integer sets
            int sets = 0;
            //for each answer in valid answer list:
            for (String ans : valid_answer_list)
            {
                //store to_tern(guess, answer) [let's call it x]
                int x = to_tern(guess, ans);
                //if array[x] is false, increment sets by 1 and set it to true
                if(!q[x])
                {
                    sets++;
                    q[x] = true;
                }
                //if sets > maximum, set maximum to sets and set curr_best to guess
                if(sets > max)
                {
                    max = sets;
                    curr_best = guess;
                }
                else if(sets == max)
                {
                }
            }
        }
        return max;
            
            
        
        
        
        //print best word
        //print size of valid_answer_list
        //print number of distinct sets
        //the probability of getting the word is equal to the number of distinct sets divided by the size of valid_answer_list
        
        
        
    }
    
    public static String solve_2nd_word_str(List<String> guess_list, List<String> answer_list, String first_word, int tern)
    {
        //replace answer_list with valid_answer_list
        ArrayList<String> valid_answer_list = new ArrayList<String>();
        for(String s : answer_list)
        {
            if(is_valid(first_word, tern, s))
            {
                valid_answer_list.add(s);
            }
        }
        
        //Trivially 100% probability for sets of size 1 or 2
        if(valid_answer_list.size() < 2)
        {
            return valid_answer_list.get(0);
        }
        
        int max = 0;
        String curr_best = "";
        //for every guess in guess list:
        for (String guess : guess_list)
        {
            //create an array with size 243 and all values false
            boolean[] q = new boolean[243];
            //store integer sets
            int sets = 0;
            //for each answer in valid answer list:
            for (String ans : valid_answer_list)
            {
                //store to_tern(guess, answer) [let's call it x]
                int x = to_tern(guess, ans);
                //if array[x] is false, increment sets by 1 and set it to true
                if(!q[x])
                {
                    sets++;
                    q[x] = true;
                }
                //if sets > maximum, set maximum to sets and set curr_best to guess
                if(sets > max)
                {
                    max = sets;
                    curr_best = guess;
                }
                else if(sets == max)
                {
                }
            }
        }
        return curr_best;
            
            
        
        
        
        //print best word
        //print size of valid_answer_list
        //print number of distinct sets
        //the probability of getting the word is equal to the number of distinct sets divided by the size of valid_answer_list
        
        
        
    }
    
    public static int to_tern(String guess, String ans)
    {
        int k = 1;
        int out = 0;
        for(int n = 0; n < 5; n++)
        {
            int a = ans.indexOf(guess.charAt(n));
            if(ans.charAt(n)==(guess.charAt(n))) { out += 2 * k; }
            else if(a != -1) { out += k; }
            k *= 3;
        }
        return out;
    }
    
    public static boolean is_valid(String first_word, int tern, String guess)
            //Given a first guess, its results, and 
    {
        int l1 = guess.indexOf(first_word.charAt(0));
        switch (tern % 3)
        {
            case 0:
                if(l1 != -1) { return false; }
                break;
            case 1:
                if((l1 == -1) || (l1 == 0)) { return false; }
                break;
                
            case 2:
                if(l1 != 0) { return false; }
                break;
        }
        int x2 = first_word.charAt(1);
        int n2 = guess.charAt(1);
        int l2 = guess.indexOf(x2);
        switch ((tern / 3) % 3)
        {
            case 0:
                if(l2 != -1) { return false; }
                break;
            case 1:
                if((l2 == -1) || (x2 == n2)) { return false; }
                break;
                
            case 2:
                if(x2 != n2) { return false; }
                break;
        }
        int x3 = first_word.charAt(2);
        int n3 = guess.charAt(2);
        int l3 = guess.indexOf(x3);
        switch ((tern / 9) % 3)
        {
            case 0:
                if(l3 != -1) { return false; }
                break;
            case 1:
                if((l3 == -1) || (x3 == n3)) { return false; }
                break;
                
            case 2:
                if(x3 != n3) { return false; }
                break;
        }
        int x4 = first_word.charAt(3);
        int n4 = guess.charAt(3);
        int l4 = guess.indexOf(x4);
        
        switch ((tern / 27) % 3)
        {
            case 0:
                if(l4 != -1) { return false; }
                break;
            case 1:
                if((l4 == -1) || (x4 == n4)) { return false; }
                break;          
            case 2:
                if(x4 != n4) { return false; }
                break;
        }       
        int x5 = first_word.charAt(4);
        int n5 = guess.charAt(4);
        int l5 = guess.indexOf(x5);
        switch ((tern / 81) % 3)
        {
            case 0:
                if(l5 != -1) { return false; }
                break;
            case 1:
                if((l5 == -1) || (x5 == n5)) { return false; }
                break;
                
            case 2:
                if(x5 != n5) { return false; }              
                break;
        }
        return true;
        
    }
    
}
