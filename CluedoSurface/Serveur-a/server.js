var express = require('express');  
var app = express();  
var httpserver = require('http').createServer(app);
var io = require('socket.io').listen(httpserver);

io.enable('browser client minification');  // send minified client
io.enable('browser client etag');          // apply etag caching logic based on version number

io.set('transports', [
    'websocket'
  , 'flashsocket'
  , 'htmlfile'
  , 'xhr-polling'
  , 'jsonp-polling'
]);

app.configure(function(){  
  app.use(express.static(__dirname + '/'));  
});  
app.get('/', function(req, res, next){  
  res.render('index.html');  
});

var port = 8080;

// launch the http server on given port
httpserver.listen(port);

var joueurs;
var tableSocket;

var jSockets;

var nbJoueurs;

var partieEnCours;

var idJoueurs;

var idJoueurActuel;

var idJoueurDepart;

var argentMilieu;

var nbMaxTours;

var nbMaxJoueurs;

var nbCases;
var cases;

/*var test = JSON.stringify(cases);
console.log(test);*/

//console.log(cases[20].couleur+'\n');

var nbCartes;
var cartes;

var nbAssociations;

var nbJoueursPrets;

init();

console.log("Serveur lance !");

/*var test = JSON.stringify(cartes);
console.log(test);*/

var termine;

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

    argentMilieu = 0;

    nbMaxTours = 15;

    nbMaxJoueurs = 4;

    nbCases = 24;
    cases = [];
    cases.push({id:0, nom:"RENTREE", valeur:100, couleur:null, proprietaire:null});
    cases.push({id:1, nom:"POLYTECH MARSEILLE", valeur:100, couleur:"VERT", proprietaire:null});
    cases.push({id:2, nom:"CHANCE", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:3, nom:"POLYTECH NANTES", valeur:100, couleur:"VERT", proprietaire:null});
    cases.push({id:4, nom:"SECHER LES COURS", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:5, nom:"POLYTECH MONTPELLIER", valeur:100, couleur:"VERT", proprietaire:null});
    cases.push({id:6, nom:"EXAMENS", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:7, nom:"CHANCE", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:8, nom:"POLYTECH TOURS", valeur:200, couleur:"JAUNE", proprietaire:null});
    cases.push({id:9, nom:"POLYTECH LYON", valeur:200, couleur:"JAUNE", proprietaire:null});
    cases.push({id:10, nom:"PRIX NUIT DE L\'INFO", valeur:50, couleur:null, proprietaire:null});
    cases.push({id:11, nom:"POLYTECH CLERMONT", valeur:200, couleur:"JAUNE", proprietaire:null});
    cases.push({id:12, nom:"BOURSE AU MERITE", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:13, nom:"SECHER LES COURS", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:14, nom:"POLYTECH GRENOBLE", valeur:300, couleur:"ROUGE", proprietaire:null});
    cases.push({id:15, nom:"CHANCE", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:16, nom:"POLYTECH ANNECY CHAMBERY", valeur:300, couleur:"ROUGE", proprietaire:null});
    cases.push({id:17, nom:"POLYTECH LILLE", valeur:300, couleur:"ROUGE", proprietaire:null});
    cases.push({id:18, nom:"ALLEZ EN EXAMS", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:19, nom:"CHANCE", valeur:null, couleur:null, proprietaire:null});
    cases.push({id:20, nom:"POLYTECH ORLEANS", valeur:400, couleur:"BLEU", proprietaire:null});
    cases.push({id:21, nom:"POLYTECH PARIS", valeur:400, couleur:"BLEU", proprietaire:null});
    cases.push({id:22, nom:"FRAIS DE SCOLARITE", valeur:150, couleur:null, proprietaire:null});
    cases.push({id:23, nom:"POLYTECH NICE SOPHIA", valeur:400, couleur:"BLEU", proprietaire:null});

    /*var test = JSON.stringify(cases);
     console.log(test);*/

//console.log(cases[20].couleur+'\n');

    nbCartes = 3;
    cartes = [];
    cartes.push({id:0, nom:"BOURSE DE MOBILITE", type:"argent", valeur:75, description:"Le conseil régional vous aide à vous déplacer ! Vous touchez par conséquent une bourse de mobilité de 75€."});
    cartes.push({id:1, nom:"PERTE CARTE ETU", type:"argent", valeur:-25, description:"Vous avez perdu votre carte étudiant, pour en obtenir une nouvelle, vous payez 25€."});
    cartes.push({id:2, nom:"CONTROLE SURPRISE", type:"deplacement", valeur:6, description:"Contrôle surprise ! Allez en EXAMS sans passer par la rentrée, ne touchez pas 100€."});

    nbAssociations = 0;

    nbJoueursPrets = 0;

    termine = false;
}

io.sockets.on('connection', function (socket) {
	
	var myId;
	var name;

    console.log("nouveau client");
	
	socket.on('test', function (s) {
		console.log("Message recu : " + s);
		io.sockets.emit('test', s);
	});
	
	io.sockets.emit('test', "hello world");

	socket.on('addTable', function() {

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
			joueurs[nbJoueurs].argent = 1000;
			joueurs[nbJoueurs].numCase = 0;
			joueurs[nbJoueurs].proprietes = [];
			joueurs[nbJoueurs].cartes = [];
			joueurs[nbJoueurs].nbTours = 0;
			joueurs[nbJoueurs].passeTour = false;
			joueurs[nbJoueurs].enExams = false;
			joueurs[nbJoueurs].nbToursExams = 0;
			joueurs[nbJoueurs].rentreePassee = false;

			if (tableSocket != null){
				tableSocket.emit("nouveauJoueur", {idJoueur: nbJoueurs, pseudoJoueur: name});
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

	socket.on('reconnect', function(arg) {

		if(joueurs[arg]) {

			myId = arg;
			name = joueurs[myId].name;

			jSockets[myId] = socket;

	        socket.emit('reconnected', joueurs);
	        socket.emit('cases', cases);

	    	if (!joueurs[idJoueurActuel].passeTour) {
				if (joueurs[idJoueurActuel].enExams) {

	                if (joueurs[idJoueurActuel].nbToursExams == 2) {
	                    joueurs[idJoueurActuel].enExams = false;
	                    joueurs[idJoueurActuel].nbToursExams = 0;

	                    //Sera emit a true lors des actions sur une case
	                    io.sockets.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
	                    io.sockets.emit('enExams', {idJoueur:idJoueurActuel, enExams:joueurs[idJoueurActuel].enExams});
	                } else {
					    joueurs[idJoueurActuel].nbToursExams++;
						tableSocket.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
	                    io.sockets.emit('tourEnExams', {idJoueur:idJoueurActuel, nbToursExams:joueurs[idJoueurActuel].nbToursExams});
	                    tableSocket.emit('enExams', {idJoueur:idJoueurActuel, enExams:joueurs[idJoueurActuel].enExams});
	                }
				} else {
				    io.sockets.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
	            }
			} else {
				tableSocket.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
				io.sockets.emit('passeSonTour', idJoueurActuel);
	            joueurs[idJoueurActuel].passeTour = false;
	            io.sockets.emit('passeTour', {idJoueur:idJoueurActuel, passeTour:joueurs[idJoueurActuel].passeTour});
			}
		}
	});

	socket.on('forcerDebutPartie', function(arg) {
        console.log(arg);
        nbMaxJoueurs = nbJoueurs;
        io.sockets.emit('choixPions', joueurs);
	});
	
	socket.on('associerTag', function(arg) {
        console.log(arg);
        var data = JSON.parse(arg);
		joueurs[data.idJoueur].tag = data.tag;
		io.sockets.emit('nouveauTag', data);

        nbAssociations++;
        if (nbAssociations == nbMaxJoueurs) {
            //io.sockets.emit('preparerPions', null);

            idJoueurDepart = Math.floor(Math.random()*nbMaxJoueurs);
            idJoueurActuel = idJoueurDepart;
            io.sockets.emit('debutPartie', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
        }
	});

    socket.on('pionPret', function() {
        nbJoueursPrets++;
		console.log(nbJoueursPrets);
        if (nbJoueursPrets == nbMaxJoueurs) {
            idJoueurDepart = Math.floor(Math.random()*nbMaxJoueurs);
            idJoueurActuel = idJoueurDepart;
            //io.sockets.emit('debutPartie', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
			//tableSocket.emit('debutPartie', idJoueurActuel);
            io.sockets.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
        }
    });

	socket.on('tourTermine', function() {
		idJoueurActuel = (idJoueurActuel + 1)%nbMaxJoueurs;
		if (idJoueurActuel == idJoueurDepart) {
			joueurs[idJoueurActuel].nbTours++;
			
			if (joueurs[idJoueurActuel].nbTours == nbMaxTours) {
				io.sockets.emit('partieTerminee', null);
                termine = true;
			}
		}
		if (!joueurs[idJoueurActuel].passeTour) {
			if (joueurs[idJoueurActuel].enExams) {

                if (joueurs[idJoueurActuel].nbToursExams == 2) {
                    joueurs[idJoueurActuel].enExams = false;
                    joueurs[idJoueurActuel].nbToursExams = 0;

                    //Sera emit a true lors des actions sur une case
                    io.sockets.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
                    io.sockets.emit('enExams', {idJoueur:idJoueurActuel, enExams:joueurs[idJoueurActuel].enExams});
                } else {
				    joueurs[idJoueurActuel].nbToursExams++;
					tableSocket.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
                    io.sockets.emit('tourEnExams', {idJoueur:idJoueurActuel, nbToursExams:joueurs[idJoueurActuel].nbToursExams});
                    tableSocket.emit('enExams', {idJoueur:idJoueurActuel, enExams:joueurs[idJoueurActuel].enExams});
                }
			} else {
			    io.sockets.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
            }
		} else {
			tableSocket.emit('prochainJoueur', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
			io.sockets.emit('passeSonTour', idJoueurActuel);
            joueurs[idJoueurActuel].passeTour = false;
            io.sockets.emit('passeTour', {idJoueur:idJoueurActuel, passeTour:joueurs[idJoueurActuel].passeTour});
		}
	});
	
	socket.on('lanceDe', function (value) {
        var val = parseInt(value);
        if (joueurs[idJoueurActuel].enExams) {
            if (val == 6) {
                joueurs[idJoueurActuel].enExams = false;
                io.sockets.emit('enExams', {idJoueur:idJoueurActuel, enExams:false});
            }
        } else {
        	if ((joueurs[idJoueurActuel].numCase + val) > nbCases) {
        		joueurs[idJoueurActuel].rentreePassee = true;
        		joueurs[idJoueurActuel].argent += cases[0].valeur;
				io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
        	}
            joueurs[idJoueurActuel].numCase = (joueurs[idJoueurActuel].numCase + val) % nbCases;
            io.sockets.emit('caseChangee', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
        }
	});

    socket.on('pionCaseExamens', function() {
        jSockets[idJoueurActuel].emit("debutExamens", null);
    });
	
	socket.on('pionDeplace', function () {

		if (joueurs[idJoueurActuel].rentreePassee == true) {
			jSockets[idJoueurActuel].emit('caseDepartPassee', cases[0].valeur);
			joueurs[idJoueurActuel].rentreePassee = false;
		}
		
		var idCase = joueurs[idJoueurActuel].numCase;
		
		switch (idCase) {
			case 0 : 	joueurs[idJoueurActuel].argent += cases[0].valeur;
						io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
						
						jSockets[idJoueurActuel].emit('caseDepart', cases[0].valeur);
						break;
						
			case 2 :
			case 7 :
			case 15 :
			case 19 :	jSockets[idJoueurActuel].emit('caseCarteChance', null);
						break;
						
			case 4 :
			case 13 :	joueurs[idJoueurActuel].passeTour = true;
						io.sockets.emit('passeTour', {idJoueur:idJoueurActuel, passeTour:joueurs[idJoueurActuel].passeTour});

                        jSockets[idJoueurActuel].emit('casePasseSonTour', null);
						break;
						
			case 6 :	jSockets[idJoueurActuel].emit('caseRevisions', null);
						break;
						
			case 10 :	joueurs[idJoueurActuel].argent += cases[10].valeur;
						io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
						jSockets[idJoueurActuel].emit('nuitDeLinfo', cases[10].valeur);
						break;
						
			case 12 :	joueurs[idJoueurActuel].argent += argentMilieu;
						io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
						jSockets[idJoueurActuel].emit('bourseAuMerite', argentMilieu);

						argentMilieu = 0;
						tableSocket.emit('nouvelleBourse', argentMilieu);

						break;
						
			case 18 :	joueurs[idJoueurActuel].enExams = true;
						joueurs[idJoueurActuel].nbToursExams = 0;
						io.sockets.emit('enExams', {idJoueur:idJoueurActuel, enExams:true});
						joueurs[idJoueurActuel].numCase = 6;
						io.sockets.emit('caseChangee', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
						jSockets[idJoueurActuel].emit('sessionExams', null);
						break;
						
			case 22 : 	joueurs[idJoueurActuel].argent -= cases[22].valeur;
						io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
						jSockets[idJoueurActuel].emit('fraisScolarite', cases[22].valeur);
						argentMilieu += cases[22].valeur;
						tableSocket.emit('nouvelleBourse', argentMilieu);
						break;
			
			default : 	if (cases[idCase].proprietaire == null) {
                            jSockets[idJoueurActuel].emit('propositionAchat', idCase);
						} else {
							if (cases[idCase].proprietaire == idJoueurActuel) {
                                jSockets[idJoueurActuel].emit('propositionVente', idCase);
							} else {
								var idProprio = cases[idCase].proprietaire;

								var nbProprietes = 0;
								var somme = 0;
								var tab = joueurs[idProprio].proprietes;
								
								for (var i=0; i<tab.length; i++) {
									if (cases[tab[i]].couleur == cases[idCase].couleur) {
										nbProprietes++;
									}
								}
								
								somme = (cases[idCase].valeur / 4) * nbProprietes;
								joueurs[idJoueurActuel].argent -= somme;
								io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
                                jSockets[idJoueurActuel].emit('paiement', {idCase:idCase, nbProprietes:nbProprietes, somme:somme});

                                joueurs[idProprio].argent += somme;
                                io.sockets.emit('nouveauArgent', {idJoueur:idProprio, argent:joueurs[idProprio].argent});
                                jSockets[idProprio].emit('gainPropriete', {idCase:idCase, somme:somme});
							}
						}
						
						break;
		}
	});
	
	socket.on('carteChanceTiree', function() {
		var carteId = Math.floor(Math.random()*nbCartes);
		if (cartes[carteId].type == "argent") {
			joueurs[idJoueurActuel].argent += cartes[carteId].valeur;
			io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});

			if (cartes[carteId].valeur < 0) {
				argentMilieu -= cartes[carteId].valeur;
				tableSocket.emit('nouvelleBourse', argentMilieu);
			}

		} else if (cartes[carteId].type == "deplacement") {
			joueurs[idJoueurActuel].numCase = cartes[carteId].valeur;
			if (cartes[carteId].nom == "CONTROLE SURPRISE") {
				joueurs[idJoueurActuel].enExams = true;
				joueurs[idJoueurActuel].nbToursExams = 0;
				io.sockets.emit('enExams', {idJoueur:idJoueurActuel, enExams:true});
			}
			io.sockets.emit('caseChangee', {idJoueur:idJoueurActuel, idCase:joueurs[idJoueurActuel].numCase});
		}
        jSockets[idJoueurActuel].emit('carteChance', {carteId:carteId, description:cartes[carteId].description});
	});
	
	socket.on('achatProprietee', function() {
		var idCase = joueurs[idJoueurActuel].numCase;
		if (cases[idCase].couleur != null) {
		
			if (cases[idCase].proprietaire == null) {
				
				joueurs[idJoueurActuel].argent -= cases[idCase].valeur;
				io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
				
				cases[idCase].proprietaire = idJoueurActuel;
				joueurs[idJoueurActuel].proprietes.push(idCase);
				io.sockets.emit('nouvellePropriete', {idJoueur:idJoueurActuel, idCase:idCase});
				tableSocket.emit('transactionEcole', {couleur:joueurs[idJoueurActuel].tag, idCase:idCase});

				argentMilieu += cases[idCase].valeur / 4;
				tableSocket.emit('nouvelleBourse', argentMilieu);
			}
		}
	});
	
	socket.on('venteProprietee', function() {
		var idCase = joueurs[idJoueurActuel].numCase;
		if (cases[idCase].proprietaire == idJoueurActuel) {
			
			joueurs[idJoueurActuel].argent += cases[idCase].valeur;
			io.sockets.emit('nouveauArgent', {idJoueur:idJoueurActuel, argent:joueurs[idJoueurActuel].argent});
			
			cases[idCase].proprietaire = null;
			joueurs[idJoueurActuel].proprietes.splice(joueurs[idJoueurActuel].proprietes.indexOf(idCase) , 1);
			io.sockets.emit('caseVendue', {idJoueur:idJoueurActuel, idCase:idCase});
		}
	});
	
	socket.on('disconnect', function () {
		console.log("PERTE DE CONNEXION");
	});
});