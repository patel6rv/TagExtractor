import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;

public class SetFilter
{
    private Set<String> wordFilter = new HashSet<>();

    public SetFilter() {
        createFilter();
    }

    public boolean contains(String key)
    {
        return wordFilter.contains(key);
    }

    private void createFilter()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        try {
                InputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\rmp12\\IdeaProjects\\TagExtractor\\src\\EnglishStopWords.txt"));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while(reader.ready())
                {
                    wordFilter.add(reader.readLine());
                }
                reader.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}
