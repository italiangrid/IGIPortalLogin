package portal.login.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateWorkflow {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Devi mettere il numero di job da creare");
			return;
		}

		int nwf = Integer.parseInt(args[1]);

		System.out.println("Numero workflow = " + nwf);

		

		FileWriter fstream = new FileWriter("workflow.xml");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<workflow download=\"abst\" export=\"work\" mainabst=\"Test_"
				+ nwf
				+ "_job\" maingraf=\"Test_"
				+ nwf
				+ "_job\" mainreal=\" name=\"Test_"
				+ nwf
				+ "_job\">\n<graf name=\"Test_"
				+ nwf
				+ "_job\" text=\"Description of Graph\">\n");
		for (int i = 0; i < nwf; i++) {
			out.write("<job name=\"Job"+i+"\" text=\"Description of Job\" x=\"10\" y=\""+(10 + i*20)+"\"/>\n");
		}
		out.write("</graf>\n<abst graf=\"Test_"
				+ nwf
				+ "_job\" name=\"Test_"
				+ nwf
				+ "_job\" text=\"2011-10-19\">\n");
		
		for (int i = 0; i < nwf; i++) {
			out.write("<job name=\"Job"+i+"\" text=\"Description of Job\" x=\"10\" y=\""+(10 + i*20)+"\">\n" +
					"<execute desc=\"\" inh=\"---\" key=\"binary\" label=\"\" value=\"C:/fakepath/test.sh\"/>\n" +
					"<execute desc=\"\" inh=\"---\" key=\"gridtype\" label=\"\" value=\"glite\"/>\n" +
					"<execute desc=\"\" inh=\"---\" key=\"jobistype\" label=\"\" value=\"binary\"/>\n" +
					"<execute desc=\"\" inh=\"---\" key=\"grid\" label=\"\" value=\"gridit\"/>\n" +
					"<execute desc=\"\" inh=\"---\" key=\"type\" label=\"\" value=\"Sequence\"/>\n" +
					" </job>\n");
		}
		out.write("</abst>\n</workflow>");
		out.close();
		
		File location = new File("Test_"+ nwf + "_job");
		if (!location.exists()) {
			location.mkdirs();
		}
		
		
		for (int i = 0; i < nwf; i++) {
			location = new File("Test_"+ nwf + "_job/Job"+i);
			location.mkdirs();
			
			fstream = new FileWriter("Test_"+ nwf + "_job/Job"+i+"/execute.bin");
			out = new BufferedWriter(fstream);
			out.write("#!/bin/sh\necho \"Hello World\"");
			out.close();
			
		}
	}
}
