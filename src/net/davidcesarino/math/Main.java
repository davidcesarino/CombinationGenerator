/*
 * Combination Generator
 * Copyright (C) 2005 David Cesarino de Sousa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.davidcesarino.math;

import jargs.gnu.CmdLineParser;

import java.text.DecimalFormat;


public class Main {

    public static void main(String[] args) {
		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option g_option =
				parser.addBooleanOption('g', "gerar");
		CmdLineParser.Option i_option =
				parser.addBooleanOption('i', "indexar");
		CmdLineParser.Option r_option =
				parser.addBooleanOption('r', "relatorio");
		CmdLineParser.Option k_option =
				parser.addIntegerOption('k', "escolher");
		CmdLineParser.Option d_option =
				parser.addStringOption('d', "delimitador");
		CmdLineParser.Option h_option =
				parser.addBooleanOption("ajuda");

		try {
			parser.parse(args);
		}
		catch (CmdLineParser.OptionException e) {
			System.err.println(e.getMessage());
			System.err.println(showUsage());
			System.exit(1);
		}

		Boolean  generate = (Boolean)parser.getOptionValue(g_option);
		Boolean         h = (Boolean)parser.getOptionValue(h_option);
		Boolean    report = (Boolean)parser.getOptionValue(r_option, false);
		Integer         k = (Integer)parser.getOptionValue(k_option);
		String[] universe =          parser.getRemainingArgs();		

		if (Boolean.TRUE.equals(generate)) {
			String          d = (String) parser.getOptionValue(d_option, "-");
			Boolean     index = (Boolean)parser.getOptionValue(i_option, false);
			
			int[][] combinations = generateCombination(universe.length, k);
			int z = combinations.length;

			if (report) {
				System.out.println(
					showReport(universe.length, k, z, universe) + LN
				);
			}

			for (int x = 0; x < combinations.length; x++) {
				if (index) {
					System.out.print(formatZeroAndSeparator(x + 1, z) + ": ");
				}
				
				for (int y = 0; y < combinations[x].length; y++) {
					System.out.print(universe[combinations[x][y]]);
					if (y < combinations[x].length - 1) {
						System.out.print(d);
					}
				}
				System.out.println();
			}
		}
		else if (Boolean.TRUE.equals(report)) {
			CombinationGenerator ForReportOnly =
				new CombinationGenerator (universe.length, k);
			
			System.out.println(showReport(
					universe.length,
					k,
					ForReportOnly.getNumLeft(),
					universe
			));
		}
		else if (Boolean.TRUE.equals(h)) {
			System.out.println(showHelp());
		}
		else {
			System.out.println(showUsage());
		}
	}
	
	private static String separate(String[] elements, String separator) {
		StringBuilder c = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			c.append(elements[i]);
			if (i < (elements.length - 1)) {
				c.append(separator);
			}
		}
		return c.toString();
	}
	
	private static String formatZeroAndSeparator(
		long num, long max_num) {
		
		StringBuilder mask = new StringBuilder();
		int HowManyDigits = String.valueOf(max_num).length();

		for (int i = 1; i <= HowManyDigits; i++) {
			// If multiple of 3 or at the end, adds only a zero
			if (((i % 3) != 0) || (i == HowManyDigits)) {
				mask.insert(0, "0");
			}
			// If multiple of 3 and not at the end, add dot-zero
			else if (((i % 3) == 0) && (i != HowManyDigits)) {
				mask.insert(0, ",0");
			}
		}

		DecimalFormat Formatter = new DecimalFormat(mask.toString());
		return Formatter.format(num);
	}

	private static int[][] generateCombination(int n, int k) {
		
		CombinationGenerator generator =
			new CombinationGenerator(n, k);
		
		int[][] list = new int[generator.getNumLeft()][k];
		int[] indices;
		int j;

		for (j = 0; generator.hasMore(); j++) {
			indices = generator.getNext();
			System.arraycopy(indices, 0, list[j], 0, indices.length);
		}
		return list;
	}

	private static String showUsage() {
		return HEADER + LN + LN + TIP;
	}
	
	private static String showHelp() {
		return HEADER + LN + LN + HELP;
	}

	private static String showReport(Integer c, Integer k, long total,
		String[] elements) {
		return L1 + c.toString() + LN +
			   L2 + k.toString() + LN +
			   L4 + formatZeroAndSeparator(total, total) + LN +
			   L3 + separate(elements, ", ");
	}
	
	// -------------------------------
	// CONSTANTES ------------>
	// -------------------------------
	private static final String LN = System.getProperty("line.separator", "\n");
	
	private static final String L1 = "Número de elementos: ";
	private static final String L2 = "Número de elementos em cada combinação: ";
	private static final String L3 = "Elementos: ";
	private static final String L4 = "Total de combinações: ";
	
	private static final String HEADER =
"Combinations 1.0"
+ LN +
"Copyright (C) 2005 David Cesarino de Sousa"
+ LN +
"Licenciado sob a licença GNU GPL v2:"
+ LN +
"<https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html>"
+ LN +
"Este programa é livre. Você está livre para modificá-lo ou redistribuí-lo."
+ LN +
"Não há NENHUMA GARANTIA, na extensão permitida pela lei."
+ LN + LN +
"Uso: combinations -g -d \"<separador>\" -k <número> ELEMENTO1 ... ELEMENTOn";
	
	private static final String TIP = 
"Tente --ajuda para maiores detalhes";
	
	private static final String HELP =
"Comandos:"
+ LN + LN +
"--ajuda                    Mostra essa ajuda. Opção ignorada se presente `-g'."
+ LN +
"-g, --gerar                Ativa o gerador de combinações."
+ LN +
"-i, --indexar              Indexa as combinações (só com opção `-g')."
+ LN +
"-r, --relatorio            Mostra relatório (no início se usado com `-g')."
+ LN +
"                           Por precaução, sempre procure mostrar o relatório"
+ LN +
"                           antes de gerar (`-g') as combinações."
+ LN +
"-k, --escolher [número]    Número de elementos escolhidos em cada combinação."
+ LN +
"-d, --delimitador [texto]  Separa cada elemento da combinação com `texto',"
+ LN +
"                           definido preferencialmente entre aspas ou o que"
+ LN +
"                           seu sistema requerer. Dependendo do sistema, as"
+ LN +
"                           aspas ou similares podem ser opcionais. Se ausente,"
+ LN +
"                           o programa usará hífens (\"-\").";
	// -------------------------------
	// <------------ CONSTANTES
	// -------------------------------

}
