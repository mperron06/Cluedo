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

    nbMaxJoueurs = 4;

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

    /*var test = JSON.stringify(cases);
     console.log(test);*/

    //console.log(cases[20].couleur+'\n');

    nbCartes = 21;
    cartes = [];
    cartes.push({ id:0, nom:"Violet", type:"perso", tag:"A" });
    cartes.push({ id: 1, nom: "Leblanc", type: "perso", tag: "B" });
    cartes.push({ id: 2, nom: "Rose", type: "perso", tag: "C" });
    cartes.push({ id: 3, nom: "Olivier", type: "perso", tag: "D" });
    cartes.push({ id: 4, nom: "Moutarde", type: "perso", tag:"E" });
    cartes.push({ id: 5, nom: "Pervenche", type: "perso", tag:"F" });

    cartes.push({ id: 6, nom: "Corde", type: "arme", tag: "G" });
    cartes.push({ id: 7, nom: "Poignard", type: "arme", tag: "H" });
    cartes.push({ id: 8, nom: "Clé anglaise", type: "arme", tag: "I" });
    cartes.push({ id: 9, nom: "Revolver", type: "arme", tag: "J" });
    cartes.push({ id: 10, nom: "Chandelier", type: "arme", tag: "K" });
    cartes.push({ id: 11, nom: "Barre de fer", type: "arme", tag: "L" });

    
    cartes.push({ id: 12, nom: "Entrée", type: "piece", tag: "" });
    cartes.push({ id: 13, nom: "Salle de jeux", type: "piece", tag: "" });
    cartes.push({ id: 14, nom: "Bureau", type: "piece", tag: "" });
    cartes.push({ id: 15, nom: "Salle à manger", type: "piece", tag: "" });
    cartes.push({ id: 16, nom: "Garage", type: "piece", tag: "" });
    cartes.push({ id: 17, nom: "Salon", type: "piece", tag: "" });
    cartes.push({ id: 18, nom: "Cuisine", type: "piece", tag: "" });
    cartes.push({ id: 19, nom: "Chambre", type: "piece", tag: "" });
    cartes.push({ id: 20, nom: "Salle de bains", type: "piece", tag: "" });


    cartes.push({ id: 21, nom: "Hall", type: "hall", tag:"" });

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


    socket.on('numJoueur', function (s) {
        console.log("Nombre joueurs maximum : " + s);
        nbMaxJoueurs = s;
        //io.sockets.emit('serveurPret', "hello");
    });

    socket.on('addTable', function () {

        if (termine == true) {
            init();
        }

        console.log("Table Connectee");

        tableSocket = socket;

        console.log(nbJoueurs);

        if (nbJoueurs == nbMaxJoueurs) {
            //io.sockets.emit('choixPions', joueurs);
            console.log("joueurs prets");
            io.sockets.emit('joueursPrets', null);
        }
    });

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
            joueurs[nbJoueurs].tag = tagPerso;
            joueurs[nbJoueurs].numCase = ["5.5", "6.3", "6.6", "7.5"]; //hall
            joueurs[nbJoueurs].supposition = [];
            joueurs[nbJoueurs].cartes = [];
            joueurs[nbJoueurs].inline = true;
            joueurs[nbJoueurs].moved = false;

            if (tableSocket != null) {
                tableSocket.emit("nouveauJoueur", { idJoueur: nbJoueurs, pseudoJoueur: name });
            }

            nbJoueurs++;

            socket.emit('myId', myId);
            socket.emit('cases', cases);
            //socket.emit('cartes', cartes);

            if (nbJoueurs == nbMaxJoueurs && tableSocket != null) {
                //io.sockets.emit('choixPions', joueurs);
                console.log("joueurs prets");
                io.sockets.emit('joueursPrets', null);
            }
        }
    });

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

    socket.on('forcerDebutPartie', function (arg) {
        console.log(arg);
        nbMaxJoueurs = nbJoueurs;
        //io.sockets.emit('choixPions', joueurs);
    });

    socket.on('pionPret', function () {
        nbJoueursPrets++;
        console.log(nbJoueursPrets);
        if (nbJoueursPrets == nbMaxJoueurs) {
            idJoueurDepart = Math.floor(Math.random() * nbMaxJoueurs);
            idJoueurActuel = idJoueurDepart;
            //io.sockets.emit('debutPartie', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
            //tableSocket.emit('debutPartie', idJoueurActuel);
            io.sockets.emit('prochainJoueur', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase });
        }
    });

    socket.on('tourTermine', function () {
        do{
            idJoueurActuel = (idJoueurActuel + 1) % nbMaxJoueurs;
        } while (!joueurs[idJoueurActuel].inline); // on saute si le joueur suivant a été éliminé après une fausse accusation

        
        io.sockets.emit('prochainJoueur', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase });
    });


    socket.on('lanceDe', function (value) {
        var val = parseInt(value);
        io.sockets.emit('choixMouvement', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase, value: val });
    });

    socket.on('pionDeplace', function () {

        var idCase = joueurs[idJoueurActuel].numCase;

        switch (idCase) {
            // si case de lieu -> supposition, si case d'accusation -> accusation
            case 0: joueurs[idJoueurActuel].argent += cases[0].valeur;
                io.sockets.emit('nouveauArgent', { idJoueur: idJoueurActuel, argent: joueurs[idJoueurActuel].argent });

                jSockets[idJoueurActuel].emit('caseDepart', cases[0].valeur);
                break;

            case 2:
            case 7:
            case 15:
            case 19: jSockets[idJoueurActuel].emit('caseCarteChance', null);
                break;

            case 4:
            case 13: joueurs[idJoueurActuel].passeTour = true;
                io.sockets.emit('passeTour', { idJoueur: idJoueurActuel, passeTour: joueurs[idJoueurActuel].passeTour });

                jSockets[idJoueurActuel].emit('casePasseSonTour', null);
                break;

            case 6: jSockets[idJoueurActuel].emit('caseRevisions', null);
                break;

            case 10: joueurs[idJoueurActuel].argent += cases[10].valeur;
                io.sockets.emit('nouveauArgent', { idJoueur: idJoueurActuel, argent: joueurs[idJoueurActuel].argent });
                jSockets[idJoueurActuel].emit('nuitDeLinfo', cases[10].valeur);
                break;

            case 12: joueurs[idJoueurActuel].argent += argentMilieu;
                io.sockets.emit('nouveauArgent', { idJoueur: idJoueurActuel, argent: joueurs[idJoueurActuel].argent });
                jSockets[idJoueurActuel].emit('bourseAuMerite', argentMilieu);

                argentMilieu = 0;
                tableSocket.emit('nouvelleBourse', argentMilieu);

                break;

            case 18: joueurs[idJoueurActuel].enExams = true;
                joueurs[idJoueurActuel].nbToursExams = 0;
                io.sockets.emit('enExams', { idJoueur: idJoueurActuel, enExams: true });
                joueurs[idJoueurActuel].numCase = 6;
                io.sockets.emit('caseChangee', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase });
                jSockets[idJoueurActuel].emit('sessionExams', null);
                break;

            case 22: joueurs[idJoueurActuel].argent -= cases[22].valeur;
                io.sockets.emit('nouveauArgent', { idJoueur: idJoueurActuel, argent: joueurs[idJoueurActuel].argent });
                jSockets[idJoueurActuel].emit('fraisScolarite', cases[22].valeur);
                argentMilieu += cases[22].valeur;
                tableSocket.emit('nouvelleBourse', argentMilieu);
                break;

            default: if (cases[idCase].proprietaire == null) {
                jSockets[idJoueurActuel].emit('propositionAchat', idCase);
            } else {
                if (cases[idCase].proprietaire == idJoueurActuel) {
                    jSockets[idJoueurActuel].emit('propositionVente', idCase);
                } else {
                    var idProprio = cases[idCase].proprietaire;

                    var nbProprietes = 0;
                    var somme = 0;
                    var tab = joueurs[idProprio].proprietes;

                    for (var i = 0; i < tab.length; i++) {
                        if (cases[tab[i]].couleur == cases[idCase].couleur) {
                            nbProprietes++;
                        }
                    }

                    somme = (cases[idCase].valeur / 4) * nbProprietes;
                    joueurs[idJoueurActuel].argent -= somme;
                    io.sockets.emit('nouveauArgent', { idJoueur: idJoueurActuel, argent: joueurs[idJoueurActuel].argent });
                    jSockets[idJoueurActuel].emit('paiement', { idCase: idCase, nbProprietes: nbProprietes, somme: somme });

                    joueurs[idProprio].argent += somme;
                    io.sockets.emit('nouveauArgent', { idJoueur: idProprio, argent: joueurs[idProprio].argent });
                    jSockets[idProprio].emit('gainPropriete', { idCase: idCase, somme: somme });
                }
            }

                break;
        }
    });

    socket.on('supposition', function () {
        var idCase = joueurs[idJoueurActuel].numCase;

        if (cases[idCase].type == "piece") {
                io.sockets.emit('nouvelleSupposition', { idJoueur: idJoueurActuel, lieu: joueurs[idJoueurActuel].numCase });
            }
    });

    socket.on('accusation', function () {
        var idCase = joueurs[idJoueurActuel].numCase;

        if (cases[idCase].type == "hall") {
            io.sockets.emit('nouvelleAccusation', { idJoueur: idJoueurActuel, lieu: joueurs[idJoueurActuel].numCase });
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