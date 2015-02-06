package com.polytech.cluedo;

import android.graphics.Color;

/**
 * Created by Fujitsu on 30/01/2015.
 */
public class IdentifyCard {

    public IdentifyCard(){

    }

    public int findImage(String elementName){
        int result = 0;
        if(elementName.equals("Rose")){
            result = R.drawable.rose;
        }
        else if(elementName.equals("Pervenche")){
            result = R.drawable.pervenche;
        }
        else if(elementName.equals("Moutarde")){
            result = R.drawable.moutarde;
        }
        else if(elementName.equals("Olivier")){
            result = R.drawable.olive;
        }
        else if(elementName.equals("Leblanc")){
            result = R.drawable.leblanc;
        }
        else if(elementName.equals("Violet")){
            result = R.drawable.violet;
        }

        else if(elementName.equals("Corde")){
            result = R.drawable.corde;
        }
        else if(elementName.equals("Cle anglaise")){
            result = R.drawable.cleanglaise;
        }
        else if(elementName.equals("Chandelier")){
            result = R.drawable.chandelier;
        }
        else if(elementName.equals("Barre de fer")){
            result = R.drawable.barredefer;
        }
        else if(elementName.equals("Revolver")){
            result = R.drawable.revolver;
        }
        else if(elementName.equals("Poignard")){
            result = R.drawable.poignard;
        }

        else if (elementName.equals("Entree")){
            result = R.drawable.entree;
        }
        else if (elementName.equals("Salle a manger")){
            result = R.drawable.salleamanger;
        }
        else if (elementName.equals("Cuisine")){
            result = R.drawable.cuisine;
        }
        else if (elementName.equals("Salon")){
            result = R.drawable.salon;
        }
        else if (elementName.equals("Chambre")){
            result = R.drawable.chambre;
        }
        else if (elementName.equals("Garage")){
            result = R.drawable.garage;
        }
        else if (elementName.equals("Bureau")){
            result = R.drawable.bureau;
        }
        else if (elementName.equals("Salle de jeux")){
            result = R.drawable.salledejeux;
        }
        else if (elementName.equals("Salle de bains")){
            result = R.drawable.salledebains;
        }

        else if (elementName.equals("SALLEDEBAIN")){
            result = R.drawable.salledebainbg;
        }
        else if(elementName.equals("GARAGE")){
            result = R.drawable.garagebg;
        }
        else if(elementName.equals("SALLEDEJEUX")){
            result = R.drawable.salledejeuxbg;
        }
        else if(elementName.equals("CHAMBRE")){
            result = R.drawable.chambrebg;
        }
        else if(elementName.equals("CUISINE")){
            result = R.drawable.cuisinebg;
        }
        else if(elementName.equals("SALLEAMANGER")){
            result = R.drawable.salleamangerbg;
        }
        else if(elementName.equals("SALON")){
            result = R.drawable.salonbg;
        }
        else if(elementName.equals("ENTREE")){
            result = R.drawable.entreebg;
        }
        else if(elementName.equals("BUREAU")){
            result = R.drawable.bureaubg;
        }
        else if(elementName.equals("HALL")){
            result = R.drawable.hallbg;
        }


        //result = R.drawable.corde;
        return result;
    }

    public int findColor(String nomPerso){
        int result = 0;
        if (nomPerso.equals("Rose")){
            result = Color.RED;
        }
        else if (nomPerso.equals("Violet")){
            result = Color.rgb(128,0,255);
        }
        else if (nomPerso.equals("Pervenche")){
            result = Color.CYAN;
        }
        else if (nomPerso.equals("Olive")){
            result = Color.GREEN;
        }
        else if (nomPerso.equals("Leblanc")){
            result = Color.WHITE;
        }
        else if (nomPerso.equals("Moutarde")){
            result = Color.rgb(191,191,0);
        }
        return result;
    }
}
