var express = require('express');
var app = express();
var httpserver = require('http').Server(app);
var io = require('socket.io').listen(httpserver);

var port = 8080;

app.get('/', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});

// launch the http server on given port
httpserver.listen(port);

console.log("Server listening on *:" + port);

var tableSocket;

var jSockets;

var partieEnCours;

var nbJoueurs;
var joueurs;

var idJoueurs;
var idJoueurActuel;
var idJoueurDepart;
var nbMaxJoueurs;

var nbCases;
var cases;

var nbCartes;
var cartes;
var cartesMilieu;

var nbJoueursPrets;
var nbAssociations;

var termine;

/* fonction random */
function randomInt(mini, maxi) {
    var nb = mini + (maxi + 1 - mini) * Math.random();
    return Math.floor(nb);
}

/* fonction pour mélanger un tableau */
Array.prototype.shuffle = function (n) {
    if (!n)
        n = this.length;
    if (n > 1) {
        var i = randomInt(0, n - 1);
        var tmp = this[i];
        this[i] = this[n - 1];
        this[n - 1] = tmp;
        this.shuffle(n - 1);
    }
}

init();

console.log("Serveur lance !");

function init() {

    console.log("Initialisation");

    joueurs = [];
    tableSocket = null;

    jSockets = [];

    nbJoueurs = 0;

    partieEnCours = false;

    idJoueurs = new Object();

    idJoueurActuel = -1;

    idJoueurDepart = -1;

    cartesMilieu = [];

    nbMaxJoueurs = 6;

    nbCases = 24;
    cases = [];

    cases.push({ id: 0, num: "1.3", nom: "Case", type: "couloir" });
    cases.push({ id: 1, num: "1.4", nom: "Case", type: "couloir" });
    cases.push({ id: 2, num: "1.5", nom: "Case", type: "couloir" });
    cases.push({ id: 3, num: "1.6", nom: "Case", type: "couloir" });
    cases.push({ id: 4, num: "2.1", nom: "Case", type: "couloir" });
    cases.push({ id: 5, num: "2.2", nom: "Case", type: "couloir" });
    cases.push({ id: 6, num: "2.3", nom: "Case", type: "couloir" });
    cases.push({ id: 7, num: "2.4", nom: "Case", type: "couloir" });
    cases.push({ id: 8, num: "2.5", nom: "Case", type: "couloir" });
    cases.push({ id: 9, num: "2.6", nom: "Case", type: "couloir" });
    cases.push({ id: 10, num: "2.7", nom: "Case", type: "couloir" });
    cases.push({ id: 11, num: "2.8", nom: "Case", type: "couloir" });
    cases.push({ id: 12, num: "3.1", nom: "Case", type: "couloir" });
    cases.push({ id: 13, num: "3.2", nom: "Case", type: "couloir" });
    cases.push({ id: 14, num: "3.3", nom: "Case", type: "couloir" });
    cases.push({ id: 15, num: "3.4", nom: "Case", type: "couloir" });
    cases.push({ id: 16, num: "3.5", nom: "Case", type: "couloir" });
    cases.push({ id: 17, num: "3.6", nom: "Case", type: "couloir" });
    cases.push({ id: 18, num: "3.7", nom: "Case", type: "couloir" });
    cases.push({ id: 19, num: "3.8", nom: "Case", type: "couloir" });
    cases.push({ id: 20, num: "4.1", nom: "Case", type: "couloir" });
    cases.push({ id: 21, num: "4.2", nom: "Case", type: "couloir" });
    cases.push({ id: 22, num: "4.3", nom: "Case", type: "couloir" });
    cases.push({ id: 23, num: "4.4", nom: "Case", type: "couloir" });
    cases.push({ id: 24, num: "4.5", nom: "Case", type: "couloir" });
    cases.push({ id: 25, num: "4.6", nom: "Case", type: "couloir" });
    cases.push({ id: 26, num: "4.7", nom: "Case", type: "couloir" });
    cases.push({ id: 27, num: "4.8", nom: "Case", type: "couloir" });
    cases.push({ id: 28, num: "5.1", nom: "Case", type: "couloir" });
    cases.push({ id: 29, num: "5.2", nom: "Case", type: "couloir" });
    cases.push({ id: 30, num: "5.7", nom: "Case", type: "couloir" });
    cases.push({ id: 31, num: "6.1", nom: "Case", type: "couloir" });
    cases.push({ id: 32, num: "6.2", nom: "Case", type: "couloir" });
    cases.push({ id: 33, num: "6.7", nom: "Case", type: "couloir" });
    cases.push({ id: 34, num: "7.1", nom: "Case", type: "couloir" });
    cases.push({ id: 35, num: "7.2", nom: "Case", type: "couloir" });
    cases.push({ id: 36, num: "7.7", nom: "Case", type: "couloir" });
    cases.push({ id: 37, num: "8.1", nom: "Case", type: "couloir" });
    cases.push({ id: 38, num: "8.2", nom: "Case", type: "couloir" });
    cases.push({ id: 39, num: "8.3", nom: "Case", type: "couloir" });
    cases.push({ id: 40, num: "8.4", nom: "Case", type: "couloir" });
    cases.push({ id: 41, num: "8.5", nom: "Case", type: "couloir" });
    cases.push({ id: 42, num: "8.6", nom: "Case", type: "couloir" });
    cases.push({ id: 43, num: "8.7", nom: "Case", type: "couloir" });
    cases.push({ id: 44, num: "9.1", nom: "Case", type: "couloir" });
    cases.push({ id: 45, num: "9.2", nom: "Case", type: "couloir" });
    cases.push({ id: 46, num: "9.3", nom: "Case", type: "couloir" });
    cases.push({ id: 47, num: "9.4", nom: "Case", type: "couloir" });
    cases.push({ id: 48, num: "9.5", nom: "Case", type: "couloir" });
    cases.push({ id: 49, num: "9.6", nom: "Case", type: "couloir" });
    cases.push({ id: 50, num: "9.7", nom: "Case", type: "couloir" });
    cases.push({ id: 51, num: "10.2", nom: "Case", type: "couloir" });
    cases.push({ id: 52, num: "10.3", nom: "Case", type: "couloir" });
    cases.push({ id: 53, num: "10.4", nom: "Case", type: "couloir" });
    cases.push({ id: 54, num: "10.5", nom: "Case", type: "couloir" });
    cases.push({ id: 55, num: "10.6", nom: "Case", type: "couloir" });
    cases.push({ id: 56, num: "10.7", nom: "Case", type: "couloir" });
    cases.push({ id: 57, num: "10.8", nom: "Case", type: "couloir" });
    cases.push({ id: 58, num: "11.1", nom: "Case", type: "couloir" });
    cases.push({ id: 59, num: "11.2", nom: "Case", type: "couloir" });
    cases.push({ id: 60, num: "11.3", nom: "Case", type: "couloir" });
    cases.push({ id: 61, num: "11.4", nom: "Case", type: "couloir" });
    cases.push({ id: 62, num: "11.5", nom: "Case", type: "couloir" });
    cases.push({ id: 63, num: "11.6", nom: "Case", type: "couloir" });
    cases.push({ id: 64, num: "11.7", nom: "Case", type: "couloir" });
    cases.push({ id: 65, num: "11.8", nom: "Case", type: "couloir" });
    cases.push({ id: 66, num: "12.1", nom: "Case", type: "couloir" });
    cases.push({ id: 67, num: "12.2", nom: "Case", type: "couloir" });
    cases.push({ id: 68, num: "12.3", nom: "Case", type: "couloir" });
    cases.push({ id: 69, num: "12.4", nom: "Case", type: "couloir" });
    cases.push({ id: 70, num: "13.1", nom: "Case", type: "couloir" });
    cases.push({ id: 71, num: "13.2", nom: "Case", type: "couloir" });
    cases.push({ id: 72, num: "13.3", nom: "Case", type: "couloir" });
    cases.push({ id: 73, num: "13.4", nom: "Case", type: "couloir" });

    cases.push({ id: 74, num: "0.4", nom: "Entrée", type: "piece" });
    cases.push({ id: 75, num: "0.5", nom: "Entrée", type: "piece" });
    cases.push({ id: 76, num: "2.0", nom: "Garage", type: "piece" });
    cases.push({ id: 77, num: "3.9", nom: "Salon", type: "piece" });
    cases.push({ id: 78, num: "6.0", nom: "Salle de jeux", type: "piece" });
    cases.push({ id: 80, num: "7.8", nom: "Salle à manger", type: "piece" });
    cases.push({ id: 81, num: "11.9", nom: "Cuisine", type: "piece" });
    cases.push({ id: 82, num: "12.0", nom: "Chambre", type: "piece" });
    cases.push({ id: 83, num: "13.5", nom: "Bureau", type: "piece" });
    cases.push({ id: 84, num: "14.3", nom: "Salle de bain", type: "piece" });

    cases.push({ id: 85, num: "5.5", nom: "Hall", type: "hall" });
    cases.push({ id: 86, num: "6.3", nom: "Hall", type: "hall" });
    cases.push({ id: 87, num: "6.6", nom: "Hall", type: "hall" });
    cases.push({ id: 88, num: "7.5", nom: "Hall", type: "hall" });

    nbCases = cases.length;

    /*var test = JSON.stringify(cases);
     console.log(test);*/

    //console.log(cases[20].couleur+'\n');

    nbCartes = 21;
    cartes = [];
    cartes.push({ id: 0, nom: "Violet", type: "perso", tag: 3 });
    cartes.push({ id: 1, nom: "Leblanc", type: "perso", tag: 6 });
    cartes.push({ id: 2, nom: "Rose", type: "perso", tag: 10 });
    cartes.push({ id: 3, nom: "Olivier", type: "perso", tag: 11 });
    cartes.push({ id: 4, nom: "Moutarde", type: "perso", tag: 12 });
    cartes.push({ id: 5, nom: "Pervenche", type: "perso", tag: 13 });

    cartes.push({ id: 6, nom: "Corde", type: "arme", tag: 14 });
    cartes.push({ id: 7, nom: "Poignard", type: "arme", tag: 15 });
    cartes.push({ id: 8, nom: "Cle anglaise", type: "arme", tag: 16 });
    cartes.push({ id: 9, nom: "Revolver", type: "arme", tag: 21 });
    cartes.push({ id: 10, nom: "Chandelier", type: "arme", tag: 22 });
    cartes.push({ id: 11, nom: "Barre de fer", type: "arme", tag: 25 });

    
    cartes.push({ id: 12, nom: "Entree", type: "piece", tag: "" });
    cartes.push({ id: 13, nom: "Salle de jeux", type: "piece", tag: "" });
    cartes.push({ id: 14, nom: "Bureau", type: "piece", tag: "" });
    cartes.push({ id: 15, nom: "Salle a manger", type: "piece", tag: "" });
    cartes.push({ id: 16, nom: "Garage", type: "piece", tag: "" });
    cartes.push({ id: 17, nom: "Salon", type: "piece", tag: "" });
    cartes.push({ id: 18, nom: "Cuisine", type: "piece", tag: "" });
    cartes.push({ id: 19, nom: "Chambre", type: "piece", tag: "" });
    cartes.push({ id: 20, nom: "Salle de bains", type: "piece", tag: "" });


    //cartes.push({ id: 21, nom: "Hall", type: "hall", tag:"" });
    

    nbCartes = cartes.length;

    nbAssociations = 0;

    nbJoueursPrets = 0;

    termine = false;

}
io.on('connection', function(socket){

    var myId;
    var name;

    console.log("nouveau client");

    socket.on('test', function (s) {
        console.log("Message recu : " + s);
        io.sockets.emit('test', s);
    });

    /***** FIN EMIT ANDROID *****/

    socket.on('addPlayer', function (playerName, persoName) {

        if (termine == true) {
            init();
        }

        console.log("Joueur connecte : " + playerName);

        var i;
        var tagPerso;
        for (i = 0; i < nbCartes; i++) {
            if (persoName == cartes[i].nom) {
                tagPerso = cartes[i].tag;
            }
        }
        name = playerName;

        if (partieEnCours) {

            var id = idJoueurs[name];
            if (id != null) {
                jSockets[id] = socket;
            }

        } else if (nbJoueurs < nbMaxJoueurs) {

            myId = nbJoueurs;
            idJoueurs[name] = nbJoueurs;

            jSockets.push(socket);

            joueurs[nbJoueurs] = {};
            joueurs[nbJoueurs].id = nbJoueurs;
            joueurs[nbJoueurs].name = name;
            joueurs[nbJoueurs].persoName = persoName;
            joueurs[nbJoueurs].tag = tagPerso;
            joueurs[nbJoueurs].numCase = ["5.5", "6.3", "6.6", "7.5"]; //hall
            joueurs[nbJoueurs].supposition = [];
            joueurs[nbJoueurs].cartes = [];
            joueurs[nbJoueurs].inline = true;
            joueurs[nbJoueurs].moved = false;


            if (tableSocket != null) {
                tableSocket.emit("nouveauJoueur", { idJoueur: nbJoueurs, persoName: persoName });
            }

            nbJoueurs++;
            console.log(nbJoueurs);

            socket.emit('myId', myId);
            socket.emit('joueurReady', null);
            //socket.emit('cases', cases);
            //socket.emit('cartes', cartes);
        }
    });


    socket.on('lanceDe', function (value) {
        var val = parseInt(value);
        console.log(val);
        tableSocket.emit('choixMouvement', { idJoueur: joueurs[idJoueurActuel].persoName, idCase: joueurs[idJoueurActuel].numCase[0], value: val });
    });

    socket.on('supposition', function (perso, arme, lieu) {
        tableSocket.emit('showSupposition', { perso: perso, arme: arme, lieu: lieu });
        var verif = false;
        var idJoueurSuppos;
        var cartesSuppos = [];
        if (nbJoueurs - idJoueurActuel != 0){
            for (i = idJoueurActuel + 1; i < idJoueurActuel; i++) {
                for (j = 0; j < joueurs[i].cartes.length; j++) {
                    if (joueurs[i].cartes[j].nom == perso) {
                        verif = true;
                        cartesSuppos.push({ card : perso });
                    }
                    if (joueurs[i].cartes[j].nom == arme) {
                        verif = true;
                        cartesSuppos.push({ card : arme });
                    }
                    if (joueurs[i].cartes[j].nom == lieu) {
                        verif = true;
                        cartesSuppos.push({ card : lieu });
                    }
                }
                if (verif) {
                    idJoueurSuppos = i;
                    break
                }
            }
        }

        if (idJoueurActuel != 0 && !verif) {
            for (i = 0; i < idJoueurActuel; i++) {
                for (j = 0; j < joueurs[i].cartes.length; j++) {
                    if (joueurs[i].cartes[j].nom == perso) {
                        verif = true;
                        cartesSuppos.push({ card : perso });
                    }
                    if (joueurs[i].cartes[j].nom == arme) {
                        verif = true;
                        cartesSuppos.push({ card : arme });
                    }
                    if (joueurs[i].cartes[j].nom == lieu) {
                        verif = true;
                        cartesSuppos.push({ card : lieu });
                    }
                }
                if (verif) {
                    idJoueurSuppos = i;
                    break
                }
            }
        }
        if(verif){
            jSockets[idJoueurSuppos].emit('choixCarteSupposition',cartesSuppos );
        }
        else {
            jSockets[idJoueurActuel].emit('noCarteSupposition');
        }
    });
	
	socket.on('receptionChoixCarteSupposition', function (nomCarte) {
        jSockets[idJoueurActuel].emit('envoiCarteSupposition',nomCarte);
    });
    //envoi d'une demande de confirmation aupres du joueur qui a emis la supposition
    //sa reponse = tourTermine, tour suivant 


    socket.on('accusation', function (perso, arme, lieu) {
        var idCase = joueurs[idJoueurActuel].numCase;
        //changement du moved pour l'accusé
        
    });
    socket.on('choixCarte', function (perso, arme, lieu) {

    });

    /***** FIN EMIT ANDROID *****/


    /***** EMIT TABLE *****/

    socket.on('addTable', function () {

        if (termine == true) {
            init();
        }

        console.log("Table Connectee");

        tableSocket = socket;

        console.log(nbJoueurs);

        /*if (nbJoueurs == nbMaxJoueurs) {
            //io.sockets.emit('choixPions', joueurs);
            console.log("joueurs prets");
            io.sockets.emit('joueursPrets', nbJoueurs);
        }*/
    });

    /*socket.on('numJoueur', function (s) {
        console.log("Nombre joueurs maximum : " + s);
        nbMaxJoueurs = s;
        //io.sockets.emit('serveurPret', "hello");
    });*/

    socket.on('newObject', function (object_tag) {
        var i;
        for (i = 0; i < nbCartes; i++) {
            if (object_tag == cartes[i].tag) {
                io.sockets.emit('interObject', {idJoueur: joueurs[idJoueurActuel].persoName, idCarte: cartes[i]} );
            }
        }
    });

    /** si bouton 'lancement partie' et non pas préciser le nombre de joueurs' */
    socket.on('lancementDebutPartie', function () {

        console.log("début");


        var cartesTemp = cartes;
        //console.log(cartesTemp);
        var persoMilieu = randomInt(0, 5);
        cartesMilieu.push(cartesTemp[persoMilieu]); // perso random
        cartesTemp.splice(persoMilieu, 1); // on le supprime de la liste des cartes

        var armeMilieu = randomInt(6, 11);
        cartesMilieu.push(cartesTemp[armeMilieu]); // arme random
        cartesTemp.splice(armeMilieu, 1);// on le supprime de la liste des cartes

        var pieceMilieu = randomInt(12, 20);
        cartesMilieu.push(cartesTemp[pieceMilieu]); // piece random
        cartesTemp.splice(pieceMilieu, 1);// on le supprime de la liste des cartes
        //console.log(cartesTemp);

        var cartesShuffle = cartesTemp;
        cartesShuffle.shuffle();     // on mélange le tableau
        cartesShuffle.join();
        console.log(cartesShuffle);

        var moduloNbCartesByPerso = (cartesShuffle.length / nbJoueurs) % nbJoueurs;
        var nbCartesByPerso = (cartesShuffle.length / nbJoueurs) - (moduloNbCartesByPerso);
        var nbResteCartes = cartesShuffle.length - nbCartesByPerso * nbJoueurs;
        console.log(cartesShuffle.length);
        console.log(moduloNbCartesByPerso);
        console.log(nbCartesByPerso);
        console.log(nbResteCartes);
        var i;
        for (i = 0; i < nbJoueurs; i++) {
            joueurs[i].cartes = cartesShuffle.slice(nbCartesByPerso * i, (nbCartesByPerso * i)+nbCartesByPerso);
            console.log(joueurs[i].cartes);
        }
        if (nbResteCartes > 0) {
            console.log('ici');
            for (i = 0; i < nbResteCartes; i++) {
                joueurs[i].cartes.push(cartesShuffle[nbResteCartes + i]);
                console.log(joueurs[i].cartes);
            }
        }
        for (i = 0; i < nbJoueurs; i++) {
            //jSockets[idJoueurActuel].emit('myCards', joueurs[i].cartes); // envoyer les cartes de tous les joueurs
            //io.sockets.emit('myCards', { idJoueur: i, cartes: joueurs[i].cartes });
            var j;
            for (j = 0; j < joueurs[i].cartes.length; j++) {
			console.log(joueurs[i].cartes[j].nom);
                io.sockets.emit('myCards', { idJoueur: i, cartes: joueurs[i].cartes[j].nom });
                if (joueurs[i].cartes[j].type == "perso") {
                    io.sockets.emit('myCardsPerso', { idJoueur: i, cartes: joueurs[i].cartes[j].nom });
                } else if (joueurs[i].cartes[j].type == "arme") {
                    io.sockets.emit('myCardsArme', { idJoueur: i, cartes: joueurs[i].cartes[j].nom });
                } else {
                    io.sockets.emit('myCardsPiece', { idJoueur: i, cartes: joueurs[i].cartes[j].nom });
                }
            }
        }

        
        console.log("Lancement début de partie");
        io.sockets.emit('joueursPrets', nbJoueurs); /* mettre écran poser les pions */
    });

    /** Pions dans le hall */
    socket.on('lancementPionsPrets', function () {
        console.log(nbJoueurs);
        
        idJoueurDepart = randomInt(0, nbJoueurs-1);
        console.log(idJoueurDepart);
        idJoueurActuel = idJoueurDepart;
        io.sockets.emit('debutPartie', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase[0]}); 

    });



    socket.on('tourTermine', function (newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        io.sockets.emit('waitTurn', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase[0] }); // fin de tour

        do {
            idJoueurActuel = (idJoueurActuel + 1) % nbJoueurs;
        } while (!joueurs[idJoueurActuel].inline); // on saute si le joueur suivant a été éliminé après une fausse accusation
        console.log("joueur tour : " + idJoueurActuel);
       
        var case_rac = [];
        if (joueurs[idJoueurActuel].moved == true) {
            joueurs[idJoueurActuel].moved = false;
            io.sockets.emit('tourChange', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase[0] });
        } else if (joueurs[idJoueurActuel].numCase[0] == "14.3") { // salle de bain -> chambre
            case_rac.push(cases[82]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase:  case_rac});
        } else if (joueurs[idJoueurActuel].numCase[0] == "7.8") { // salle à manger -> cuisine
            case_rac.push(cases[81]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "11.9") { // cuisine -> salle à manger et garage
            case_rac.push(cases[80]);
            case_rac.push(cases[76]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "12.0") { // chambre -> salle de bain, salon
            case_rac.push(cases[84]);
            case_rac.push(cases[77]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "2.0") { // garage -> cuisine
            case_rac.push(cases[81]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "3.9") { // salon -> chambre
            case_rac.push(cases[82]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else { // salle sans raccourcis ou dans le couloir
            io.sockets.emit('tourLancerDe', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase[0] });
        }

    });

    socket.on('notSuppose', function () { // tel / tablette
        var case_rac = [];
        if (joueurs[idJoueurActuel].numCase[0] == "14.3") { // salle de bain -> chambre
            case_rac.push(cases[82]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "7.8") { // salle à manger -> cuisine
            case_rac.push(cases[81]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "11.9") { // cuisine -> salle à manger et garage
            case_rac.push(cases[80]);
            case_rac.push(cases[76]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "12.0") { // chambre -> salle de bain, salon
            case_rac.push(cases[84]);
            case_rac.push(cases[77]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "2.0") { // garage -> cuisine
            case_rac.push(cases[81]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else if (joueurs[idJoueurActuel].numCase[0] == "3.9") { // salon -> chambre
            case_rac.push(cases[82]);
            io.sockets.emit('tourRaccourci', { idJoueur: idJoueurActuel, idCase: case_rac });
        } else { // salle sans raccourcis ou dans le couloir
            io.sockets.emit('tourLancerDe', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase[0] });
        }

    });

    /* Changement de tour avec ancien appel dans une piece */
    socket.on('nextTourChange', function (prochainJoueur, newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        console.log(prochainJoueur);
        joueurs[idJoueurActuel].moved = false;
        jSockets[idJoueurActuel].emit('tourChange', joueurs[idJoueurActuel].numCase[0] );
    });

    /* Changement de tour avec possibilité de prendre un raccourci */
    socket.on('nextTourRaccourci', function (prochainJoueur, newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        console.log(prochainJoueur);
        jSockets[idJoueurActuel].emit('tourRaccourci', joueurs[idJoueurActuel].numCase[0] );
    });

    /* Changement de tour avec lancé de dé */
    socket.on('nextTourLanceDe', function (prochainJoueur, newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        console.log(prochainJoueur);
        jSockets[idJoueurActuel].emit('tourLancerDe', null);
    });

    socket.on('newPionSupposition', function (idNewPion) { // envoie des pions un à la fois
        if (cartes[idNewPion].type == "perso") {
            jSockets[idJoueurActuel].emit('addPersoSupposition', { idJoueur: idJoueurActuel, cartes: cartes[idNewPion].nom });
        } else if (cartes[idNewPion].type == "arme") {
            jSockets[idJoueurActuel].emit('addArmeSupposition', { idJoueur: idJoueurActuel, cartes: cartes[idNewPion].nom });
        }
    });
    /* Choix de la supposition si dans une pièce */
    socket.on('tourChoixSupposition', function (newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        jSockets[idJoueurActuel].emit('tourSupposition', joueurs[idJoueurActuel].numCase[0] );
    });

    /* Choix de l'accusation si dans une pièce */
    socket.on('tourChoixAccusation', function (newNumCase) {
        joueurs[idJoueurActuel].numCase[0] = newNumCase;
        jSockets[idJoueurActuel].emit('tourAccusation', joueurs[idJoueurActuel].numCase[0]);
    });

    /* Demande des déplacements de l'accusation */
    socket.on('tourAttenteDeplacement', function (arg) {
        console.log(arg);
        jSockets[idJoueurActuel].emit('attenteDeplacement', null);
    });

    /* Attente de la supposition */
    socket.on('tourAttenteSupposition', function (arg) {
        console.log(arg);
        jSockets[idJoueurActuel].emit('attenteSupposition', null);
    });

    /* Attente de l'accusation */
    socket.on('tourAttenteAccusation', function (arg) {
        console.log(arg);
        jSockets[idJoueurActuel].emit('attenteAccusation', null);
    });

    /* Choix de la carte d'accusationn */
    socket.on('tourChoixCarteAccusation', function (arg) {
        console.log(arg);
        // COMMENT FAIRE................???????????????,
        io.sockets.emit('choixCarteAccusation', null);
    });

    /* Reception de la carte de l'accusation */
    socket.on('tourReceptionCarte', function (pseudo, idCarteRecu) {
        jSockets[idJoueurActuel].emit('receptionCarteAccusation', { idJoueur: idJoueurActuel, pseudo: pseudo, cartes: cartes[idCarteRecu].nom }); //envoie de la carte
    });

    /* Reception de la carte de l'accusation */
    socket.on('tourFinJeu', function (arg) {
        console.log(arg);
        io.sockets.emit('partieTerminee', arg); //affichage du vainqueur et des accusés
    });

    /***** EMIT TABLE *****/

    socket.on('reconnect', function (arg) {

        if (joueurs[arg]) {

            myId = arg;
            name = joueurs[myId].name;

            jSockets[myId] = socket;

            socket.emit('reconnected', joueurs);
            socket.emit('cases', cases);

            io.sockets.emit('prochainJoueur', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase });
        }
    });

    socket.on('disconnect', function(){
        console.log("Client disconnected : " + socket.id);
    });

    socket.on('error', function(errorData){
        console.log("An error occurred during Client connection : " + socket.id);
        console.log(errorData);
    });
});