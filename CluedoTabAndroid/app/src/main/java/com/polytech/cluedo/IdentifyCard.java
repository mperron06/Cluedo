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
        if(elementName.equals("Rose")||elementName.equals("rose")){
            result = R.drawable.rose;
        }
        else if(elementName.equals("Pervenche")||elementName.equals("pervenche")){
            result = R.drawable.pervenche;
        }
        else if(elementName.equals("Moutarde")||elementName.equals("moutarde")){
            result = R.drawable.moutarde;
        }
        else if(elementName.equals("Olive")||elementName.equals("olive")){
            result = R.drawable.olive;
        }
        else if(elementName.equals("Leblanc")||elementName.equals("leblanc")){
            result = R.drawable.leblanc;
        }
        else if(elementName.equals("Violet")||elementName.equals("violet")){
            result = R.drawable.violet;
        }

        else if(elementName.equals("Corde")||elementName.equals("corde")){
            result = R.drawable.corde;
        }
        else if(elementName.equals("Cle anglaise")||elementName.equals("cle anglaise")||elementName.equals("cleanglaise")){
            result = R.drawable.cleanglaise;
        }
        else if(elementName.equals("Chandelier")||elementName.equals("chandelier")){
            result = R.drawable.chandelier;
        }
        else if(elementName.equals("Barre de fer")||elementName.equals("barredefer")||elementName.equals("barre de fer")){
            result = R.drawable.barredefer;
        }
        else if(elementName.equals("Revolver")||elementName.equals("revolver")){
            result = R.drawable.revolver;
        }
        else if(elementName.equals("Poignard")||elementName.equals("poignard")){
            result = R.drawable.poignard;
        }

        else if (elementName.equals("Entree") || elementName.equals("ENTREE")||elementName.equals("entree")){
            result = R.drawable.entree;
        }
        else if (elementName.equals("Salle a manger") || elementName.equals("SALLEAMANGER")||elementName.equals("salleamanger")||elementName.equals("salle a manger")){
            result = R.drawable.salleamanger;
        }
        else if (elementName.equals("Cuisine") || elementName.equals("CUISINE")||elementName.equals("cuisine")){
            result = R.drawable.cuisine;
        }
        else if (elementName.equals("Salon") || elementName.equals("SALON")||elementName.equals("salon")){
            result = R.drawable.salon;
        }
        else if (elementName.equals("Chambre") || elementName.equals("CHAMBRE")||elementName.equals("chambre")){
            result = R.drawable.chambre;
        }
        else if (elementName.equals("Garage") || elementName.equals("GARAGE")||elementName.equals("garage")){
            result = R.drawable.garage;
        }
        else if (elementName.equals("Bureau") || elementName.equals("BUREAU")||elementName.equals("bureau")){
            result = R.drawable.bureau;
        }
        else if (elementName.equals("Salle de jeux") || elementName.equals("SALLEDEJEUX")||elementName.equals("salledejeux")||elementName.equals("salle de jeux")){
            result = R.drawable.salledejeux;
        }
        else if (elementName.equals("Salle de bains") || elementName.equals("SALLEDEBAIN")||elementName.equals("salle de bains")||elementName.equals("salledebains")){
            result = R.drawable.salledebains;
        }

        //result = R.drawable.corde;
        return result;
    }

    public int findBigImage(String elementName){
        int result = 0;
        if (elementName.equals("SALLEDEBAIN")){
            result = R.drawable.salledebainbg;
        }
        else if(elementName.equals("GARAGE")){
            result = R.drawable.garagebg;
        }
        else if(elementName.equals("SALLEDEJEUX")){
            //result = R.drawable.salledejeuxbg;
            result = R.drawable.hallbg;
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
        else if(elementName.equals("Salon")){
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
