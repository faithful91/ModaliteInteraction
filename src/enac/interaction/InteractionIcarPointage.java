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
    @Override
    public void receive(IvyClient ic, String[] strings) {
    }

   

    
  enum State 
  {
    //etat initiale
//etat initiale
//etat initiale
//etat initiale
//etat initiale
//etat initiale
//etat initiale
//etat initiale
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
    //le timer est appelé aprés avoir detecter la forme il est lancé pour attenre un click ou un pointage
    //de l'utilisateur sinon il s"excute est crée la forme sur la palette tous seul, si l'utilisateur a clické 
    //ou pointer alr le timer va etre arreter.
    
    ActionListener task = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          System.out.println("timer");
                //si l'utilisateur a clicker ou pointer il va choisir à quelle endroit la forme sera créer
               
          if (!isClicked)
                       
                //la forme va etre cree à un emplacement décaller 50 px a la derniere forme creer par le timer
                 
                        {   System.out.println("3afatlo"+form);
                            try {
                                   bus.unBindMsg(busGetPointClick); 
                bus.sendMsg(form+" x="+posx+" y="+posy+" couleurFond="+couleur);

                                posx+=50;
                                posy+=50;
                               
                                sb=sb.CREE;
                                activation();
                            } catch (IvyException ex) {Logger.getLogger(InteractionIcarPointage.class.
                                                            getName()).log(Level.SEVERE, null, ex);
                                                       } catch (AWTException ex) {
                  Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
              }
                             t.stop();
                        }
          
}
    };
        //initiation du timer
      Timer t = new Timer(delay,task);
      
      
      
      
      
      ActionListener taskCouleur = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          System.out.println("timercouleur");
                //si l'utilisateur a clicker ou pointer il va choisir à quelle endroit la forme sera créer
                if (isClicked)
                        {
                                   
                               
                        }
                //sinon la forme va etre cree à un emplacement décaller 50 px a la derniere forme creer par le timer
                else 
                        {   System.out.println("3afatlo"+form);
                            try {
                                   bus.unBindMsg(busGetPointClick); 
                
                                   bus.sendMsg(form+" x="+x+" y="+y+"C");
                                   System.out.println(form+" x="+x+" y="+y);
                                posx+=50;
                                posy+=50;
                                sb=sb.CREE;
                                activation();
                            } catch (IvyException ex) {Logger.getLogger(InteractionIcarPointage.class.
                                                            getName()).log(Level.SEVERE, null, ex);
                                                       } catch (AWTException ex) {
                  Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
              }
                             t.stop();
                        }
}
    };
        //initiation du timer
      Timer tcouleur = new Timer(delay,taskCouleur);
    
    public InteractionIcarPointage() throws IvyException, AWTException
    {   bus = new Ivy("IvyTranslater","",null);
         bus.start("127.255.255.255:2010");
        sb=sb.INIT;
        init();
        
        
        
    }
     public void activation() throws IvyException, AWTException 
    {  
        switch (sb) 
        {
            case INIT:{init();System.out.println("ena fi init");getForm();}break;
            case FORM:{getPosVoice();getClickPosition();t.start();System.out.println("TIMER START");  }break;
            case CPOS:{t.stop();System.out.println("i create with click");createForm();}break;
            case PPOS:{t.stop();System.out.println("i create with voice");createForm();}break;
            case COLOR:{System.out.println("ena fi color");createForm();}break;
            case CREE:{System.out.println("je suis creer");etat();}break;
        }
    }
    
   private void etat() throws IvyException, AWTException {
        sb=sb.INIT;activation();  
        
        
       }
    private void init() throws IvyException {
        isClicked=false;
        iGetVoiceIci=false;
        couleur="";
        getForm();

    }
     
    
    public void getForm() throws IvyException
       {
        bus.bindMsgOnce("^ICAR (.*)",new IvyMessageListener() 
           {
          public void receive(IvyClient client, String[] args) 
                {   
                    switch (sb) 
                        {   case INIT:
                                {   System.out.println(args[0]);
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
                                    sb=sb.CPOS;
                                        try {
                                            activation();
                                        } catch (IvyException ex) {
                                            Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (AWTException ex) {
                                            Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                }break;
                            case CPOS:break;
                            case PPOS:
                                {   x=args[0];
                                    y=args[1];
                                    sb=sb.PPOS;
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
    
    public void createForm() throws AWTException
        {
          try 
            {  
                System.out.println(form+" x="+x+" y="+y);
                bus.sendMsg(form+" x="+x+" y="+y+" couleurFond="+couleur);
                isClicked=false;
                sb=sb.CREE;
                activation();


            } catch (IvyException ex) 
                {
                    Logger.getLogger(ivyTranslater.class.getName()).log(Level.SEVERE, null, ex);
                }   
        }
        
  public void getPosVoice() throws IvyException, AWTException{
        Robot r=new Robot();
        bus.bindMsg("^sra5 Parsed=(.*) Confidence=.*",new IvyMessageListener() 
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
                                            }
                                            sb=sb.PPOS;
                                                try {
                                                    activation();
                                                } catch (IvyException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (AWTException ex) {
                                                    Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
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
              bus.bindMsg("^sra5 Parsed=(.*) Confidence=.*",new IvyMessageListener() 

      {
            // callback for "Bye" message
            public void receive(IvyClient client, String[] args) 
                {
                    switch (sb) 
                                  {   case INIT:break;
                                      case FORM:
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
                                        case CPOS:
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

}