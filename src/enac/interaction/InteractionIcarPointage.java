/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enac.interaction;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Hazem
 */
public class InteractionIcarPointage implements IvyMessageListener {
   int busGetPointClick;
    int posx=0;
  int posy=0;
    String form="";
  private Ivy bus;
  String x;
  String y;
  //savoir si l'utilisateur a cliquer ou pointer sur la palette
  Boolean isClicked=false;
  //savoir si la forme est detecter
  //pour savoir si l'emplacement de la création est detecter par pointage(voix) ou par click 
  boolean iGetVoiceIci=false;
  String couleur="";
  GuideFrame frame;
  	private static ArrayList<Listener> listeners;

    @Override
    public void receive(IvyClient ic, String[] strings) {
    }
    enum State 
  {//etat initiale
      INIT,
     //etat forme connue
      FORM,
      //etat click detecter
     CPOS,
     //etat pointage detecter
     PPOS,
     //etat detecter couleur
     COLOR,
     //etat forme cree sur la palette
     CREE
   }
    State sb;
    //delay pour timer
    int delay = 4000; //milliseconds
    //action du timer 
    //le timer est appelé aprés avoir detecter la forme il est lancé pour attendre un click ou un pointage
    //de l'utilisateur sinon il s"excute et crée la forme sur la palette tous seul, si l'utilisateur a clické 
    //ou pointer alr le timer va etre arreter.
    
    ActionListener task = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          switch (sb) 
                        {   case INIT:break;
                            case FORM:{
                                        System.out.println("je vais creer seul"+form);
                                        try 
                                            {   createForm();
                                                t.stop();
                                            } catch (IvyException ex) {
                                                Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                                      } catch (AWTException ex) { 
                                                Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                } 
                                        }break;
                            case CPOS:{t.stop();System.out.println("tcouleur start");tCouleur.start();}break;
                            case PPOS:{t.stop();System.out.println("tcouleur start");tCouleur.start();}break;
                            case COLOR:{
                                        System.out.println("je vais creer seul avec coleur "+form);
                                        try 
                                            {   createForm();
                                                t.stop();
                                            } catch (IvyException ex) {
                                                Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                                      } catch (AWTException ex) { 
                                                Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                                                                } 
                                        }break;
                            case CREE:break;
                        }
                   
          
 }};
      Timer t = new Timer(delay,task);
      
      
      
      ActionListener taskCouleur = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          System.out.println("timercouleur");
                //si l'utilisateur a clicker ou pointer il va choisir à quelle endroit la forme sera créer
             switch (sb) 
                        {   case INIT:break;
                            case FORM:break;
                            case CPOS:
                            {try {
                                createForm();
                                                } catch (AWTException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IvyException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                tCouleur.stop();
                            }break;
                            case PPOS:
                            {try {
                                createForm();
                                                } catch (AWTException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IvyException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                tCouleur.stop();
                            }break;
                            case COLOR:
                            {try {
                                createForm();
                                                } catch (AWTException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IvyException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                tCouleur.stop();
                            }break;
                            case CREE:break;
                        }
}
    };
        //initiation du timer
      Timer tCouleur = new Timer(delay,taskCouleur);
    
    public InteractionIcarPointage() throws IvyException, AWTException
    {   bus = new Ivy("IvyTranslater","",null);
         bus.start("127.255.255.255:2010");
        sb=sb.INIT;
        init();
       frame = new GuideFrame();
       listeners = new ArrayList<Listener>();
        
        
        
    }
     public void activation() throws IvyException, AWTException 
    {  
        switch (sb) 
        {
            case INIT:{init();updateLabel("lol"); System.out.println("ena fi init");getForm();}break;
            case FORM:{updateLabel("enafigfirm");getColor();getPosVoice();getClickPosition();t.start();System.out.println("TIMER START");}break;
            case CPOS:{t.stop();System.out.println("ena fi CPOS:i have cordonnée with click");}break;
            case PPOS:{t.stop();System.out.println("ena fi PPOS:i have coordonnée with voice");}break;
            case COLOR:{System.out.println("ena fi color");}break;
            case CREE:{updateLabel("kol");System.out.println("je suis creer");etat();}break;
        }
    }
    
   private void etat() throws IvyException, AWTException {
        sb=sb.INIT;activation();  
        
        
       }
    private void init() throws IvyException {
        isClicked=false;
        iGetVoiceIci=false;
        couleur="";
        x=null;
        y=null;}
        
    public void updateLabel(String x)
    {for(Listener listener : listeners){
				listener.textComandChanged(x);
			}
			frame.setCommand(x);
    }
            
    
    
    public void getForm() throws IvyException
       {
        bus.bindMsgOnce("^ICAR (.*)",new IvyMessageListener() 
           {
          public void receive(IvyClient client, String[] args) 
                {   
                    switch (sb) 
                        {   case INIT:
                                {   System.out.println(args[0]+"lol");
                                    switch(args[0])
                                            {
                                                case "Rectangle":form="Palette:CreerRectangle";break;
                                                case "Ellipse":form="Palette:CreerEllipse";break;
                                            }  
                                    System.out.println("i have form");
                                    sb=sb.FORM;try {
                                        activation();
                                    } catch (IvyException ex) {
                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (AWTException ex) {
                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                               }
                                }break;
                            case FORM:break;
                            case CPOS:break;
                            case PPOS:break;
                            case COLOR:break;
                            case CREE:break;
                        } 
                   
                    
                    
              
                }
           });            
       }  
           
          
    public void getClickPosition() throws IvyException
       {
         busGetPointClick=  bus.bindMsgOnce("^Palette:MouseReleased x=(.*) y=(.*)",new IvyMessageListener() 
          
        {
                
            public void receive(IvyClient client, String[] args) 
                {   

                    switch (sb) 
                        {   case INIT:break;
                            case FORM:
                                {   x=args[0];
                                    y=args[1];
                                       if(iGetVoiceIci==true){sb=sb.PPOS;}
                                       else {sb=sb.CPOS; }   
                                }break;
                            case CPOS:break;
                            case PPOS:
                                {   x=args[0];
                                    y=args[1];
                                     
                                }break;
                            case COLOR:break;
                            case CREE:break;
                        }
                } 
        });                            
}
    
    public void createForm() throws AWTException, IvyException
        { switch (sb) 
                        {   case INIT:break;
                            case FORM:
                                {
                                 bus.sendMsg(form+" x="+posx+" y="+posy+" couleurFond="+couleur);
                                 posx+=50;
                                 posy+=50;
                                }break;
                            case CPOS:
                                {System.out.println(form+" x="+x+" y="+y+"lol");
                                    bus.sendMsg(form+" x="+x+" y="+y);
                                }break;
                            case PPOS:
                                {System.out.println(form+" x="+x+" y="+y+"lol");
                                    bus.sendMsg(form+" x="+x+" y="+y);
                                }break;
                            case COLOR:
                                {if((x==null)||(y==null))
                                    {   System.out.println(form+" x="+x+" y="+y);
                                        bus.sendMsg(form+" x="+posx+" y="+posy+" couleurFond="+couleur);
                                        posx+=50;
                                        posy+=50;
                                    }
                                else { System.out.println(form+" x="+x+" y="+y+"lol");
                                       bus.sendMsg(form+" x="+x+" y="+y+" couleurFond="+couleur);
                                     }
                                }break;
                            case CREE:break;
                        }
            sb=sb.CREE;
            activation();
        }
        
  public void getPosVoice() throws IvyException, AWTException{
        Robot r=new Robot();
        bus.bindMsgOnce("^sra5 Parsed=(.*) Confidence=.*",new IvyMessageListener() 
        {
            // callback for "Bye" message
            public void receive(IvyClient client, String[] args) 
                {
                    switch (sb) 
                                  {   case INIT:break;
                                      case FORM:
                                        { System.out.println(args[0]);
                                            if(args[0].equals("Action:creeici"))
                                            {
                                                r.mouseRelease(InputEvent.BUTTON1_MASK);iGetVoiceIci = true;
                                                System.out.println("yes i have voice");
                                                iGetVoiceIci=true;
                                            }
                                        }break;
                                      case CPOS:break;
                                      case PPOS:break;
                                      case COLOR:break;
                                      case CREE:break;
                                  }  
                }
        });
}
   
  
  
  public void getColor() throws IvyException, AWTException{
              bus.bindMsgOnce("^sra5 Parsed=(.*) Confidence=.*",new IvyMessageListener() 

      {
            // callback for "Bye" message
            public void receive(IvyClient client, String[] args) 
                {
                    switch (sb) 
                                  {   case INIT:break;
                                      case FORM:
                                           { System.out.println(args[0]);
                                          try {
                                                switch(args[0])
                                                  {
                                                        case "Action:couleur bleu" :{couleur="blue";System.out.println("GetColor():I get color");sb=sb.COLOR;activation();};break; 
                                                        case "Action:couleur rouge" :{couleur="red";System.out.println("GetColor():I get color");sb=sb.COLOR;activation();};break; 
                                                        case "Action:couleur vert" :{couleur="green";System.out.println("GetColor():I get color");sb=sb.COLOR;activation();};break; 
                                                   }
                                                    
                                                        activation();
                                                    } catch (IvyException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    } catch (AWTException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                            }break;
                                        case CPOS:
                                           { System.out.println(args[0]);
                                          
                                                switch(args[0])
                                                  {
                                                        case "Action:couleur bleu" :{couleur="blue";System.out.println("GetColor():I get color and i have x and y");};break; 
                                                        case "Action:couleur rouge" :{couleur="red";System.out.println("GetColor():I get color and i have x and y");};break; 
                                                        case "Action:couleur vert" :{couleur="green";System.out.println("GetColor():I get color and i have x and y");};break; 
                                                   }
                                                sb=sb.COLOR;
                                                    try {
                                                        activation();
                                                    } catch (IvyException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    } catch (AWTException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                            }break;  
                                        case PPOS:
                                           { System.out.println(args[0]);
                                          
                                                switch(args[0])
                                                  {
                                                        case "Action:couleur bleu" :{couleur="blue";};break; 
                                                        case "Action:couleur rouge" :{couleur="red";};break; 
                                                        case "Action:couleur vert" :{couleur="green";};break; 
                                                   }
                                                sb=sb.COLOR;
                                                    try {
                                                        activation();
                                                    } catch (IvyException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    } catch (AWTException ex) {
                                                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                           }break;
                                      case COLOR:break;
                                      case CREE:break;
                                  }  
                }
        });
  } 
public void addHiroListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

}