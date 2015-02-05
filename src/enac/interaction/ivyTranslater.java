package enac.interaction;

/**
 * Yet another Ivy java program example
 *
 * This is the example from the documentation
 *
 * @author Yannick Jestin <jestin@cena.fr>
 *
 * (c) CENA
 *
 * This program is distributed as is
 *
 */
import fr.dgac.ivy.* ;
import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

class ivyTranslater implements IvyMessageListener {
      String form="";

  private Ivy bus;
  enum State {

        INIT,FICAR,SPOIN,CREE
    }
    State sb;
  

  ivyTranslater() throws IvyException {
    // initialization, name and ready message
    bus = new Ivy("IvyTranslater","",null);
        bus.start("127.255.255.255:2010");
    sb=sb.INIT;
        

  }
 
    public void getIvyTransMsg() throws IvyException{
        bus.bindMsg("^IvyTranslater(.*)" ,new IvyMessageListener() {
      public void receive(IvyClient client, String[] args) {
         
          
    }});
    }
    
      
            
    // starts the bus on the default domain
  

  // callback associated to the "Hello" messages"
  

       
  public static void main(String args[]) throws IvyException, AWTException {
    ivyTranslater e =new ivyTranslater();
    InteractionIcarPointage x=new InteractionIcarPointage();
   x.run();
  }

    @Override
    public void receive(IvyClient ic, String[] strings) {
     
    }
}
