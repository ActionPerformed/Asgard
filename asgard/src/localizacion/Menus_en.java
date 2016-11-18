package localizacion;

import java.util.*;

/**
 * Clase que contiene la la localización al inglés del juego
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class Menus_en extends ListResourceBundle
{
    @Override
    public Object[][] getContents()
    {
       return contenido;
    }

    private final Object[][] contenido = {   
       
       //OBJETOS
        { "libreria1", "All these books are about fishing."},
        { "libreria2", "\"Make your own rigging\" hmmm I'm not sure about this......"},
        { "libreria3", "\"Me, my yatch and you, filthy people\", by Pérez Reverte. I'll read this, someday."},
        { "libreria4", "\"Do you stink as a fish? Remove it!\". I shouldn't touch this book."},
        { "libreria5", "\"Funny pics of accidents with fish-hooks\". This seems to be... to be... OMG plz no!"},
        { "libreria6", "\"setup & setup\" .It seems a technical book. Oh wait, I'm reading it in the wrong way..."},
        
        { "cartel_casa_9001", "Social hall of Asgard."},
        { "cartel_casa_9002", "Local store. We'll be back the 17th!"},
        { "cartel_casa_9003", "Local laundry."},
        { "cartel_casa_9004", "(this is stroke)'s home. Asgard Loan House's property, NO TRESPASSING."},
        { "cartel_casa_9005", "Welcome to my home!"},
       
       //NPCS
        { "npc900102", "My clothes were white and red... and now they are pink! it's Sarkis' fault!"},
        { "npc900101", "This is the library, in Asgard people like reading very much"},
        { "npc900103", "Have you seen these books? Their text changes when you choose a new language! It's the same with people."},
        { "npc900104", "If you like reading, this is your place. But if you prefer going fishing, you should go out."},
       
        { "npc102101", "Welcome to Asgard, outlander! Asgard is a peaceful and quiet place, so don't get afraid of interacting with everything. Our homes are yours!"},
        { "npc102102", "Do you want to purchase a fishing rod, too? The merchant is on holidays until the 17th of July, bad luck!"},
       
        { "npc102201", "This week my husband do the house work. We are a modern family!"},
      
        { "npc900301", "Our home? Oh my god, no! I'm Sarkis' brother-in-law, I've come only for eating something"},
        { "npc900302", "My brother is missing since he spoiled all the male town clothes. I hope he's fine!"},
       
        { "npc900401", "Loan house is selling all these books at a very low price. It's a real bargain!"},
        { "npc900402", "The old owners? I don't know, this is a property of Asgard Loan House and you shouldn't be here"},
        
        { "npc103301", "If you want to visit the rest of Asgard, I'm sorry: the path is closed due to a rock slide and we are working on repairing and clearing it"},
        
        { "npc103201", "The girl who lives in that island is very pretty."},
        { "npc103202", "The land, that girl... Â¡everything that surround you! Something so perfect can only be designed by a god"},
       
        { "npc103101", "The owner of this house ir very funny, has painted his closed door in black!"},
        
        { "npc104001", "I used to live in the hills... but Asgard Loan House seized my home"},
        { "npc104002", "Are you looking for fishing advices? Loki is a great expert, but I'm afraid you won't be able to speak with him until the path is cleared"},
        { "npc104003", "If you want to fish, it's easy: shake your gadget as if it were a fishing rod!"},
        { "npc104004", "This place is very warm, that's why we are here!"},
        
        { "npc102001", "Please don't hit me! Oh, wait... your clothes aren't pink, you are new in the city! Please, don't tell anybody where I am!"},
        
       
        //INTERFACE 
        { "volver_juego", "Continue playing"},
        { "opciones", "Options"},
        { "salir", "Exit"},
        { "idioma", "Language"},
        { "es", "Spanish"},
        { "en", "English"},
        { "aceptar", "OK"},
        { "cancelar", "Cancel"},
        { "guardar", "Save"},
        { "volver", "Return"},
           
        //OTROS
        { "agua", "Grab your rod!"}    
   };
} 
