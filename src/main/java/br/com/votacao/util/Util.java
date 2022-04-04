package br.com.votacao.util;

import java.util.ArrayList;
import java.util.List;

public class Util {


		public static String removeCaracteresEspeciais(String texto) {
			if (texto == null)
				return "";
			
			texto = texto.replaceAll("[^0-9A-Za-z' ']*", "");  
			return texto;
		}

		public static boolean validaCPF(String cpf) {
			if (cpf == null)
				return true;
			
			if (cpf.equals("00000000000") ||
					cpf.equals("11111111111") ||
					cpf.equals("22222222222") ||
					cpf.equals("33333333333") ||
					cpf.equals("44444444444") ||
					cpf.equals("55555555555") ||
					cpf.equals("66666666666") ||
					cpf.equals("77777777777") ||
					cpf.equals("88888888888") ||
					cpf.equals("99999999999") 	) {
					
					return false;
			}
			
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			List<String> cpfs =  cpfSequencial();
			String nDigResult;

			
			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;

			try {
			     Long.parseLong(cpf);
			} catch (Exception e) {
			  return false;
			}
			
			if ((cpf != null) && (cpf.trim().length() != 11)) {  
				return false;
			}
			
			if((cpf != null) && (cpfs.contains(cpf))) {
				return false;
			}
			
			for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
				digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

				// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
				// e assim por diante.
				d1 = d1 + (11 - nCount) * digitoCPF;

				// para o segundo digito repita o procedimento incluindo o primeiro
				// digito calculado no passo anterior.
				d2 = d2 + (12 - nCount) * digitoCPF;
			}

			// Primeiro resto da divisÃ£o por 11.
			resto = (d1 % 11);

			// Se o resultado for 0 ou 1 o digito Ã© 0 caso contrÃ¡rio o digito Ã© 11
			// menos o resultado anterior.
			if (resto < 2)
				digito1 = 0;
			else
				digito1 = 11 - resto;

			d2 += 2 * digito1;

			// Segundo resto da divisÃ£o por 11.
			resto = (d2 % 11);

			// Se o resultado for 0 ou 1 o digito Ã© 0 caso contrÃ¡rio o digito Ã© 11
			// menos o resultado anterior.
			if (resto < 2)
				digito2 = 0;
			else
				digito2 = 11 - resto;

			// Digito verificador do CPF que estÃ¡ sendo validado.
			String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

			// Concatenando o primeiro resto com o segundo.
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

			// comparar o digito verificador do cpf com o primeiro resto + o segundo
			// resto.
			if (!nDigVerific.equals(nDigResult)) {
				return false;
			}
			
			return true;
		}
		
		private static List<String> cpfSequencial() {
			List<String> cpfs = new ArrayList<String>();
			StringBuffer c = null;
			for(int i = 2; i <10; i++) {
				c = new StringBuffer();
				for(int n = 0; n < 11;n++) {
					c.append(i);
				}
				cpfs.add(c.toString());
			}
			return cpfs;
		}
		

}

