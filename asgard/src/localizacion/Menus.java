package localizacion;

/**
 * Clase que contiene la la localización al español del juego
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
import java.util.*;

public class Menus extends ListResourceBundle
{
    @Override
    public Object[][] getContents()
    {
       return contenido;
    }
    
    private final Object[][] contenido = {      
       
       //OBJETOS
        { "libreria1", "Todos estos libros son de pesca."},
        { "libreria2", "\"Fabrica tus propios aparejos desde cero\" mmmm no se yo..."},
        { "libreria3", "\"Yo, mi yate y la chusma que me rodeáis\", por Pérez Reverte. Tengo que ver cuándo lo leo."},
        { "libreria4", "\"Cómo quitarse el olor a pescado\". No sé si tocarlo siquiera..."},
        { "libreria5", "\"Divertidas fotos de accidentes con anzuelos\". Esto de la portada parece... parece... ¡oh no, por dios!"},
        { "libreria6", "\"setup & setup\" .Parece un libro de informát... eh, espera, estoy leyendo el título al revés..."},
        
        { "cartel_casa_9001", "Centro social de Asgard."},
        { "cartel_casa_9002", "Tienda local. Estamos de vacaciones hasta el 17"},
        { "cartel_casa_9003", "Tintorería local."},
        { "cartel_casa_9004", "Casa de (esto aparece tachado). Propiedad de la casa de préstamos de Asgard, NO PASAR."},
        { "cartel_casa_9005", "¡Bienvenido a mi casa!"},
       
       //NPCS
        { "npc900102", "Mi ropa antes era blanca y roja... ¡y ahora es rosa! ¡por culpa de Sarkis!"},
        { "npc900101", "Esta es la biblioteca del pueblo, en Asgard nos gusta mucho leer"},
        { "npc900103", "¿Has visto estos libros? ¡Su texto cambia según el idioma que elijas! Con la gente pasa lo mismo, fijate."},
        { "npc900104", "Si te gusta leer, este es tu sitio. Pero si prefieres pescar, deberias ir a donde haya agua."},
       
        { "npc102101", "¡Bienvenido a Asgard, forastero! Asgard es un sitio muy pacifico y tranquilo, asi que no tengas miedo de interactuar con cualquier cosa que veas. Â¡Y entra en nuestras casas como si fueran tuyas!"},
        { "npc102102", "Â¿También vienes a comprar una caña? Parece que el tendero está de vacaciones y no viene hasta el 17 de junio. ¡Tambien es mala pata!"},
       
        { "npc102201", "Esta semana mi marido se encarga de las tareas del hogar. ¡Aqui nos repartimos el trabajo!"},
      
        { "npc900301", "Â¿Nuestra casa? ¡No, no, ni mucho menos! Yo soy el cuñado de Sarkis, he venido de merienda"},
        { "npc900302", "Mi hermano ha desaparecido desde que tiñó toda la ropa de la ciudad de rosa. ¡Espero que no le haya pasado nada malo!"},
       
        { "npc900401", "La casa de préstamos vende estos libros a bajo precio. ¡Son una ganga!"},
        { "npc900402", "¿Los antiguos dueños de la casa? Ni idea, ahora esto es propiedad de la casa de préstamos, y TÚ no deberías estar aquí."},
        
        { "npc103301", "Si tu objetivo era visitar el resto de Asgard, siento desilusionarte. Un alud ha cerrado el camino y nos llevará varios días despejar el terreno."},
        
        { "npc103201", "La chica que vive en esa isla es muy guapa."},
        { "npc103202", "El paisaje, esta chica... ¡todo lo que te rodea! Algo tan perfecto sólo puede ser obra de Dios."},
       
        { "npc103101", "El dueño de la casa es un cachondo, ¡ha cerrado la puerta y la ha pintado de negro!"},
        
        { "npc104001", "Yo antes vivía en la colina, ¡pero la casa de préstamos me embargó mi hogar!"},
        { "npc104002", "Â¿Buscas consejos para pescar? Loki es un gran experto, pero me temo que, hasta que no despejen el camino, no podrás hablar con él"},
        { "npc104003", "Si quieres pescar, es sencillo: ¡agita tu dispositivo como quien lanza una caña!"},
        { "npc104004", "Este claro del bosque es un sitio muy recogido, ¡por eso estamos aquí!"},
        
        { "npc102001", "¡No me pegues! Ah, espera... ¡tu ropa no es rosa, eres nuevo en la ciudad! ¡No le digas a nadie dónde estoy! ¡Por favor!"},
        
        //INTERFACE 
        { "volver_juego", "Volver al juego"},
        { "opciones", "Opciones"},
        { "salir", "Salir"},
        { "idioma", "Idioma"},
        { "es", "Espanol"},
        { "en", "Ingles"},
        { "aceptar", "Aceptar"},
        { "cancelar", "Cancelar"},
        { "guardar", "Guardar"},
        { "volver", "Volver"},

        //OTROS
        { "agua", "¡Prepara tu caña!"},   
        { "lanzamiento_inicio", "¡Guau! ¡"},
        { "lanzamiento_fin", "m de lanzamiento!"},   
        { "pez_inicio", " !Y has pescado una pieza de "},
        { "pez_fin", " kilos!"}   
   };
} 
