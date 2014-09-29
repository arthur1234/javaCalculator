import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;


public class calculator extends JFrame {
	 
	 //components to  create buttons 
     private Component c[] = new Component[16];
     private Component a[] = new Component[3];

     private String[] btn = {
		"AC", "+/-", "%", "/",
		"7", "8", "9", "*",
		"4", "5", "6", "-",
		"1", "2", "3", "+"
	  };

     private String[] restBtn = { "0", ".", "=" };
     private int i   = 0;
     private int num = 0;
     public JTextField txt1;
     private double aV = 0;
     private double bV = 0;
     private double cV = 0;
     private String strC  = "0";
     private int operator = 0;
     private boolean afterOperation = false;  // after multiply, divide, add OR subtract
     

     // if command done
     private boolean deciding = false;

     private int temp=0;

      public calculator() {
          super("Calculator");
          createGUI();
     }

     public void createGUI() {
	
	  
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          JPanel panel = new JPanel();
          panel.setLayout(null);


		  txt1 = new JTextField(Integer.toString(num));
		  txt1.setBounds(0, 10, 300, 30);

		  panel.add(txt1);
		

	
	  	for(i = 0; i < c.length/4; i++){
			c[i] = new JButton(btn[i]);
			c[i].setBounds(51*i,50, 50,30);

			c[i].addMouseListener(new MouseL(i));
			panel.add(c[i]);

			c[i+4] = new JButton(btn[i+4]);
			c[i+4].setBounds(51*i,81, 50,30);
			c[i+4].addMouseListener(new MouseL(i+4));

			panel.add(c[i+4]);

			c[i+8] = new JButton(btn[i+8]);
			c[i+8].setBounds(51*i,112, 50,30);
			c[i+8].addMouseListener(new MouseL(i+8));

			panel.add(c[i+8]);

			c[i+12] = new JButton(btn[i+12]);
			c[i+12].setBounds(51*i,143, 50,30);
			c[i+12].addMouseListener(new MouseL(i+12));

			panel.add(c[i+12]);
	  	}

		  a[0] = new JButton(restBtn[0]);
		  a[0].setBounds(0, 174, 101, 30);	
		  a[0].addMouseListener(new MouseL(100));
		  panel.add(a[0]);
			
		  a[1] = new JButton(restBtn[1]);
		  a[1].setBounds(102, 174, 50, 30);
		  a[1].addMouseListener(new MouseL(101));
		  panel.add(a[1]);

		  a[2] = new JButton(restBtn[2]);
		  a[2].setBounds(153, 174, 50, 30);
		  a[2].addMouseListener(new MouseL(102));
		  add(a[2]);
	
	

          getContentPane().add(panel);

          setPreferredSize(new Dimension(485, 345));
    }


     public static void main(String[] args) {
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    calculator frame = new calculator();
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
       
     }

	class MouseL implements MouseListener  {

		private int index1;
		private String btnValue;
 
		public MouseL(int index){
			index1 = index;	
		}

		public void mouseClicked(MouseEvent event) {

			// show entered numbers
			if((index1 > 3 && index1 <7) || (index1 >7 && index1 <11) || (index1 >11 && index1 < 15) || (index1 == 100 && Double.parseDouble(txt1.getText()) != 0 ))
			{
				
				String inputedValue = "";
				String inputedValueSTR = "";

				int strIndex = 0;

				inputedValue  = txt1.getText().toString();
				
			  
			  	if(inputedValue.indexOf(".") > 0)
			  	{
					strIndex = inputedValue.indexOf(".");

				    inputedValueSTR = inputedValue.substring(0, strIndex);

			  	}
				// length of number < 9
				if(inputedValueSTR.length() <9 ) { 
					if(inputedValue.equals("0") || afterOperation){
						
						if(index1 == 100 ) {
							// if first was typed zero, value stay be zero
							btnValue = 	"0";
						} else {
							btnValue = 	btn[index1].toString();
						}
						if(afterOperation) afterOperation = false;
						
					}					
					else {

						if(index1 == 100) {
							btnValue = 	inputedValue + "0";
						} else {

							btnValue = 	inputedValue + btn[index1].toString();
						}
						
					}	
					txt1.setText(btnValue);
					
				}

				else{
					JOptionPane.showMessageDialog(null, "This number is too big :(", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(index1 == 101){
					int count = countOccurrences(txt1.getText().toString(),'.');
				    
				    if(count <1)
						txt1.setText(txt1.getText().toString()+".");
			}
			else if(index1 == 100 && Double.parseDouble(txt1.getText()) == 0 ){
					int count = countOccurrences(txt1.getText().toString(),'.');
				    
				    if(count > 0)
						txt1.setText(txt1.getText().toString()+"0");
			}


			// 1=/ 2=* 3=- 4=+ 5== 6=% 
			switch(index1){
				case(0): 
					txt1.setText("0");
					aV = 0;
					bV = 0;
					cV = 0;
					operator = 0;
					break;
				case(1):
					// change a +/- of a number
					if(Double.parseDouble(txt1.getText()) == 0) break;
					if(Double.parseDouble(txt1.getText()) > 0)
					{
						txt1.setText("-"+txt1.getText().toString());
					}
					else{
						txt1.setText(txt1.getText().substring(1))	;
					}

					if(aV != 0){
						bV = Double.parseDouble(txt1.getText());
					}
					
					break;

				case(2):
					/// result 1% of number

					String percent = fmt(Double.parseDouble(txt1.getText())/100);
					txt1.setText(percent);
				break;
				case(3):
					// devide
					calculate(3);
					break;
				case(7):
					// multiply
					calculate(7);
					break;
				case(11):
					// subtract
					calculate(11);
					break;
				case(15):
					// add 
					calculate(15);
					break;
				case(102):
					// return result
					calculate(102);
					break;

			}
			

		
		}
 
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {} 
		public void mouseReleased(MouseEvent event) {}
 
	}

	public void calculate(int operator){
		if( !afterOperation && this.operator != 0 ) {
			bV = Double.parseDouble(txt1.getText());
			afterOperation = true;

			switch(this.operator){
				case(3):
					cV = aV/bV;
				break;
				case(7):
					cV = aV*bV;
				break;
				case(11):
					cV = aV-bV;
				break;
				case(15):
					cV = aV+bV;
				break;
			}

			
			strC = fmt(cV);
			txt1.setText(strC);
			aV = Double.parseDouble(txt1.getText());
			bV = 0;
			cV = 0;
			if(operator == 102)
				this.operator = 0;
			else
				this.operator = operator;
			
		}

		else if(this.operator != operator && this.operator != 0 && afterOperation){
			this.operator = operator;
			
		}
		else if(this.operator == 0 ){
			aV = Double.parseDouble(txt1.getText());
			this.operator = operator;
			afterOperation = true;
			
		}

	}



	// create string from Double without .0 in end of numeral
	public static String fmt(double d)
	{
	    if(d == (long) d)
	        return String.format("%d",(long)d);
	    else
	        return String.format("%s",d);
	}

	public static int countOccurrences(String haystack, char needle)
	{
	    int count = 0;
	    for (int i=0; i < haystack.length(); i++)
	    {
	        if (haystack.charAt(i) == needle)
	        {
	             count++;
	        }
	    }
	    return count;
	}




}




