package com.ponto_eletronico;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	private static final int DIGIT_COUNT = 11;
	
	@Id
	private String pis;
	private String senha;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Usuario(String pis) {
		this.setPis(pis);
	}

	public Usuario(String pis, String senha) {
		this.setPis(pis);
		this.senha = senha;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
		if (!this.validaPIS()) {
			throw new PeException("Pis invalido");
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	private boolean validaPIS(){
        if (pis == null) return false;
        String n = pis.replaceAll("[^0-9]*","");
        //boolean isPis = n.length() == PIS_DIGITS;
        //boolean isPasep = n.length() == PASEP_DIGITS;
        if (n.length() != DIGIT_COUNT) return false;
        int i;          // just count 
        int digit;      // A number digit
        int coeficient; // A coeficient  
        int sum;        // The sum of (Digit * Coeficient)
        int foundDv;    // The found Dv (Chek Digit)
        int dv = Integer.parseInt(String.valueOf(n.charAt(n.length()-1)));      
        sum = 0;
        coeficient = 2;
        for (i = n.length() - 2; i >= 0 ; i--){
            digit = Integer.parseInt(String.valueOf(n.charAt(i)));               
            sum += digit * coeficient;
            coeficient ++;
            if (coeficient > 9) coeficient = 2;                
        }                
        foundDv = 11 - sum % 11;
        if (foundDv >= 10) foundDv = 0;        
        return dv == foundDv;
    }
}
