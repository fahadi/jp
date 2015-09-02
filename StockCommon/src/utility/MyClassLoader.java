package utility;

import java.util.logging.Level;
import java.util.logging.Logger;



public class MyClassLoader {
	
	static public Object loadClass(String classDetail){
		  
        try {
             return (Object) Class.forName(classDetail).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
	return null;
	}
	
}
