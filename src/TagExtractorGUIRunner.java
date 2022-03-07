import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class TagExtractorGUIRunner extends JFrame
{
    private MapFrequency mapFrequency;

    ArrayList<String> wordList = new ArrayList<>();

    JPanel mainPnl;

    JPanel displayPnl;
    JPanel controlPnl;

    //displaying
    JTextArea displayTA;
    JScrollPane scroller;

    JButton filterBtn;
    JButton saveBtn;
    JButton clearBtn;
    JButton quitBtn;

    public TagExtractorGUIRunner()
    {
        mapFrequency = new MapFrequency();

        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2,1));

        createDisplayPanel();
        mainPnl.add(displayPnl);

        createControlPanel();
        mainPnl.add(controlPnl);

        add(mainPnl);
        setSize(600,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createDisplayPanel()
    {
        displayPnl = new JPanel();
        displayTA = new JTextArea(18,60);
        displayTA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scroller = new JScrollPane(displayTA);
        displayPnl.add(scroller);
    }

    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        filterBtn = new JButton("Filter Text");
        controlPnl.add(filterBtn);
        filterBtn.addActionListener((ActiveEvent_ae) ->
        {
            filterBtnAction();
        });

        saveBtn = new JButton("Save Words");
        controlPnl.add(saveBtn);
        saveBtn.addActionListener((ActiveEvent_ae) ->
        {
            saveBtnAction();
        });

        clearBtn = new JButton("Clear List");
        controlPnl.add(clearBtn);
        clearBtn.addActionListener((ActiveEvent_ae) ->
        {
            clearBtnAction();
        });


        quitBtn = new JButton("Quit");
        controlPnl.add(quitBtn);
        quitBtn.addActionListener((ActiveEvent_ae) ->
        {
            quitBtnAction();
        });
    }

    private void filterBtnAction()
    {
        String[] split = String.valueOf(mapFrequency.getWordCount()).split(",");
        for(int i=0; i < split.length; i++)
        {
            wordList.add(split[i]);
            displayTA.append(split[i] + "\n");
        }
    }

    private void saveBtnAction()
    {
        String displayedText = displayTA.getText();
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\WordList.txt");
        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(String rec : wordList)
            {
                writer.write(rec);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void clearBtnAction()
    {
        displayTA.selectAll();
        displayTA.replaceSelection("");
        wordList.clear();
    }

    private void quitBtnAction()
    {
        int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Select an Option", JOptionPane.YES_NO_CANCEL_OPTION);
        if(input == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

}
