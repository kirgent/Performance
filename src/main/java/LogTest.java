import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class LogTest {
    private Logger logger;

    private void initLog() {
        try {
            logger = Logger.getLogger (LogTest.class.getName ());
            FileHandler xmlFile = new FileHandler ("log.xml", true);
            logger.addHandler (xmlFile);


        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }

    private void testMsg() {
        logger.log (Level.FINEST,"Finest level!");
        logger.log (Level.WARNING,"Warning level!");
        logger.log (Level.SEVERE,"Severe level!");
    }

    public static void main (String [] args) {
        LogTest l = new LogTest ();
        l.initLog ();

        l.logger.setLevel (Level.SEVERE);
        l.testMsg ();

        l.logger.setLevel (Level.WARNING);
        l.testMsg ();

        l.logger.setLevel (Level.FINEST);
        l.testMsg ();
    }
}