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
    
    cases.push({id:22, nom:"FRAIS DE SCOLARITE", valeur:150, couleur:null, proprietaire:null});
    cases.push({id:23, nom:"POLYTECH NICE SOPHIA", valeur:400, couleur:"BLEU", proprietaire:null});

    /*var test = JSON.stringify(cases);
     console.log(test);*/

    //console.log(cases[20].couleur+'\n');

    nbCartes = 21;
    cartes = [];
    cartes.push({ id:0, nom:"Violet", type:"perso" });
    cartes.push({ id: 1, nom: "Leblanc", type: "perso" });
    cartes.push({ id: 2, nom: "Rose", type: "perso" });
    cartes.push({ id: 3, nom: "Olivier", type: "perso" });
    cartes.push({ id: 4, nom: "Moutarde", type: "perso" });
    cartes.push({ id: 5, nom: "Pervenche", type: "perso" });

    cartes.push({ id: 6, nom: "Corde", type: "arme" });
    cartes.push({ id: 7, nom: "Poignard", type: "arme" });
    cartes.push({ id: 8, nom: "Clé anglaise", type: "arme" });
    cartes.push({ id: 9, nom: "Revolver", type: "arme" });
    cartes.push({ id: 10, nom: "Chandelier", type: "arme" });
    cartes.push({ id: 11, nom: "Barre de fer", type: "arme" });

    
    cartes.push({ id: 12, nom: "Entrée", type: "piece" });
    cartes.push({ id: 13, nom: "Salle de jeux", type: "piece" });
    cartes.push({ id: 14, nom: "Bureau", type: "piece" });
    cartes.push({ id: 15, nom: "Salle à manger", type: "piece" });
    cartes.push({ id: 16, nom: "Garage", type: "piece" });
    cartes.push({ id: 17, nom: "Salon", type: "piece" });
    cartes.push({ id: 18, nom: "Cuisine", type: "piece" });
    cartes.push({ id: 19, nom: "Chambre", type: "piece" });
    cartes.push({ id: 20, nom: "Salle de bains", type: "piece" });


    cartes.push({ id: 20, nom: "Hall", type: "hall" });

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

    socket.on('addTable', function () {

        if (termine == true) {
            init();
        }

        console.log("Table Connectee");

        tableSocket = socket;

        console.log(nbJoueurs);

        if (nbJoueurs == nbMaxJoueurs) {
            io.sockets.emit('choixPions', joueurs);
        }
    });

    socket.on('addPlayer', function (playerName) {

        if (termine == true) {
            init();
        }

        console.log("Joueur connecte : " + playerName);

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
            joueurs[nbJoueurs].tag = "";
            joueurs[nbJoueurs].numCase = 0;
            joueurs[nbJoueurs].supposition = [];
            joueurs[nbJoueurs].cartes = [];
            joueurs[nbJoueurs].inline = true;
            joueurs[nbJoueurs].moved = false;

            if (tableSocket != null) {
                tableSocket.emit("nouveauJoueur", { idJoueur: nbJoueurs, pseudoJoueur: name });
            }

            console.log("Avant : " + nbJoueurs);

            nbJoueurs++;

            console.log("Apres : " + nbJoueurs);

            socket.emit('myId', myId);
            socket.emit('cases', cases);
            //socket.emit('cartes', cartes);

            if (nbJoueurs == nbMaxJoueurs && tableSocket != null) {
                io.sockets.emit('choixPions', joueurs);
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
        io.sockets.emit('choixPions', joueurs);
    });

    socket.on('associerTag', function (arg) {
        console.log(arg);
        var data = JSON.parse(arg);
        joueurs[data.idJoueur].tag = data.tag;
        io.sockets.emit('nouveauTag', data);

        nbAssociations++;
        if (nbAssociations == nbMaxJoueurs) {
            //io.sockets.emit('preparerPions', null);
            // Le premier joueur est tiré aléatoirement
            idJoueurDepart = Math.floor(Math.random() * nbMaxJoueurs);
            idJoueurActuel = idJoueurDepart;
            io.sockets.emit('debutPartie', { idJoueur: idJoueurActuel, idCase: joueurs[idJoueurActuel].numCase });
        }
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