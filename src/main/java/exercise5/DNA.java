package exercise5;

import java.util.ArrayList;
import java.util.HashSet;

public class DNA{
    private ArrayList<Character> dnaseq = new ArrayList<>();

    /**
     * Constructor for DNA
     * @param nucleotides
     */
    public DNA(String nucleotides){
        if (!valid(nucleotides)){
            throw new IllegalArgumentException("Invalid DNA sequence");
        }
        for (int i = 0; i < nucleotides.length(); i++){
            dnaseq.add(nucleotides.charAt(i));
        }

    }

    /**
     * Checks if string is valid
     * @param strsequence
     * @return true if the String has complete codons (or no codons), false if some codons are incomplete.
     */
    public boolean valid(String strsequence){
        int count = 0;
        for (int i = 0; i < strsequence.length(); i++){
            if (strsequence.charAt(i) == 'A' || strsequence.charAt(i) == 'T' || strsequence.charAt(i) == 'C' || strsequence.charAt(i) == 'G'){
                count++;
                if (count == 3){
                    count = 0;
                }

            }
        }
        if (count != 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Calculates the mass of the DNA sequence
     * @return mass
     */
    public double totalMass() {
        double mass = 0;
        for (int i = 0; i < dnaseq.size(); i++){
            if (dnaseq.get(i) == 'A'){
                mass += 135.128;
            }
            else if (dnaseq.get(i) == 'C'){
                mass += 111.103;
            }
            else if (dnaseq.get(i) == 'T'){
                mass += 125.107;
            }
            else if (dnaseq.get(i) == 'G'){
                mass += 151.128;
            }
            else {
                mass += 100.000;
            }
        }
        mass = Math.round(mass*10.0)/10.0;
        return mass;
    }

    /**
     * Checks if DNA sequence is a protein. To be one, it has:
     * -Starts with an ATG codon.
     * -Ends with TAA, TAG or TGA.
     * -Has at least 30% C and G mass.
     * @return true if the DNA sequence is  a protein, false otherwise.
     */
    public boolean isProtein() {
        double massprotein = totalMass();
        mutateCodon("ATG","ATG");
        char [] startarray = { dnaseq.get(0), dnaseq.get(1), dnaseq.get(2) };
        String startcodon = new String (startarray);
        char [] endarray = { dnaseq.get(dnaseq.size()-3), dnaseq.get(dnaseq.size()-2), dnaseq.get(dnaseq.size()-1) };
        String endcodon = new String (endarray);
        if (!startcodon.equals("ATG")){
            return false;
        }
        if (!(endcodon.equals("TAA")) && !(endcodon.equals("TAG")) && !(endcodon.equals("TGA"))){
            return false;
        }
        if (dnaseq.size()<15){
            return false;
        }
        if ((nucleotideCount('C')*111.103+nucleotideCount('G')*151.128) < massprotein*0.3){
            return false;
        }
        return true;
    }

    /**
     *
     * @param cta must be a valid three letter codon.
     * @param atc must be a valid three letter codon.
     */
    public void mutateCodon(String cta, String atc) {
        char a = atc.charAt(0);
        char b = atc.charAt(1);
        char c = atc.charAt(2);
        char d = cta.charAt(0);
        char e = cta.charAt(1);
        char f = cta.charAt(2);
        if ((a != 'A' && a != 'T' && a != 'C'&& a != 'G') || (b != 'A' && b != 'T' && b != 'C'&& c != 'G') || (c != 'A' && c != 'T' && c != 'C'&& c != 'G')){
            return;
        }
        if ((d != 'A' && d != 'T' && d != 'C'&& d != 'G') || (e != 'A' && e != 'T' && e != 'C'&& e != 'G') || (f != 'A' && f != 'T' && f != 'C'&& f != 'G')){
            return;
        }
        for (int i = 0; i < dnaseq.size(); i++){
            if (dnaseq.get(i) != 'A' && dnaseq.get(i) != 'T' && dnaseq.get(i) != 'C' && dnaseq.get(i) != 'G'){
                dnaseq.remove(i);
                i--;
            }
        }
        for (int i = 0; i < dnaseq.size()-2; i++){
            if (dnaseq.get(i) == cta.charAt(0) && dnaseq.get(i+1) == cta.charAt(1) && dnaseq.get(i+2) == cta.charAt(2)){
                dnaseq.set((i), a);
                dnaseq.set((i+1), b);
                dnaseq.set((i+2), c);
            }
        }
    }

    /**
     *Counts the instances of a nucleotide in a DNA sequence.
     * @param a is 'A', 'T', 'C' or 'G'.
     * @return returns the number of times a occurs in DNA sequence.
     */
    public int nucleotideCount(char a) {
        int count = 0;
        for (int i = 0; i < dnaseq.size(); i++){
            if (dnaseq.get(i) == a){
                count++;
            }
        }
        return count;
    }

    /**
     *Converts passed string into string
     * @return String of DNA sequence
     */
    public String sequence() {
        StringBuilder builder = new StringBuilder(dnaseq.size());
        for(int i = 0; i < dnaseq.size(); i++){
            builder.append(dnaseq.get(i));
        }
        return builder.toString();
    }

    public HashSet<String> codonSet() {
        HashSet<String> codonSetg = new HashSet<String>();
        mutateCodon("ATG","ATG");
        char [] codon = new char [3];
        for (int i = 0; i < dnaseq.size(); i += 3){
            codon [0] = dnaseq.get(i);
            codon [1] = dnaseq.get(i+1);
            codon [2] = dnaseq.get(i+2);
            String scodon = new String (codon);
            codonSetg.add(scodon);
        }
        return codonSetg;
    }
}