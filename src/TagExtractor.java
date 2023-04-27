import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class TagExtractor extends JFrame implements ActionListener
{
    StringBuilder find;
    File f;

    JPanel mainPnl, displayPnl, displayPnl2, controlPnl, middlePnl;
    JTextArea displayTA, displayTA2;
    JTextField searchTextField;

    JScrollPane scroller;

    JButton load, extract, quit;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public TagExtractor ()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createMiddlePnl();
        mainPnl.add(middlePnl,BorderLayout.CENTER);

        createControlPnl();
        mainPnl.add(controlPnl, BorderLayout.SOUTH);



        add(mainPnl);

        searchTextField = new JTextField();

        mainPnl.add(searchTextField, BorderLayout.NORTH);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createMiddlePnl() {

        middlePnl = new JPanel(new GridLayout(2,1));

        displayPnl = new JPanel();
        displayTA = new JTextArea(40, 40);
        displayTA.setEditable(false);
        scroller = new JScrollPane(displayTA);
        displayPnl.add(scroller);
        middlePnl.add(displayPnl);

        displayPnl2 = new JPanel();
        displayTA2 = new JTextArea(40, 40);
        displayTA2.setEditable(false);
        scroller = new JScrollPane(displayTA2);
        displayPnl.add(scroller);
        middlePnl.add(displayPnl2);

    }


    private void createControlPnl()
    {
        controlPnl = new JPanel();
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(),"Controls"));
        load = new JButton("Load my File");
        extract = new JButton("Extract");
        quit = new JButton("Quit");

        load.addActionListener((ActionEvent ae) ->
        {
            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                f = chooser.getSelectedFile();

                try
                {
                    BufferedReader br=new BufferedReader(new FileReader(f));
                    find =new StringBuilder();
                    String line="";
                    line=br.readLine();
                    while(line!=null)
                    {
                        find.append(line);
                        find.append("\n");
                        line=br.readLine();
                    }

                    displayTA.setText(find.toString());

                }
                catch(Exception e)
                {
                    System.err.println(e.toString());
                }
            } else
            {

            }

        });
        controlPnl.add(load);


        extract.addActionListener((ActionEvent ae)-> {
            String sr= searchTextField.getText();
            find.delete(0, find.length());
            try
            {
                BufferedReader br=new BufferedReader(new FileReader(f));

                String line="";
                line=br.readLine();
                while(line!=null)
                {
                    String h[]=line.split(" ");
                    int len=h.length;
                    for(int i=0;i<len;i++)
                    {
                        if(h[i].equalsIgnoreCase(sr))
                        {
                            find.append(line);
                            find.append("\n");
                        }
                    }

                    line=br.readLine();
                }

                displayTA2.setText(find.toString());



            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }

        });



        controlPnl.add(extract);

        quit.addActionListener((ActionEvent ae)-> System.exit(0));
        controlPnl.add(quit);


    }


}