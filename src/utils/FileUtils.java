package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {
 
    public static String[] readShaderSource( String filename ) {

        Path path = Paths.get( filename ).toAbsolutePath().normalize();
        try{
            List<String> lines = Files.readAllLines( path );

            return lines.parallelStream()
                        .map( line -> line+=" \n" )
                        .toList()
                        .toArray( new String[]{} ); 
        }
        catch( IOException ex ) { 
            ex.printStackTrace();
        }

        return new String[]{};
    }
}